package com.event.organizer.api.appuser.registration.token;

import com.event.organizer.api.appuser.AppUser;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    public ConfirmationToken(String token, LocalDateTime created, LocalDateTime expiresAt,
                             LocalDateTime confirmedAt, AppUser appUser) {
        this.token = token;
        this.created = created;
        this.expiresAt = expiresAt;
        this.confirmedAt = confirmedAt;
        this.appUser = appUser;
    }

    @Id
    @SequenceGenerator(
        name = "token_sequence ",
        sequenceName = "token_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
        nullable = false,
        name = "app_user_id"
    )
    private AppUser appUser;
}
