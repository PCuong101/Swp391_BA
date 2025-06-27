package org.Scsp.com.service.impl;

import org.Scsp.com.dto.UserRankingDTO;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.TaskCompletionRepository;
import org.Scsp.com.repository.UsersRepository;
import org.Scsp.com.service.QuitPlansService;
import org.Scsp.com.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankingServiceImpl implements RankingService {
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private QuitPlansService quitPlansService;

    @Autowired
    private TaskCompletionRepository taskRepo;

    public RankingServiceImpl(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserRankingDTO> getUserRanking() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    QuitPlan quitPlan = user.getQuitPlans();
                    if (quitPlan == null || quitPlan.getAverageCost() == null || quitPlan.getStartDate() == null) {
                        return null;
                    }

                    long quitDays = ChronoUnit.DAYS.between(
                            quitPlan.getStartDate().toLocalDate(),
                            LocalDate.now()
                    );

                    double moneySaved = quitPlansService.getSavingsByUserId(user.getUserId()).getTotalSavings().doubleValue();

//                    double moneySaved = quitPlan.getAverageCost().divide(BigDecimal.valueOf(20), 2, RoundingMode.HALF_UP)
//                            .multiply(BigDecimal.valueOf(quitPlan.getCigarettesPerDay()))
//                            .setScale(2, RoundingMode.HALF_UP)
//                            .doubleValue();


                    int tasksCompleted = taskRepo.countByUser_UserIdAndCompletedAtIsNotNull(user.getUserId());

                    return new UserRankingDTO(
                            user.getName(),
                            quitDays,
                            moneySaved,
                            tasksCompleted
                    );
                })
                .filter(ranking -> ranking != null)
                .sorted((a, b) -> {
                    int compareDays = Long.compare(b.getQuitDays(), a.getQuitDays());
                    if (compareDays != 0) return compareDays;

                    int compareMoney = Double.compare(b.getMoneySaved(), a.getMoneySaved());
                    if (compareMoney != 0) return compareMoney;

                    return Integer.compare(b.getTaskCompleted(), a.getTaskCompleted());
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<UserRankingDTO> getUserRankingMonney() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    QuitPlan quitPlan = user.getQuitPlans();
                    if (quitPlan == null || quitPlan.getAverageCost() == null || quitPlan.getStartDate() == null) {
                        return null;
                    }

                    long quitDays = ChronoUnit.DAYS.between(
                            quitPlan.getStartDate().toLocalDate(),
                            LocalDate.now()
                    );

                    double moneySaved = quitPlansService.getSavingsByUserId(user.getUserId()).getTotalSavings().doubleValue();

                    int tasksCompleted = taskRepo.countByUser_UserIdAndCompletedAtIsNotNull(user.getUserId());

                    return new UserRankingDTO(
                            user.getName(),
                            quitDays,
                            moneySaved,
                            tasksCompleted
                    );
                })
                .filter(ranking -> ranking != null)
                .sorted((a, b) -> {
                    int compareMoney = Double.compare(b.getMoneySaved(), a.getMoneySaved());
                    if (compareMoney != 0) return compareMoney;

                    int compareDays = Long.compare(b.getQuitDays(), a.getQuitDays());
                    if (compareDays != 0) return compareDays;

                    return Integer.compare(b.getTaskCompleted(), a.getTaskCompleted());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRankingDTO> getUserRankingMission() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    QuitPlan quitPlan = user.getQuitPlans();
                    if (quitPlan == null || quitPlan.getAverageCost() == null || quitPlan.getStartDate() == null) {
                        return null;
                    }

                    long quitDays = ChronoUnit.DAYS.between(
                            quitPlan.getStartDate().toLocalDate(),
                            LocalDate.now()
                    );

                    double moneySaved = quitPlansService.getSavingsByUserId(user.getUserId()).getTotalSavings().doubleValue();

                    int tasksCompleted = taskRepo.countByUser_UserIdAndCompletedAtIsNotNull(user.getUserId());
                    return new UserRankingDTO(
                            user.getName(),
                            quitDays,
                            moneySaved,
                            tasksCompleted
                    );
                })
                .filter(ranking -> ranking != null)
                .sorted((a, b) -> {
                    int compareTasks = Integer.compare(b.getTaskCompleted(), a.getTaskCompleted());
                    if (compareTasks != 0) return compareTasks;

                    return Long.compare(b.getQuitDays(), a.getQuitDays());
                })
                .collect(Collectors.toList());
    }

}
