package com.kitchenstory.controller;

import com.kitchenstory.entity.UserEntity;
import com.kitchenstory.exceptions.UserNotFoundException;
import com.kitchenstory.model.UserRole;
import com.kitchenstory.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{id}")
    public String user(@PathVariable String id, Model model) {
        final UserEntity user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with Id: " + id + " not found"));
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("all")
    public String allUsers(Model model) {
        final List<UserEntity> users = new ArrayList<>();
        users.addAll(userService.findAll());
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("edit/{id}")
    public String editUser(@PathVariable String id, Model model) {
        final UserEntity user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with Id: " + id + " not found"));
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("update/{id}")
    public String updateUser(@Valid @ModelAttribute("user") UserEntity userEntity, @PathVariable String id, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "update-user";
        userEntity.setId(id);
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userService.save(userEntity);
        return "redirect:/user/all?update-user=true";
    }

    @GetMapping("sign-up")
    public String signUp(Model model) {
        model.addAttribute("userEntity", new UserEntity());
        return "sign-up";
    }

    @PostMapping("sign-up")
    public String signUp(@Valid UserEntity userEntity, BindingResult result) {
        if (result.hasErrors())
            return "sign-up";

        if (userEntity.getEmail().equals("j.riyazu@gmail.com")) {
            userEntity.setUserRole(UserRole.ROLE_ADMIN);
            userEntity.setIsAccountNonExpired(true);
            userEntity.setIsAccountNonLocked(true);
            userEntity.setIsCredentialsNonExpired(true);
            userEntity.setIsEnabled(true);
        } else
            userEntity.setUserRole(UserRole.ROLE_USER);

//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userService.save(userEntity);
        return "redirect:/?sign-up=true";
    }
}