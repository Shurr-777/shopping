package com.example.shopping.controller;

import com.example.shopping.entity.data.User;
import com.example.shopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {
    @Autowired

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String register(User user) {

        return "signup";
    }

    @PostMapping("/signup")
    public String register(@Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("passwordMatchProblem", "Passwords do not match!");
            return "signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/login";
    }
}
