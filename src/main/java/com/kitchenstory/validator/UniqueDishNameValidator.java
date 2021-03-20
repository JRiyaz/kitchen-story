package com.kitchenstory.validator;

import com.kitchenstory.entity.DishEntity;
import com.kitchenstory.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueDishNameValidator implements ConstraintValidator<UniqueDishName, String> {

    @Autowired
    private DishService dishService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        final Optional<DishEntity> dish = dishService.findByName(value);
        return dish.isPresent() ? false : true;
    }
}
