package org.Scsp.com.repository;

import org.Scsp.com.Enum.AddictionLevel;
import org.Scsp.com.model.TaskTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long> {
    List<TaskTemplate> findByAddictionLevel(AddictionLevel level);

}
