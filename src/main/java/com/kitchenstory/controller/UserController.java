package com.kitchenstory.controller;

import com.kitchenstory.entity.UserEntity;
import com.kitchenstory.exceptions.UserNotFoundException;
import com.kitchenstory.model.UserRole;
import com.kitchenstory.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final HttpServletRequest request;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("{id}")
    public String user(@PathVariable String id, Model model) {

        final UserEntity user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with Id: " + id + " not found"));
        final String email = request.getUserPrincipal().getName();

        if (!email.equals(user.getEmail()))
            return "redirect:/?user-unauthorized=true";

        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("all")
    public String allUsers(Model model) {
        final boolean is_admin = request.isUserInRole("ROLE_ADMIN");
        if (is_admin)
            model.addAttribute("users", userService.findAll());
        else {
            final String email = request.getUserPrincipal().getName();
            final UserEntity user = userService.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User with Email Id: " + email + " not found."));
            model.addAttribute("user", user);
            return "user";
        }
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
    public String updateUser(@Valid @ModelAttribute("user") UserEntity userEntity,
                             @PathVariable String id, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "update-user";
        final UserEntity user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with Id: " + id + " not found"));

        user.setEmail(userEntity.getEmail())
                .setName(userEntity.getName())
                .setGender(userEntity.getGender())
                .setAddress(userEntity.getAddress())
                .setCity(userEntity.getCity())
                .setState(userEntity.getState())
                .setZipcode(userEntity.getZipcode())
                .setTerms(userEntity.getTerms())
                .setAccountNonExpired(userEntity.getIsAccountNonExpired())
                .setCredentialsNonExpired(userEntity.getIsCredentialsNonExpired())
                .setAccountNonLocked(userEntity.getIsAccountNonLocked())
                .setEnabled(userEntity.getIsEnabled());

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

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userService.save(userEntity);
        return "redirect:/?sign-up=true";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String password, @RequestParam String confirmPassword) {
        System.out.println(password + " " + confirmPassword);
        if (!password.equals(confirmPassword))
            return "redirect:/?password-not-matching=true";
        final String email = request.getUserPrincipal().getName();
        final UserEntity user = userService.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with Email Id: " + email + " not found."));
        user.setPassword(passwordEncoder.encode(password));
        userService.save(user);
        return "redirect:/?change-password=true";
    }
}
