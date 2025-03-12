package com.guilhermemaciel.instant_message_app.notification;

import com.guilhermemaciel.instant_message_app.message.MessageType;
import lombok.Builder;

@Builder
public record Notification(
        String chatId,
        String content,
        String senderId,
        String receiverId,
        String chatName,
        MessageType messageType,
        NotificationType type,
        byte[] media
) {
}
