package org.Scsp.com.service.impl;

import org.Scsp.com.dto.ScheduleDTO;
import org.Scsp.com.dto.ScheduleOverviewDTO;
import org.Scsp.com.model.Booking;
import org.Scsp.com.model.Schedule;
import org.Scsp.com.repository.ScheduleRepository;
import org.Scsp.com.repository.UsersRepository;
import org.Scsp.com.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<ScheduleDTO> getCoachSchedules(Long coachId) {
        String coachName = usersRepository.findUserByUserId(coachId).getName();
        List<Schedule> schedules = scheduleRepository
                .findByCoach_UserIdAndDateAfterAndDateBeforeAndIsPublishedTrue(
                        coachId,
                        LocalDate.now(),
                        LocalDate.now().plusDays(7)
                );

        return schedules.stream()
                .map(schedule -> new ScheduleDTO(
                        schedule.getSchedulesID(),
                        coachName,
                        schedule.getSlot().getSlotID().toString(),
                        schedule.getDate(),
                        schedule.isAvailable()
                ))
                .toList();
    }
    @Override
    public List<ScheduleDTO> getPublishedSchedules(Long coachId) {
        List<Schedule> schedules = scheduleRepository.findByCoachUserIdAndDateAfterAndDateBeforeAndIsPublishedTrue(
                coachId,
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        );

        return schedules.stream()
                .map(s -> new ScheduleDTO(
                        s.getSchedulesID(),
                        s.getCoach().getName(),
                        s.getSlot().getLabel(),
                        s.getDate(),
                        s.isAvailable()
                ))
                .collect(Collectors.toList());
    }





}
