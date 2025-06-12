package org.Scsp.com.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.Scsp.com.Enum.Role;
import org.Scsp.com.dto.SurveyRegisterDTO;
import org.Scsp.com.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/survey-register")
@RestController
public class SurveyRegisterController {

    @Autowired
    private UsersController userController;

    @PostMapping("/register")
    public ResponseEntity<?> getSurveyAndRegister(@RequestBody SurveyRegisterDTO surveyRegisterDTO, HttpServletRequest request) {
        int mark = 0;
        switch (surveyRegisterDTO.getFirstSmokeTime()) {
            case "within 5 minutes":
                mark += 3;
                break;
            case "6-30 minutes":
                mark += 2;
                break;
            case "after 60 minutes":
                mark += 1;
                break;
            case "don't smoke":
                mark += 0;
                break;
        }

        if(surveyRegisterDTO.getCigarettesPerDay() >= 30) {
            mark += 3;
        } else if(surveyRegisterDTO.getCigarettesPerDay() >= 21) {
            mark += 2;
        } else if(surveyRegisterDTO.getCigarettesPerDay() <= 11) {
            mark += 1;
        } else {
            mark += 0;
        }
        Users user = new Users();
        user.setEmail(surveyRegisterDTO.getEmail());
        user.setName(surveyRegisterDTO.getUsername());
        user.setPassword(surveyRegisterDTO.getPassword());
        if (mark >= 6) {
            user.setAddictionLevel("Heavy");
        } else if (mark >= 4) {
            user.setAddictionLevel("Medium");
        } else if (mark >= 2) {
            user.setAddictionLevel("Light");
        } else {
            user.setAddictionLevel("None");
        }
        user.setRole(Role.valueOf("MEMBER"));
        userController.createUser(user);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return ResponseEntity.ok("Survey Register Data");
    }
}
