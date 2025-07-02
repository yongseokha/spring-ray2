package servicename.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import servicename.entity.RayJobEntity;

import java.util.Optional;

/**
 * RayJob 요청/응답 DB 저장용 JPA 리포지토리
 */
public interface RayJobRepository extends JpaRepository<RayJobEntity, Long> {
    Optional<RayJobEntity> findByNamespaceAndJobName(String namespace, String jobName);
}
