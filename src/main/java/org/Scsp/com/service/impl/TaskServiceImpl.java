package org.Scsp.com.service.impl;

import org.Scsp.com.Enum.AddictionLevel;
import org.Scsp.com.dto.TaskDTO;
import org.Scsp.com.model.*;
import org.Scsp.com.repository.*;
import org.Scsp.com.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskTemplateRepository templateRepo;

    @Autowired
    private TaskCompletionRepository completionRepo;

    @Autowired
    private UsersRepository userRepo;

    @Override
    public List<TaskDTO> getTasksForUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        AddictionLevel level = user.getAddictionLevel();

        List<TaskTemplate> templates = templateRepo.findByAddictionLevel(level);

        return templates.stream().map(template -> {
            TaskDTO dto = new TaskDTO();
            dto.setTemplateID(template.getTemplateID());
            dto.setTitle(template.getTitle());
            dto.setDescription(template.getDescription());
            dto.setSuggestedDay(template.getSuggestedDay());
            dto.setAddictionLevel(template.getAddictionLevel());
            boolean done = completionRepo.existsByUserAndTemplate(user, template);
            dto.setCompleted(done);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void completeTask(Long userId, Long templateId) {
        User user = userRepo.findById(userId).orElseThrow();
        TaskTemplate template = templateRepo.findById(templateId).orElseThrow();

        if (!completionRepo.existsByUserAndTemplate(user, template)) {
            TaskCompletion completion = new TaskCompletion();
            completion.setUser(user);
            completion.setTemplate(template);
            completionRepo.save(completion);
        }
    }
}
