package org.seydaliev.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.seydaliev.exceptions.ResourceNotFoundException;
import org.seydaliev.model.Users;
import org.seydaliev.repository.UsersRepository;
import org.seydaliev.service.UsersService;
import org.seydaliev.service.Views;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
public class UsersController {
    private final UsersService usersService;

    private final UsersRepository usersRepository;

    public UsersController(UsersService usersService, UsersRepository usersRepository) {
        this.usersService = usersService;
        this.usersRepository = usersRepository;
    }

    @JsonView(Views.UserSummary.class)
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return new ResponseEntity<>(usersService.findAll(), HttpStatus.OK);
    }

    @JsonView(Views.UserDetails.class)
    @GetMapping("/{id}")
    public Users getUserById(@PathVariable Long id) {
        return usersRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @JsonView(Views.UserSummary.class)
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody Users user) {
        Users createdUser = usersService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @JsonView(Views.UserSummary.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @JsonView(Views.UserSummary.class)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id,
                                        @Valid @RequestBody Users updatedUser) {
        Users user = usersService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }
}
