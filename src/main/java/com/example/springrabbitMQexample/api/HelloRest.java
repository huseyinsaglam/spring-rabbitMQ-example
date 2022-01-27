package com.example.springrabbitMQexample.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloRest {

    @GetMapping("/hello")
    public String getMessage() {
        return "Hello";
    }


}
