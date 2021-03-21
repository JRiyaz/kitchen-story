package com.kitchenstory.config;

import com.kitchenstory.entity.CartEntity;
import com.kitchenstory.entity.DishEntity;
import com.kitchenstory.entity.UserEntity;
import com.kitchenstory.exceptions.UserNotFoundException;
import com.kitchenstory.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ControllerAdvice
@AllArgsConstructor
public class ModelData {

    private final UserService userService;
    private final HttpServletRequest request;

    @ModelAttribute("count")
    public Integer countOfDishesInCart() {
        return getDishes().isPresent() ? getDishes().get().size() : 0;
    }

    @ModelAttribute("dishes")
    public List<DishEntity> listOfDishes() {
        return getDishes().isPresent() ? getDishes().get() : null;
    }

    @ModelAttribute("total")
    public Double totalAmountOfAllDishes() {
        return getDishes().isPresent() ? getDishes().get()
                .stream()
                .map(dish -> dish.getPrice())
                .mapToDouble(Double::doubleValue)
                .sum() : 0.0;
    }

    @ModelAttribute("cards")
    public List<String> cards() {
        return Arrays.asList("Credit", "Debit");
    }

    private Optional<List<DishEntity>> getDishes() {

        final String uri = request.getRequestURI();
        if (uri.contains("/order/cart") || uri.contains("/order/add")) {
            final List<DishEntity> dishes = new ArrayList<>();

            try {
                final UserEntity user = userService.findByEmail("j.riyazu@gmail.com")
                        .orElseThrow(() -> new UserNotFoundException("User with Email Id: j.riyazu@gmail.com not found."));

                final Optional<CartEntity> cart = Optional.of(user.getCart());

                if (cart.isPresent())
                    dishes.addAll(cart.get().getDishes());

                return Optional.of(dishes);
            } catch (Exception exception) {
                return Optional.of(new ArrayList<>());
            }
        }
        return Optional.of(new ArrayList<>());
    }
}
