package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.Scsp.com.Enum.MemberPlanSubscriptionStatus;
import org.Scsp.com.Enum.Role;
import org.Scsp.com.dto.CoachDTO;
import org.Scsp.com.dto.DashboardSummaryDto;
import org.Scsp.com.dto.ScheduleDTO;
import org.Scsp.com.model.Schedule;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.MemberPlanSubscriptionRepository;
import org.Scsp.com.repository.UsersRepository;
import org.Scsp.com.service.DashboardService;
import org.Scsp.com.service.MemberPlanSubscriptionService;
import org.Scsp.com.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
@Builder
public class DashboardServiceImpl implements DashboardService {

    private final UsersRepository userRepository;
    private  final ScheduleService scheduleService;
    private  final MemberPlanSubscriptionRepository memberPlanSubscriptionRepository;

    @Override
    public DashboardSummaryDto getDashboardSummary() {
        long totalUsers = userRepository.count();
        LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().plusMonths(1).withDayOfMonth(1).atStartOfDay();
        BigDecimal monthlyRevenue = memberPlanSubscriptionRepository.getRevenueBetween(MemberPlanSubscriptionStatus.ACTIVE, start, end);

        return DashboardSummaryDto.builder()
                .totalUsers(totalUsers)
                .monthlyRevenue(monthlyRevenue)
                .build();
    }

    @Override
    public List<CoachDTO> getCoaches() {
        List<User> coaches = userRepository.findByRole(Role.COACH);
        return coaches.stream()
                .map(coach -> CoachDTO.builder()
                        .id(coach.getUserId())
                        .name(coach.getName())
                        .email(coach.getEmail())
                        .build())
                .toList();
    }

    @Override
    public CoachDTO getCoachById(Long id) {
        User coach = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Coach not found with id: " + id));
        List<ScheduleDTO> schedules = scheduleService.getCoachSchedules(coach.getUserId());
        return CoachDTO.builder()
                .id(coach.getUserId())
                .name(coach.getName())
                .email(coach.getEmail())
                .schedules(schedules)
                .build();
    }


}
