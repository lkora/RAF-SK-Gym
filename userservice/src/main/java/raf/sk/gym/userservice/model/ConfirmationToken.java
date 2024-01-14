package raf.sk.gym.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "confirmation_token")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "confirmation_token", nullable = false)
    private UUID confirmationToken;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
        this.createdAt = Instant.now();
        this.confirmationToken = UUID.randomUUID();
    }

    /**
     * Checks if the confirmation token is valid.
     * A token is valid if it has been issued in the last 20 minutes.
     * @return true if the token is valid.
     */
    public boolean isValid() {
        return this.createdAt.plusSeconds(60 * 20).isAfter(Instant.now());
    }

    @Override
    public String toString() {
        return "ConfirmationToken{" + "id=" + id + ", confirmationToken=" + confirmationToken + ", createdAt=" + createdAt + ", user=" + user + '}';
    }
}