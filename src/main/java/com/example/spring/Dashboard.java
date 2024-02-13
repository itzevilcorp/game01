package com.example.spring;


import org.springframework.web.bind.annotation.GetMapping;

public class Dashboard {

    @GetMapping("/")
    public String index() {
        return "dashboard";
    }
}