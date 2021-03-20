package com.kitchenstory.validator;

import com.kitchenstory.entity.UserEntity;
import com.kitchenstory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueEmailIdValidator implements ConstraintValidator<UniqueEmailId, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        final Optional<UserEntity> user = userService.findByEmail(value);

        return user.isPresent() ? false : true;
    }
}
