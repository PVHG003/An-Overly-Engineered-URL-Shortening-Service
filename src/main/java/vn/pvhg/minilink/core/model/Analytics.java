package vn.pvhg.minilink.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.pvhg.minilink.common.BaseEntity;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "analytics")
public class Analytics extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID analyticsId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id", nullable = false, unique = true)
    private Url url;

    @Column(nullable = false)
    private long totalClicks = 0;

    @Column(nullable = false)
    private long totalQrScans = 0;

    @ElementCollection
    @CollectionTable(name = "analytics_clicks_by_country", 
                    joinColumns = @JoinColumn(name = "analytics_id"))
    @MapKeyColumn(name = "country")
    @Column(name = "clicks")
    private Map<String, Long> clicksByCountry;

    @ElementCollection
    @CollectionTable(name = "analytics_referrers",
                    joinColumns = @JoinColumn(name = "analytics_id"))
    @MapKeyColumn(name = "referrer")
    @Column(name = "count")
    private Map<String, Long> referrers;

    @Column(nullable = false)
    private double ctr = 0.0; // Click-through rate
}
