package net.javaguides.springbootrestapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RestController = @Controller + @ResponseBody + other annotations
public class HelloWordController {
    //handle http GET request
    //http://localhost:8080/hello-word
    @GetMapping("/hello-word")
    public String helloWorld() {
        return "Hello, World!";
    }
}
