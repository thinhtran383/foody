package com.example.foodordering.services.fcm;

import com.example.foodordering.dtos.NotificationMessageDTO;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FirebaseMessagingService {
    private final FirebaseMessaging firebaseMessaging;

    public String sendNotificationByToken(NotificationMessageDTO notificationMessageDTO) {
        Map<String, String> content = new HashMap<>();

        content.put("title", notificationMessageDTO.getTitle());
        content.put("body", notificationMessageDTO.getBody());
        content.put("image", notificationMessageDTO.getImage());


        Notification notification = Notification
                .builder()
                .setTitle(notificationMessageDTO.getTitle())
                .setBody(notificationMessageDTO.getBody())
                .setImage(notificationMessageDTO.getImage())
                .build();

        Message message = Message
                .builder()
                .setToken(notificationMessageDTO.getRecipientToken())
                .setNotification(notification)
                .putAllData(content)
                .build();

        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        return "success sending notification";
    }
}
