package org.Scsp.com.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.Scsp.com.Enum.AddictionLevel;
import org.Scsp.com.Enum.MemberPlanSubscriptionStatus;
import org.Scsp.com.Enum.Role;
import org.Scsp.com.dto.QuitPlanDto;
import org.Scsp.com.dto.SurveyRegisterDTO;
import org.Scsp.com.dto.UsersRegisterDto;
import org.Scsp.com.model.MemberPlanSubscription;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.MemberPlanSubscriptionRepository;
import org.Scsp.com.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequestMapping("/api/survey-register")
@RestController
public class SurveyRegisterController {

    @Autowired
    private UsersController userController;

    @Autowired
    private QuitPlansController quitPlansController;

    @Autowired
    private LoginController loginController;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MemberPlanSubscriptionRepository memberPlanSubscriptionRepository;

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
        try {
            UsersRegisterDto user = new UsersRegisterDto();
            user.setEmail(surveyRegisterDTO.getEmail());
            user.setName(surveyRegisterDTO.getUsername());
            user.setPassword(surveyRegisterDTO.getPassword());
            if (mark >= 6) {
                user.setAddictionLevel(AddictionLevel.HIGH);
            } else if (mark >= 4) {
                user.setAddictionLevel(AddictionLevel.MEDIUM);
            } else if (mark >= 2) {
                user.setAddictionLevel(AddictionLevel.LOW);
            } else {
                user.setAddictionLevel(AddictionLevel.NONE);
            }

            if(loginController.registerUser(user) != null) {
                QuitPlanDto quitPlan = new QuitPlanDto();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                User registeredUser = usersRepository.findByEmail(user.getEmail()).orElse(null);
                quitPlan.setAverageCost(BigDecimal.valueOf(surveyRegisterDTO.getPackPrice()));
                quitPlan.setYearsSmoking(surveyRegisterDTO.getYearsSmoking());
                quitPlan.setStartDate(LocalDateTime.parse(surveyRegisterDTO.getDateStart(), formatter));
                quitPlan.setStartedSmokingAt(surveyRegisterDTO.getFirstSmokeTime());
                quitPlan.setUserId(registeredUser.getUserId());
                quitPlan.setCigarettesPerDay(surveyRegisterDTO.getCigarettesPerDay());
                quitPlan.setReason(String.join(",", surveyRegisterDTO.getReason()));
                quitPlansController.createQuitPlan(quitPlan);
                User userLoggedIn = usersRepository.findByEmail(user.getEmail()).orElse(null);

                HttpSession session = request.getSession();
                session.setAttribute("user", userLoggedIn);
                return ResponseEntity.ok(userLoggedIn);
            }
            else {
                return ResponseEntity.status(400).body("User registration failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
