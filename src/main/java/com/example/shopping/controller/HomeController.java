package com.example.shopping.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/someRandomPage")
    public String home() {
        return "home";
    }
}