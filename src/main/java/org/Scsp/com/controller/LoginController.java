package org.Scsp.com.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.Scsp.com.dto.UsersRegisterDto;
import org.Scsp.com.model.User;
import org.Scsp.com.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api")
@AllArgsConstructor
@RestController
public class LoginController {

    private final UsersService usersService;

    @GetMapping("/check-session")
    public ResponseEntity<?> checkSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            System.out.println("User found in session: " + user);
            return ResponseEntity.ok(Map.of("authenticated", true));
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> checkLogin(@RequestBody User user, HttpServletRequest request) {
        if(user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }
        User loggedInUser = usersService.loginUser(user);
        System.out.println(loggedInUser);
        if (loggedInUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UsersRegisterDto usersRegisterDto) {
        if(usersRegisterDto.getEmail() == null || usersRegisterDto.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }
        User registeredUser = usersService.registerUser(usersRegisterDto);
        if (registeredUser != null) {
            return ResponseEntity.ok(registeredUser);
        } else {
            return ResponseEntity.status(400).build();
        }
    }
}
