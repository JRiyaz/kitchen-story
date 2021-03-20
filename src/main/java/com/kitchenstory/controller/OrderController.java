package com.kitchenstory.controller;

import com.kitchenstory.entity.*;
import com.kitchenstory.exceptions.DishNotFoundException;
import com.kitchenstory.exceptions.UserNotFoundException;
import com.kitchenstory.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final DishService dishService;
    private final CartService cartService;
    private final CardService cardService;
    private final UserService userService;

    @GetMapping("checkout/{id}")
    public String checkout(@PathVariable final String id, Model model) {
        final DishEntity dish = dishService.findById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish with id: " + id + " not found."));

        model.addAttribute("dishes", Arrays.asList(dish));
        model.addAttribute("count", 1);
        model.addAttribute("total", dish.getPrice());
        model.addAttribute("cards", Arrays.asList("Credit", "Debit"));
        return "payment";
    }

    @PostMapping({"add", "add/{id}"})
    public String add(@Valid CardEntity cardEntity, BindingResult result,
                      @PathVariable final String id, Model model) {

        //        Return the payment page if there are any errors
        if (result.hasErrors())
            return "payment";

        final UserEntity user = userService.findByEmail("j.riyazu@gmail.com")
                .orElseThrow(() -> new UserNotFoundException("User with Email Id: j.riyazu@gmail.com not found."));

        List<DishEntity> dishes = null;

        //        Check if @PathVariable is null
        if (id != null)
            dishes.add(dishService.findById(id).get());
        else {
            final CartEntity cart = user.getCart();

            dishes = cart.getDishes();

            cartService.deleteById(cart.getId());
        }

        //        Save the card details to database
        final CardEntity card = cardService.save(cardEntity);

        //        Get the bill amount of all the dishes user is trying to order
        final Double billAmount = dishes.stream()
                .map(dish -> dish.getPrice())
                .mapToDouble(Double::doubleValue)
                .sum();

        //        Save the order in database
        final OrderEntity order = orderService
                .save(new OrderEntity(billAmount, dishes.size(), new Date(), dishes, user));

        //        Update the card and order details to the user
        user.setCards(Arrays.asList(card))
                .setOrders(Arrays.asList(order));

        //        Update the user with card details in database
        userService.save(user);

        model.addAttribute("order", order);

        return "receipt";
    }
}
