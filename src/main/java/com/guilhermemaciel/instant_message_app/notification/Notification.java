package com.guilhermemaciel.instant_message_app.notification;

public record Notification(
        String chatId,
        String content,
        String senderId,
        String receiverId,
        String chatName,
        NotificationType type,
        byte[] media
) {
}
