package com.marketplace.demo.controllers;

import com.marketplace.demo.configs.UsernameNotValidException;
import com.marketplace.demo.dtos.UserDto;
import com.marketplace.demo.models.User;
import com.marketplace.demo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user/")
public class UserController {

    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public List<User> getAll(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getPublicUser(@PathVariable("id") Long id){
        return userService.getPublicUser(id);
    }

    @GetMapping("/me")
    public UserDto getMyUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getMyUser(email);
    }

    @PostMapping
    public void createUser(@RequestBody User user)  {
        userService.addUser(user);
    }


    
}
