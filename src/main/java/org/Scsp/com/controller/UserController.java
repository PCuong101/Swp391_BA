package org.Scsp.com.controller;

import lombok.AllArgsConstructor;
import org.Scsp.com.model.Users;
import org.Scsp.com.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UsersService usersService;


    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody User user) {
        return ResponseEntity.ok(usersService.saveUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Optional<Users> user = usersService.getUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (!usersService.getUserById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        user.setUserId(id);
        return ResponseEntity.ok(usersService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!usersService.getUserById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}