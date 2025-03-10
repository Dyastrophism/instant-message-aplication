package com.guilhermemaciel.instant_message_app.user;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserMapper {

    /**
     * Maps the token attributes to a user.
     * @param attributes the token attributes
     * @return the user
     */
    public User fromTokenAttributes(Map<String, Object> attributes) {
        User user = new User();

        if (attributes.containsKey("sub")) {
            user.setId((String) attributes.get("sub"));
        }

        if (attributes.containsKey("given_name")) {
            user.setFirstName((String) attributes.get("given_name"));
        } else if (attributes.containsKey("nickname")) {
            user.setFirstName((String) attributes.get("nickname"));
        }

        if (attributes.containsKey("family_name")) {
            user.setLastName((String) attributes.get("family_name"));
        }

        if (attributes.containsKey("email")) {
            user.setEmail((String) attributes.get("email"));
        }

        user.setLastSeen(LocalDateTime.now());

        return user;
    }
}
