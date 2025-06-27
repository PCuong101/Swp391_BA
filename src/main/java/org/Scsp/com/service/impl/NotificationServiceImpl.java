package org.Scsp.com.service.impl;

import org.Scsp.com.model.Notification;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.NotificationRepository;
import org.Scsp.com.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void createNotification(User user, String title, String content) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setContent(content);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        notificationRepository.save(notification);
    }
}
