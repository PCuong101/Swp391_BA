package org.Scsp.com.service;

import org.Scsp.com.dto.TaskDTO;

import java.util.List;

public interface TaskService {


    List<TaskDTO> getTasksForUser(Long userId);


    void completeTask(Long userId, Long templateId);
}
