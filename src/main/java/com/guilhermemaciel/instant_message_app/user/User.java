package com.guilhermemaciel.instant_message_app.user;

import com.guilhermemaciel.instant_message_app.chat.Chat;
import com.guilhermemaciel.instant_message_app.common.BaseAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseAuditingEntity {

    private static final int LAST_ACTIVE_INTERVAL = 5;

    /**
     * The user's ID come from Keycloak.
     */
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDateTime lastSeen;

    @OneToMany(mappedBy = "sender")
    private List<Chat> chatsAsSender;

    @OneToMany(mappedBy = "recipient")
    private List<Chat> chatsAsRecipient;

    /**
     * Check if the user is online checking if the last seen time is less than 5 minutes.
     * To determine if the user is online, we consider that the user is online if the last seen time is less than 5 minutes.
     * The constant LAST_ACTIVE_INTERVAL is used to define the time interval to consider the user online.
     * @return true if the user is online, false otherwise.
     */
    @Transient
    public boolean isUserOnline() {
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERVAL));
    }
}
