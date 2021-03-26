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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URL;
import java.util.List;

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

    @GetMapping("all")
    public String allDishes(Model model) {
        final List<DishEntity> dishes = dishService.findAll();
        model.addAttribute("dishes", dishes);
        return "dishes";
    }

    @GetMapping("add")
    public String showAddTemplate(@ModelAttribute("dishEntity") DishEntity dishEntity, Model model) {
        model.addAttribute("header", "Add Dish");
        return "add-dish";
    }

    @GetMapping("edit/{id}")
    public String editDish(@PathVariable String id, Model model) {
        final DishEntity dish = dishService.findById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish with id: " + id + " not found."));

        model.addAttribute("dishEntity", dish);
        model.addAttribute("header", "Update Dish");
        return "add-dish";
    }

    @PostMapping("add")
    public String addDish(@Valid final DishEntity dish, BindingResult result, Model model,
                          RedirectAttributes attributes) throws IOException {

        if (result.hasErrors())
            return "add-dish";
        if (dishService.findByName(dish.getName()).isPresent()) {
            attributes.addFlashAttribute("dishEntity", dish);
            return "redirect:/dish/add?dishName=error";
        }

        byte[] image = null;
        try {
            image = IOUtils.toByteArray(new URL(dish.getUrl()));
        } catch (Exception e) {
            attributes.addFlashAttribute("dishEntity", dish);
            return "redirect:/dish/add?imageUrl=error";
        }
        final ImageEntity file = new ImageEntity(image);
        dish.setImage(file);

        final DishEntity dishEntity = dishService.save(dish);

        model.addAttribute("dish", dishEntity);
        return "redirect:/?add-dish=true";
    }

    @PostMapping("add/{id}")
    public String updateDish(@PathVariable String id, @Valid final DishEntity dish, BindingResult result, Model model,
                             RedirectAttributes attributes, HttpServletRequest request) throws IOException {

        final DishEntity dishEntity = dishService.findById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish with id: " + id + " not found."));

        if (result.hasErrors())
            return "add-dish";

        byte[] image = null;
        try {
            image = IOUtils.toByteArray(new URL(dish.getUrl()));
        } catch (Exception e) {
            attributes.addFlashAttribute("dishEntity", dish);
            return "redirect:/dish/edit/" + id + "?imageUrl=error";
        }
        final ImageEntity file = new ImageEntity(image);

        dishEntity.setImage(file);
        dishEntity.setName(dish.getName());
        dishEntity.setPrice(dish.getPrice());
        dishEntity.setRating(dish.getRating());
        dishEntity.setSpecial(dish.getSpecial());
        dishEntity.setSpicy(dish.getSpicy());
        dishEntity.setDescription(dish.getDescription());
        dishEntity.setType(dish.getType());
        dishEntity.setUrl(dish.getUrl());

        final DishEntity entity = dishService.save(dishEntity);
        model.addAttribute("dish", entity);
        return "redirect:/?update-dish=true";
    }

    @GetMapping("/delete/{id}")
    public String deleteDish(@PathVariable String id) {
        dishService.deleteById(id);
        return "redirect:/?delete-dish=true";
    }
}
