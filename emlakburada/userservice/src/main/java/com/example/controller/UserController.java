package com.example.controller;

import com.example.dto.UserRequestDTO;
import com.example.dto.UserResponseDTO;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO userRequestDTO) {
        log.info("Received request to save user: {}", userRequestDTO.getUsername());
        UserResponseDTO userResponseDTO = userService.saveUser(userRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id) {
        log.info("Received request to fetch user by ID: {}", id);
        UserResponseDTO userResponseDTO = userService.getUserById(id);

        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("Received request to fetch all users");
        List<UserResponseDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        log.info("Received request to delete user with ID: {}", id);
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserRequestDTO userRequestDTO) {
        log.info("Received request to Update user with ID: {}", id);
        UserResponseDTO userResponseDTO = userService.updateUser(id, userRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
}
