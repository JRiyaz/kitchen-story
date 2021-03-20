package com.kitchenstory.controller;

import com.kitchenstory.entity.DishEntity;
import com.kitchenstory.service.DishService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private final DishService dishService;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("menu")
    public String menu(Model model) {
        final List<DishEntity> dishEntities = dishService.findAll();

        final Map<String, Integer> typeCount = dishEntities.stream()
                .collect(Collectors.groupingBy(DishEntity::getType, Collectors.summingInt(dish -> 1)));

        model.addAttribute("dishes", dishEntities);
        model.addAttribute("typeCount", typeCount);

        return "menu";
    }

}
