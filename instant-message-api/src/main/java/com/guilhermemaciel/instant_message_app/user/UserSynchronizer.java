package com.guilhermemaciel.instant_message_app.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Synchronizes the user with the IDP.
     * If the user does not exist, it will be created.
     * If the user exists, it will be updated.
     * The user is identified by the email.
     * @param token the token
     */
    public void synchronizeWithIdp(Jwt token) {
        log.info("Synchronizing user with IDP...");
        getUserEmail(token).ifPresent(userEmail -> {
            log.info("Synchronizing user having email: {}", userEmail);
//            Optional<User> optUser = userRepository.findByEmail(userEmail);
            User user = userMapper.fromTokenAttributes(token.getClaims());
//            optUser.ifPresent(value -> user.setId(optUser.get().getId()));
            userRepository.save(user);
        });
    }

    /**
     * Extracts the user email from the token.
     * @param token the token
     * @return the user email
     */
    private Optional<String> getUserEmail(Jwt token) {
        Map<String, Object> attributes = token.getClaims();
        if (attributes.containsKey("email")) {
            return Optional.of((String) attributes.get("email"));
        }
        return Optional.empty();
    }
}
