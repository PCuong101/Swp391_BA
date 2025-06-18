package org.Scsp.com.repository;

import org.Scsp.com.model.TaskCompletion;
import org.Scsp.com.model.TaskTemplate;
import org.Scsp.com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCompletionRepository extends JpaRepository<TaskCompletion, Integer> {
    List<TaskCompletion> findByUser(User user);
    boolean existsByUserAndTemplate(User user, TaskTemplate template);
}
