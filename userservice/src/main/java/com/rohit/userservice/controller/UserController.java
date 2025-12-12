package com.rohit.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/public")
public class UserController {

    @GetMapping("/all")
    public String getAll(@RequestHeader("X-Auth-User") String username){
        return "Request handled for user: " + username;
    }
}
