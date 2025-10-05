package vn.pvhg.minilink.core.model;

import jakarta.persistence.*;
import lombok.*;
import vn.pvhg.minilink.auth.model.User;
import vn.pvhg.minilink.common.BaseEntity;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "urls")
public class Url extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID urlId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false, length = 2000)
    private String originalUrl;

    @Column(nullable = false, unique = true, length = 50)
    private String shortenUrl;

    @Column(nullable = false)
    private boolean active = true;

    private Instant expiresAt;

    @Column(nullable = false)
    @Builder.Default
    private long totalClicks = 0;

    @Column(nullable = false)
    @Builder.Default
    private long qrScans = 0;
}
