package com.kitchenstory.controller;

import com.kitchenstory.entity.CartEntity;
import com.kitchenstory.entity.DishEntity;
import com.kitchenstory.entity.UserEntity;
import com.kitchenstory.exceptions.DishNotFoundException;
import com.kitchenstory.exceptions.UserNotFoundException;
import com.kitchenstory.service.CartService;
import com.kitchenstory.service.DishService;
import com.kitchenstory.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final DishService dishService;
    private final UserService userService;

    @GetMapping()
    public String view(CartEntity cartEntity, Model model) {
        final UserEntity user = userService.findByEmail("j.riyazu@gmail.com")
                .orElseThrow(() -> new UserNotFoundException("User with Email Id: j.riyazu@gmail.com not found."));

        final CartEntity cart = user.getCart();

        final List<DishEntity> dishes = cart.getDishes();
        final Integer count = dishes.size();
        final Double total = dishes.stream()
                .map(dish -> dish.getPrice())
                .mapToDouble(Double::doubleValue)
                .sum();

        model.addAttribute("dishes", dishes);
        model.addAttribute("count", count);
        model.addAttribute("total", total);
        model.addAttribute("cards", Arrays.asList("Credit", "Debit"));
        return "payment";
    }

    @GetMapping("add/{id}")
    public String addDish(@PathVariable final String id) {
        final UserEntity user = userService.findByEmail("j.riyazu@gmail.com")
                .orElseThrow(() -> new UserNotFoundException("User with Email Id: j.riyazu@gmail.com not found."));

        final DishEntity dish = dishService.findById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish with id: " + id + " not found."));

        CartEntity cart = user.getCart();

        if (cart == null)
            cart = new CartEntity(null, user);

        cart.setDishes(Arrays.asList(dish));
        cartService.save(cart);

        return "redirect:/?add-to-cart=true";
    }

    @GetMapping("delete/{id}")
    public String deleteDish(@PathVariable final String id, Model model) {

        final UserEntity user = userService.findByEmail("j.riyazu@gmail.com")
                .orElseThrow(() -> new UserNotFoundException("User with Email Id: j.riyazu@gmail.com not found."));

        final CartEntity cart = user.getCart();

        final List<DishEntity> dishes = cart.getDishes();

        final DishEntity dish = dishService.findById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish with id: " + id + " not found."));

        dishes.remove(dish);

        cart.setDishes(dishes);
        cartService.save(cart);

        return "redirect:/cart?dish-removed=true";
    }

}
