package org.Scsp.com.controller;

import org.Scsp.com.dto.TaskDTO;
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

    /**
     * Lấy danh sách nhiệm vụ cho người dùng dựa trên mức độ nghiện
     * @param userId ID người dùng
     * @return Danh sách nhiệm vụ tương ứng
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasks(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.getTasksForUser(userId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Đánh dấu nhiệm vụ là đã hoàn thành cho người dùng
     * @param userId ID người dùng
     * @param templateId ID nhiệm vụ mẫu
     * @return Thông báo thành công
     */
    @PostMapping("/complete/{userId}/{templateId}")
    public ResponseEntity<String> completeTask(
            @PathVariable Long userId,
            @PathVariable Long templateId
    ) {
        taskService.completeTask(userId, templateId);
        return ResponseEntity.ok("Nhiệm vụ đã được đánh dấu hoàn thành");
    }
}
