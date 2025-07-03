package servicename.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import servicename.entity.RayServeEntity;

import java.util.Optional;

/**
 * RayServeEntity에 대한 JPA Repository 인터페이스.
 * 기본적인 CRUD 메서드 제공 + name/namespace로 조회 지원.
 */
public interface RayServeRepository extends JpaRepository<RayServeEntity, Long> {

    /**
     * 이름 + 네임스페이스로 리소스 조회
     */
    Optional<RayServeEntity> findByNameAndNamespace(String name, String namespace);

    /**
     * 특정 네임스페이스에 존재하는 모든 Serve 리소스 조회
     */
    Iterable<RayServeEntity> findByNamespace(String namespace);
}
