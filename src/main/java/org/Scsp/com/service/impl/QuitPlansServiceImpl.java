package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.dto.QuitPlanDto;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.model.User;
import org.Scsp.com.model.UserDailyLog;
import org.Scsp.com.repository.QuitPlanRepository;
import org.Scsp.com.repository.UsersRepository;
import org.Scsp.com.service.HealthMilestoneService;
import org.Scsp.com.service.QuitPlansService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public QuitPlan createPlan(QuitPlanDto quitPlanDto) {
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
        quitPlan.setYearsSmoking(quitPlan.getYearsSmoking());


        QuitPlan savedPlan = quitPlanRepository.save(quitPlan);
        healthMilestoneService.createHealthMilestones(savedPlan);

        return savedPlan;
    }




    @Override
    public void deleteById(Long id) {

    }

    @Override
    public BigDecimal getSavingsByUserId(Long userId) {
        QuitPlan quitPlan = quitPlanRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("No quit plan found for user with id: " + userId));
        BigDecimal averageCostPerCigarettes = quitPlan.getAverageCost();
        int cigarettesPerDay = quitPlan.getCigarettesPerDay();
        BigDecimal savings = averageCostPerCigarettes.multiply(BigDecimal.valueOf(cigarettesPerDay));
        BigDecimal totalSpent = calculateTotalSpentOnCigarettes(quitPlan);

        return savings.subtract(totalSpent);
    }

    public BigDecimal calculateTotalSpentOnCigarettes(QuitPlan quitPlan) {
        return quitPlan.getUserDailyLogs().stream()
                .filter(dailyLog -> Boolean.TRUE.equals(dailyLog.getSmokedToday()))
                .map(dailyLog -> {
                    Integer price = dailyLog.getSpentMoneyOnCigarettes();
                    Integer count = dailyLog.getCigarettesSmoked();
                    return (price != null && count != null)
                            ? BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(count))
                            : BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
