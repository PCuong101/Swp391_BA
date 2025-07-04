package org.Scsp.com.controller;

import org.Scsp.com.dto.CompletedTaskDTO;
import org.Scsp.com.dto.TaskDTO;
import org.Scsp.com.dto.TaskCompletionStatsDTO;
import org.Scsp.com.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")

public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasks(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.getTasksForUserToday(userId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/complete/{userId}/{templateId}")
    public ResponseEntity<String> completeTask(
            @PathVariable Long userId,
            @PathVariable Long templateId
    ) {
        taskService.completeTask(userId, templateId);
        return ResponseEntity.ok("✅ Nhiệm vụ đã được đánh dấu hoàn thành");
    }

    @GetMapping("/progress/{userId}")
    public ResponseEntity<TaskCompletionStatsDTO> getDailyProgress(@PathVariable Long userId) {
        TaskCompletionStatsDTO stats = taskService.getCompletionStats(userId);
        return ResponseEntity.ok(stats);
    }
    @GetMapping("/completed/{userId}")
    public ResponseEntity<List<CompletedTaskDTO>> getCompletedTasks(@PathVariable Long userId) {
        List<CompletedTaskDTO> completedTasks = taskService.getCompletedTasksByUserId(userId);
        return ResponseEntity.ok(completedTasks);
    }
}
