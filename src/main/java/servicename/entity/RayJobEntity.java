package servicename.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * RayJob 리소스 요청/응답 저장용 JPA 엔티티
 */
@Entity
@Table(name = "ray_job")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RayJobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String jobName;

    @Column(nullable = false)
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
