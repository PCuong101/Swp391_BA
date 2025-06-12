package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.dto.QuitPlanDto;
import org.Scsp.com.model.QuitPlans;
import org.Scsp.com.model.Users;
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
    public List<QuitPlans> findAll() {
        return List.of();
    }

    @Override
    public QuitPlans findById(Long id) {
        return quitPlanRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Quit plan not found with id: " + id)
        );
    }

    @Override
    public QuitPlans createPlane(QuitPlanDto quitPlanDto) {
        Users user = usersRepository.findById(quitPlanDto.getUserId()).orElseThrow(
                () -> new RuntimeException("User not found with id: " + quitPlanDto.getUserId())
        );
        QuitPlans quitPlans = new QuitPlans();
        quitPlans.setUser(user);
        quitPlans.setReason(quitPlanDto.getReason());
        quitPlans.setStartDate(quitPlanDto.getStartDate());
        quitPlans.setExpectedQuitDate(quitPlanDto.getExpectedQuitDate());
        quitPlans.setPersonalizedNotes(quitPlanDto.getPersonalizedNotes());
        quitPlans.setCigarettesPerDay(quitPlanDto.getCigarettesPerDay());
        quitPlans.setSmokingFrequency(quitPlanDto.getSmokingFrequency());
        quitPlans.setAverageCost(quitPlanDto.getAverageCost());
        quitPlans.setStartedSmokingAt(quitPlanDto.getStartedSmokingAt());


        QuitPlans savedPlan = quitPlanRepository.save(quitPlans);
        healthMilestoneService.createHealthMilestones(savedPlan);

        return savedPlan;
    }




    @Override
    public void deleteById(Long id) {

    }
}
