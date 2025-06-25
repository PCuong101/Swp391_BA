package org.Scsp.com.repository;

import org.Scsp.com.Enum.AddictionLevel;
import org.Scsp.com.model.TaskCompletion;
import org.Scsp.com.model.TaskTemplate;
import org.Scsp.com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskCompletionRepository extends JpaRepository<TaskCompletion, Integer> {
    List<TaskCompletion> findByUser(User user);
    boolean existsByUserAndTemplate(User user, TaskTemplate template);
    long countByUserUserIdAndCompletedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    int countByUser_UserIdAndCompletedAtIsNotNull(Long userId);
    List<TaskCompletion> findByUser_UserId(Long userId);

}
