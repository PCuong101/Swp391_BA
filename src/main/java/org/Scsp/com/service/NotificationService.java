package org.Scsp.com.service;

import org.Scsp.com.model.User;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    public void createNotification(User user, String title, String content);
}
