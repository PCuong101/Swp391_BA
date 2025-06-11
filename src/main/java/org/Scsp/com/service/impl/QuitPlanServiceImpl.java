package org.Scsp.com.service.impl;

import org.Scsp.com.dto.QuitPlanDto;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.QuitPlanRepository;
import org.Scsp.com.repository.UserRepository;
import org.Scsp.com.service.HealthMilestoneService;
import org.Scsp.com.service.QuitPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuitPlanServiceImpl implements QuitPlanService {


    private final QuitPlanRepository quitPlanRepository;
    private final UserRepository userRepository;
    private final HealthMilestoneService milestoneService;

    @Autowired
    public QuitPlanServiceImpl(QuitPlanRepository quitPlanRepository, UserRepository userRepository, HealthMilestoneService milestoneService) {
        this.quitPlanRepository = quitPlanRepository;
        this.userRepository = userRepository;
        this.milestoneService = milestoneService;
    }

    @Override
    public List<QuitPlan> findAll() {
        return List.of();
    }

    @Override
    public QuitPlan findById(Long id) {
        return null;
    }

    @Override
    public QuitPlan createPlane(Long userId, QuitPlanDto quitPlanDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found with id: " + userId)
        );
        QuitPlan quitPlan = new QuitPlan();
        quitPlan.setUser(user);
        quitPlan.setReason(quitPlanDto.getReason());
        quitPlan.setStartDate(quitPlanDto.getStartDate());
        quitPlan.setExpectedQuitDate(quitPlanDto.getExpectedQuitDate());
        quitPlan.setPersonalizedNotes(quitPlanDto.getPersonalizedNotes());
        quitPlan.setCigarettesPerDay(quitPlanDto.getCigarettesPerDay());
        quitPlan.setSmokingFrequency(quitPlanDto.getSmokingFrequency());
        quitPlan.setAverageCost(quitPlanDto.getAverageCost());
        quitPlan.setStartedSmokingAt(quitPlanDto.getStartedSmokingAt());


        QuitPlan savedPlan = quitPlanRepository.save(quitPlan);
        milestoneService.createHealthMilestones(savedPlan);

        return savedPlan;
    }


    @Override
    public void deleteById(Long id) {

    }
}
