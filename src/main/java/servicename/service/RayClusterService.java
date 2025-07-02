package servicename.service;

import servicename.dto.RayClusterRequest;
import servicename.entity.RayClusterEntity;

import java.util.List;

/**
 * RayCluster 서비스 인터페이스
 */

public interface RayClusterService {

    String createCluster(RayClusterRequest request);
    String getCluster(String namespace, String name);
    String updateCluster(String namespace, String name, RayClusterRequest request);
    List<RayClusterEntity> getAllClusters();

    void deleteCluster(String namespace, String name);
}
