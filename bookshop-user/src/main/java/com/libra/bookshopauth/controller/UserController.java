package com.libra.bookshopauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
public class UserController {


    @GetMapping
    public String getAllUsers() {
        return "Hello from user controller (Method getAllUsers())";
    }

    @GetMapping(path = "/admin")
    public String getAdmin() {
        return "Hello from user controller (Method getAdmin()) \n You are admin";
    }
}
