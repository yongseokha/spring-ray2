package servicename.service;

import servicename.dto.RayServeRequest;
import servicename.entity.RayServeEntity;

import java.util.List;
/**
 * RayServe 관련 비즈니스 로직 인터페이스.
 * Serve 리소스 생성, 조회, 삭제 등을 처리한다.
 */
public interface RayServeService {

    /**
     * Serve 리소스 생성
     */
    RayServeEntity createServe(RayServeRequest request);

    /**
     * Serve 리소스 조회 (YAML)
     */
    String getServe(String namespace, String name);

    /**
     * Serve 리소스 삭제
     */
    void deleteServe(String namespace, String name);

    List<RayServeEntity> getAllServes();
}
