package com.kitchenstory.controller;

import com.kitchenstory.entity.CartEntity;
import com.kitchenstory.entity.DishEntity;
import com.kitchenstory.entity.OrderEntity;
import com.kitchenstory.entity.UserEntity;
import com.kitchenstory.exceptions.UserNotFoundException;
import com.kitchenstory.service.CartService;
import com.kitchenstory.service.DishService;
import com.kitchenstory.service.OrderService;
import com.kitchenstory.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private final UserService userService;

    @PostMapping("add")
    public String add(@Valid OrderEntity orderEntity, BindingResult result, Model model) {

        //        Return the payment page if there are any errors
        if (result.hasErrors())
            return "payment";

        final UserEntity user = userService.findByEmail("j.riyazu@gmail.com")
                .orElseThrow(() -> new UserNotFoundException("User with Email Id: j.riyazu@gmail.com not found."));

        final CartEntity cart = user.getCart();

        List<DishEntity> dishes = cart.getDishes();

        //        Get the bill amount of all the dishes user is trying to order
        final Double billAmount = dishes.stream()
                .map(dish -> dish.getPrice())
                .mapToDouble(Double::doubleValue)
                .sum();

        //        Save the order in database
        final OrderEntity order = new OrderEntity(billAmount, dishes.size(), new Date(), dishes, user);

        //        Update the card and order details to the user
        user.setOrders(Arrays.asList(order));

        //        Update the user with card details in database
        userService.save(user);

        //        Delete the cart
        cartService.deleteById(cart.getId());

        model.addAttribute("order", order);

        return "receipt";
    }
}
