package com.abdullahturhan.controller;

import com.abdullahturhan.dto.CreateUserRequest;
import com.abdullahturhan.dto.CreateUserResponse;
import com.abdullahturhan.dto.SearchUserResponse;
import com.abdullahturhan.exception.UserNotFoundException;
import com.abdullahturhan.model.Role;
import com.abdullahturhan.model.User;
import com.abdullahturhan.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/id")
    public ResponseEntity<SearchUserResponse> getUserById(@RequestParam ("id") String id) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findUserById(id));
    }
    @GetMapping(path = "/email")
    public ResponseEntity<SearchUserResponse> getUserByEmail(@RequestParam ("email") String email) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findUserByEmail(email));
    }
    @GetMapping(path = "/via")
    public ResponseEntity<User> getUserViaEmail(@RequestParam ("email") String email) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserByEmail(email));
    }

    @GetMapping(path = "/role")
    public ResponseEntity<List<SearchUserResponse>> getUsersByRole(@RequestParam("role") Role role) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findUserByRole(role));
    }

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateUserResponse> create(@RequestBody  CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }


    @DeleteMapping(path = "/delete")
    public void deleteUser(@RequestParam("id") String id) throws UserNotFoundException {
        userService.deleteUserById(id);
    }
}
