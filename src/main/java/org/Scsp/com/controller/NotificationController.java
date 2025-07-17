package org.Scsp.com.controller;

import org.Scsp.com.dto.NotificationDTO;
import org.Scsp.com.model.Notification;
import org.Scsp.com.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/user/{userId}")
    public List<NotificationDTO> getUserNotifications(@PathVariable Long userId) {
        return notificationRepository.findByUser_UserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(n -> new NotificationDTO(
                        n.getId(),
                        n.getTitle(),
                        n.getContent(),
                        n.isRead(),
                        n.getCreatedAt()
                ))
                .toList();
    }


    @PostMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        Notification noti = notificationRepository.findById(id).orElseThrow();
        noti.setRead(true);
        notificationRepository.save(noti);
    }
}
