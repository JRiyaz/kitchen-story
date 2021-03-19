package com.kitchenstory.controller;

import com.kitchenstory.entity.DishEntity;
import com.kitchenstory.service.DishService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URL;

@Controller
@RequestMapping("/dish")
@AllArgsConstructor
public class DishController {

    private final DishService dishService;

    @GetMapping("add")
    public String showAddTemplate(DishEntity dishEntity) {
        return "add-dish";
    }

    @PostMapping("add")
    @ResponseBody
    public DishEntity addDish(@Valid final DishEntity dish, BindingResult result) throws IOException {

        if (result.hasErrors())
            return null;

        final byte[] image = IOUtils.toByteArray(new URL(dish.getUrl()));
        dish.setImage(image);

        return dishService.save(dish);
    }
}
