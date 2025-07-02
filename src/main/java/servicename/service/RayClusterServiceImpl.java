// File: src/main/java/servicename/service/RayClusterServiceImpl.java

package servicename.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import servicename.dto.RayClusterRequest;
import servicename.entity.RayClusterEntity;
import servicename.k8s.KubernetesClusterClient;
import servicename.repository.RayClusterRepository;
import servicename.util.YamlUtil;
import servicename.webhook.WebhookService;

import java.util.List;
import java.util.Optional;

/**
 * RayCluster 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class RayClusterServiceImpl implements RayClusterService {

    private final WebhookService webhookService;
    private final KubernetesClusterClient kubernetesClusterClient;
    private final RayClusterRepository rayClusterRepository;

    /**
     * 클러스터 생성
     */
    @Override
    public String createCluster(RayClusterRequest request) {
        // 승인 요청
        boolean approved = webhookService.approveClusterRequest(request);
        if (!approved) throw new RuntimeException("승인되지 않은 요청입니다.");

        // YAML 변환
        String requestYaml = YamlUtil.toYaml(request);

        // Kubernetes 리소스 생성
        String responseMsg = kubernetesClusterClient.createCluster(request);

        // DB 저장
        RayClusterEntity entity = RayClusterEntity.builder()
                .clusterName(request.getMetadata().getName())
                .namespace(request.getMetadata().getNamespace())
                .requestYaml(requestYaml)
                .responseYaml(responseMsg)
                .build();
        rayClusterRepository.save(entity);

        return responseMsg;
    }

    /**
     * 클러스터 조회
     */
    @Override
    public String getCluster(String namespace, String name) {
        String clusterYaml = kubernetesClusterClient.getCluster(namespace, name);

        // 조회 결과를 DB에도 반영 (있으면 업데이트)
        Optional<RayClusterEntity> optional = rayClusterRepository.findByNamespaceAndClusterName(namespace, name);
        optional.ifPresent(entity -> {
            entity.setResponseYaml(clusterYaml);
            rayClusterRepository.save(entity);
        });

        return clusterYaml;
    }

    /**
     * 클러스터 수정
     */
    @Override
    public String updateCluster(String namespace, String name, RayClusterRequest request) {
        String newRequestYaml = YamlUtil.toYaml(request);
        String updateResult = kubernetesClusterClient.updateCluster(namespace, name, request);

        Optional<RayClusterEntity> optional = rayClusterRepository.findByNamespaceAndClusterName(namespace, name);
        if (optional.isPresent()) {
            RayClusterEntity entity = optional.get();
            entity.setRequestYaml(newRequestYaml);
            entity.setResponseYaml(updateResult);
            rayClusterRepository.save(entity);
        }

        return updateResult;
    }

    /**
     * 클러스터 삭제
     */
    @Override
    public void deleteCluster(String namespace, String name) {
        kubernetesClusterClient.deleteCluster(namespace, name);
        rayClusterRepository.findByNamespaceAndClusterName(namespace, name)
                .ifPresent(rayClusterRepository::delete);
    }

    @Override
    public List<RayClusterEntity> getAllClusters() {
        return rayClusterRepository.findAll();
    }

}
