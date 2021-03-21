package com.kitchenstory.validator;

import com.kitchenstory.config.BeanUtil;
import com.kitchenstory.service.DishService;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//https://stackoverflow.com/questions/37958145/autowired-gives-null-value-in-custom-constraint-validator
//https://stackoverflow.com/questions/13350537/inject-service-in-constraintvalidator-bean-validator-jsr-303-spring
//https://stackoverflow.com/questions/46594706/inject-repository-inside-constraintvalidator-with-spring-4-and-message-interpola
//https://codingexplained.com/coding/java/hibernate/unique-field-validation-using-hibernate-spring
public class UniqueDishNameValidator implements ConstraintValidator<UniqueDishName, String> {

    private DishService dishService;
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueDishName constraintAnnotation) {
        dishService = BeanUtil.getBean(DishService.class);
        entityManager = BeanUtil.getBean(EntityManager.class);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = false;
        try {
            entityManager.setFlushMode(FlushModeType.COMMIT);
            //your code
            isValid = dishService.findByName(value).isPresent() ? false : true;
        } finally {
            entityManager.setFlushMode(FlushModeType.AUTO);
        }
        return isValid;
    }
}
