package com.guilhermemaciel.instant_message_app.user;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        LocalDateTime lastSeen,
        boolean isOnline
) {
}
