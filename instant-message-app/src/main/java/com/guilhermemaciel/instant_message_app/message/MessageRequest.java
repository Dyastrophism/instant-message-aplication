package com.guilhermemaciel.instant_message_app.message;

import lombok.Builder;

@Builder
public record MessageRequest(
        String content,
        String senderId,
        String receiverId,
        MessageType type,
        String chatId
) {
}
