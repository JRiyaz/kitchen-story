package com.kitchenstory.controller;

import com.kitchenstory.entity.CartEntity;
import com.kitchenstory.entity.DishEntity;
import com.kitchenstory.exceptions.CartNotFoundException;
import com.kitchenstory.exceptions.DishNotFoundException;
import com.kitchenstory.service.CartService;
import com.kitchenstory.service.DishService;
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

    @GetMapping()
    public String view(Model model) {
        final CartEntity cart = cartService.findByCartId(1)
                .orElseThrow(() -> new CartNotFoundException("Dish with id: " + 1 + " not found."));

        final List<DishEntity> dishes = cart.getDishEntities();

        model.addAttribute("dishes", dishes);
        return "payment";
    }

    @GetMapping("add/{id}")
    public String add(@PathVariable final String id) {
        final DishEntity dish = dishService.findById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish with id: " + id + " not found."));

        final CartEntity cart = new CartEntity(1, Arrays.asList(dish));

        cartService.save(cart);

        return "redirect:/?add-to-cart=true";
    }
}
