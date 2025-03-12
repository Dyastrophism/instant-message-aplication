package com.guilhermemaciel.instant_message_app.common;

import lombok.Builder;

@Builder
public record StringResponse(
        String response
) {
}
