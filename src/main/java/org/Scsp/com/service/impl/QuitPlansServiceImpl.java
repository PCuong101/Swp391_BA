package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.dto.SavingResponseDto;
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
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

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
    public SavingResponseDto getSavingsByUserId(Long userId) {
        QuitPlan quitPlan = quitPlanRepository.findByUser_UserId(userId).orElseThrow(() ->  new RuntimeException("Quit plan not found with id: " + userId));
        BigDecimal costPerPacket = quitPlan.getAverageCost(); // Giá 1 bao thuốc
        int cigarettesPerDay = quitPlan.getCigarettesPerDay(); // Số điếu/ngày

        BigDecimal pricePerCigarette = costPerPacket.divide(BigDecimal.valueOf(20), 2, RoundingMode.HALF_UP); // 20 điếu/bao

        BigDecimal savingPerDay = pricePerCigarette.multiply(BigDecimal.valueOf(cigarettesPerDay)); // Tiền tiết kiệm/ngày
        BigDecimal totalSpentOnCigarettes = calculateTotalSpentOnCigarettes(quitPlan,pricePerCigarette);

        int daysSinceStart = (int) quitPlan.getStartDate().until(LocalDateTime.now(), ChronoUnit.DAYS) + 1;

        BigDecimal totalSpentOnNrt = calculateTotalSpentOnNrt(quitPlan);
        BigDecimal savings = savingPerDay.multiply(BigDecimal.valueOf(daysSinceStart)).subtract(totalSpentOnCigarettes).subtract(totalSpentOnNrt); // Tổng tiền tiết kiệm;

        return SavingResponseDto.builder()
                .totalSavings(savings)
                .totalSpentOnNrt(totalSpentOnNrt)
                .totalSpentOnCigarettes(totalSpentOnCigarettes)
                .moneyPerDay(savingPerDay)
                .moneyPerWeek(savingPerDay.multiply(BigDecimal.valueOf(7)))
                .moneyPerMonth(savingPerDay.multiply(BigDecimal.valueOf(30)))
                .moneyPerYear(savingPerDay.multiply(BigDecimal.valueOf(365)))
                .build();
    }

    public BigDecimal calculateTotalSpentOnNrt(QuitPlan quitPlan) {
        return quitPlan.getUserDailyLogs().stream()
                .map(UserDailyLog::getSpentMoneyOnNtr)
                .filter(Objects::nonNull)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalSpentOnCigarettes(QuitPlan quitPlan,BigDecimal pricePerCigarette) {
        return quitPlan.getUserDailyLogs().stream()
                .filter(dailyLog -> Boolean.TRUE.equals(dailyLog.getSmokedToday()))
                .map(dailyLog -> {
                    Integer count = dailyLog.getCigarettesSmoked();
                    return (pricePerCigarette != null && count != null)
                            ? pricePerCigarette.multiply(BigDecimal.valueOf(count))
                            : BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
