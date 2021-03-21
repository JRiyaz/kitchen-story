package com.kitchenstory.controller;

import com.kitchenstory.entity.DishEntity;
import com.kitchenstory.entity.ImageEntity;
import com.kitchenstory.exceptions.DishNotFoundException;
import com.kitchenstory.service.DishService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URL;

@Controller
@RequestMapping("/dish")
@AllArgsConstructor
public class DishController {

    private final DishService dishService;

    @GetMapping("{id}")
    public String showItem(@PathVariable final String id, Model model) {
        final DishEntity dish = dishService.findById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish with id: " + id + " not found."));

        model.addAttribute("dish", dish);

        return "dish";
    }

    @GetMapping("add")
    public String showAddTemplate(DishEntity dishEntity) {
        return "add-dish";
    }

    @PostMapping("add")
    public String addDish(@Valid final DishEntity dish, BindingResult result, Model model) throws IOException {

        if (result.hasErrors())
            return "add-dish";

        final byte[] image = IOUtils.toByteArray(new URL(dish.getUrl()));
        final ImageEntity file = new ImageEntity(image);
        dish.setImage(file);

        final DishEntity dishEntity = dishService.save(dish);

        model.addAttribute("dish", dishEntity);
        return "redirect:/?add-dish=true";
    }
}
