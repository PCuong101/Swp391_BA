package org.Scsp.com;

import org.Scsp.com.controller.SavingResponseDto;
import org.Scsp.com.model.QuitPlan;
import org.Scsp.com.model.UserDailyLog;
import org.Scsp.com.repository.QuitPlanRepository;
import org.Scsp.com.service.impl.QuitPlansServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuitPlansServiceImplTest {

    @Mock
    private QuitPlanRepository quitPlanRepository;

    @InjectMocks
    private QuitPlansServiceImpl quitPlansService;

    private QuitPlan quitPlan;
    private final Long userId = 1L;

    @BeforeEach
    void setUp() {
        // Setup basic quit plan
        quitPlan = new QuitPlan();
        quitPlan.setAverageCost(BigDecimal.valueOf(10.00)); // $10 per packet
        quitPlan.setCigarettesPerDay(20);
        quitPlan.setStartDate(LocalDateTime.now().minusDays(10)); // Started 10 days ago
        quitPlan.setUserDailyLogs(new ArrayList<>());
    }

    @Test
    void getSavingsByUserId_SuccessfulRetrieval() {
        // Given
        when(quitPlanRepository.findByUser_UserId(userId)).thenReturn(Optional.of(quitPlan));

        // Add some daily logs
        List<UserDailyLog> dailyLogs = new ArrayList<>();
        UserDailyLog log1 = new UserDailyLog();
        log1.setSmokedToday(true);
        log1.setCigarettesSmoked(5);
        log1.setSpentMoneyOnNtr(10);
        dailyLogs.add(log1);
        quitPlan.setUserDailyLogs(dailyLogs);

        // When
        SavingResponseDto result = quitPlansService.getSavingsByUserId(userId);

        // Then
        assertNotNull(result);
        assertNotNull(result.getTotalSavings());
        assertNotNull(result.getTotalSpentOnNrt());
        assertNotNull(result.getTotalSpentOnCigarettes());
        assertNotNull(result.getMoneyPerDay());
        assertNotNull(result.getMoneyPerWeek());
        assertNotNull(result.getMoneyPerMonth());
        assertNotNull(result.getMoneyPerYear());
        
        // Verify calculations
        assertEquals(BigDecimal.TEN, result.getTotalSpentOnNrt());
        assertTrue(result.getMoneyPerWeek().compareTo(result.getMoneyPerDay().multiply(BigDecimal.valueOf(7))) == 0);
        assertTrue(result.getMoneyPerMonth().compareTo(result.getMoneyPerDay().multiply(BigDecimal.valueOf(30))) == 0);
        assertTrue(result.getMoneyPerYear().compareTo(result.getMoneyPerDay().multiply(BigDecimal.valueOf(365))) == 0);
    }

    @Test
    void getSavingsByUserId_QuitPlanNotFound() {
        // Given
        when(quitPlanRepository.findByUser_UserId(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> quitPlansService.getSavingsByUserId(userId));
        verify(quitPlanRepository).findByUser_UserId(userId);
    }

    @Test
    void getSavingsByUserId_WithNoSmokingLogs() {
        // Given
        when(quitPlanRepository.findByUser_UserId(userId)).thenReturn(Optional.of(quitPlan));
        quitPlan.setUserDailyLogs(new ArrayList<>()); // Empty logs

        // When
        SavingResponseDto result = quitPlansService.getSavingsByUserId(userId);

        // Then
        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, result.getTotalSpentOnNrt());
        assertEquals(BigDecimal.ZERO, result.getTotalSpentOnCigarettes());
        assertTrue(result.getTotalSavings().compareTo(BigDecimal.ZERO) > 0);
    }

}