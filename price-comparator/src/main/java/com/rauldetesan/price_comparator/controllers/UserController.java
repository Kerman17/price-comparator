package com.rauldetesan.price_comparator.controllers;

import com.rauldetesan.price_comparator.domain.User;
import com.rauldetesan.price_comparator.dtos.UserDTOS.UserResponseDTO;
import com.rauldetesan.price_comparator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("{userId}")
    public UserResponseDTO getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @PostMapping
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }
}
