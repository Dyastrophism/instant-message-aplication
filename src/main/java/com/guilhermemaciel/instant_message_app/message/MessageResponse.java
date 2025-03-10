package com.guilhermemaciel.instant_message_app.message;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MessageResponse(
        Long id,
        String content,
        MessageType type,
        MessageState state,
        String senderId,
        String receiverId,
        LocalDateTime createdAt,
        byte[] media
) {
}
