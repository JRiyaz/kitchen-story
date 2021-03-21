package com.kitchenstory.validator;

import com.kitchenstory.config.BeanUtil;
import com.kitchenstory.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//https://stackoverflow.com/questions/37958145/autowired-gives-null-value-in-custom-constraint-validator
//https://stackoverflow.com/questions/13350537/inject-service-in-constraintvalidator-bean-validator-jsr-303-spring
//https://stackoverflow.com/questions/46594706/inject-repository-inside-constraintvalidator-with-spring-4-and-message-interpola
//https://codingexplained.com/coding/java/hibernate/unique-field-validation-using-hibernate-spring
public class UniqueEmailIdValidator implements ConstraintValidator<UniqueEmailId, String> {

    private UserService userService;
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueEmailId constraintAnnotation) {
        userService = BeanUtil.getBean(UserService.class);
        entityManager = BeanUtil.getBean(EntityManager.class);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = false;
        try {
            entityManager.setFlushMode(FlushModeType.COMMIT);
            //your code
            isValid = userService.findByEmail(value).isPresent() ? false : true;
        } finally {
            entityManager.setFlushMode(FlushModeType.AUTO);
        }
        return isValid;
    }
}
