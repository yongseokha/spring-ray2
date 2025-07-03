package servicename.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * RayServe 리소스 정보를 DB에 저장하기 위한 Entity 클래스.
 * 요청한 Serve 리소스의 메타 정보를 보존한다.
 */
@Entity
@Table(name = "ray_serve")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RayServeEntity {

    /**
     * 기본 키 (자동 증가)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Serve 리소스 이름 (metadata.name)
     */
    @Column(nullable = false)
    private String name;

    /**
     * 리소스가 배포된 namespace
     */
    @Column(nullable = false)
    private String namespace;

    /**
     * Serve 리소스가 생성된 시간
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * 원본 요청 YAML 또는 JSON 문자열
     */
    @Lob
    @Column(columnDefinition = "TEXT")
    private String originalRequest;

    /**
     * YAML 파일 경로 또는 이름 (선택적)
     */
    private String yamlPath;
}
