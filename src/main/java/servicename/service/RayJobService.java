package servicename.service;

import servicename.dto.RayJobRequest;

/**
 * RayJob 비즈니스 로직 인터페이스
 */
public interface RayJobService {

    String createJob(RayJobRequest request);
    String getJob(String namespace, String name);
    void deleteJob(String namespace, String name);

}
