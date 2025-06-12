package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.dto.QuitPlanDto;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.QuitPlanRepository;
import org.Scsp.com.repository.UsersRepository;
import org.Scsp.com.service.HealthMilestoneService;
import org.Scsp.com.service.QuitPlansService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuitPlansServiceImpl implements QuitPlansService {


    private final QuitPlanRepository quitPlanRepository;
    private final UsersRepository usersRepository;
    private final HealthMilestoneService healthMilestoneService;


    @Override
    public List<QuitPlan> findAll() {
        return List.of();
    }

    @Override
    public QuitPlan findById(Long id) {
        return quitPlanRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Quit plan not found with id: " + id)
        );
    }

    @Override
    public QuitPlan createPlane(QuitPlanDto quitPlanDto) {
        User user = usersRepository.findById(quitPlanDto.getUserId()).orElseThrow(
                () -> new RuntimeException("User not found with id: " + quitPlanDto.getUserId())
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
        healthMilestoneService.createHealthMilestones(savedPlan);

        return savedPlan;
    }




    @Override
    public void deleteById(Long id) {

    }
}
