package vn.pvhg.minilink.auth.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "refresh_tokens",
        indexes = {
                @Index(name = "idx_verification_token_user_id", columnList = "user_id"),
                @Index(name = "idx_verification_token_token", columnList = "token")
        }
)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tokenId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private boolean isRevoked = false;

    @Column(nullable = false)
    private Instant issuedAt;

    @Column(nullable = false)
    private Instant expiredAt;

    public boolean isExpired() {
        return Instant.now().isAfter(expiredAt);
    }
}
