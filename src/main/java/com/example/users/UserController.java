package com.example.users;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/register", produces = "application/json")
    public Response registerUser(@RequestParam String email, @RequestParam String password, @RequestParam String role,
            @RequestParam String direccion) {
        User user = new User(email, password, role, direccion);
        return userService.registerUser(user);
    }

    @GetMapping(value = "/login", produces = "application/json")
    public Response loginUser(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }

    @GetMapping(value = "/users")
    public Collection<User> getUsers() {
        return userService.getAll();
    }
}
