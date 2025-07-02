package servicename.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import servicename.entity.RayClusterEntity;

import java.util.Optional;

/**
 * RayCluster 요청/응답 기록 저장용 JPA 레포지토리
 */
public interface RayClusterRepository extends JpaRepository<RayClusterEntity, Long> {
    Optional<RayClusterEntity> findByNamespaceAndClusterName(String namespace, String clusterName);
}
