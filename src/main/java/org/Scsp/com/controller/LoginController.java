package org.Scsp.com.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.Scsp.com.dto.UsersRegisterDto;
import org.Scsp.com.dto.LoginRequest;
import org.Scsp.com.model.User;
import org.Scsp.com.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/auth")
@AllArgsConstructor
@RestController
public class LoginController {

    private final UsersService usersService;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/get-session-user")
    public ResponseEntity<?> getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(401).build();
        }
        User user = (User) session.getAttribute("user");
        if (user != null) {
            System.out.println("User found in session: " + user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/refresh-session")
    public ResponseEntity<?> refreshSessionUser(HttpSession session) {
        // Lấy userId từ session hiện tại
        User loggedInUser = (User) session.getAttribute("user");

        if (loggedInUser.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Gọi service để lấy lại thông tin mới nhất
        User user = usersService.getUserById(loggedInUser.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        // Ghi đè lại session user
        session.setAttribute("user", user);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> checkLogin(@RequestBody LoginRequest user, HttpServletRequest request) {
        if(user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }
        User loggedInUser = usersService.loginUser(user);
        if (loggedInUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", loggedInUser);
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
