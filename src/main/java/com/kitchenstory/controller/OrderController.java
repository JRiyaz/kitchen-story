package com.kitchenstory.controller;

import com.kitchenstory.entity.DishEntity;
import com.kitchenstory.exceptions.DishNotFoundException;
import com.kitchenstory.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

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
}
