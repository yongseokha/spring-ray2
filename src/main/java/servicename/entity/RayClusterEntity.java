package servicename.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ray_cluster")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RayClusterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clusterName;
    private String namespace;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String requestYaml;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String responseYaml;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
