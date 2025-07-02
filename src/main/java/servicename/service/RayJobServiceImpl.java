package servicename.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import servicename.dto.RayJobRequest;
import servicename.entity.RayJobEntity;
import servicename.k8s.KubernetesJobClient;
import servicename.repository.RayJobRepository;
import servicename.util.YamlUtil;
import servicename.webhook.WebhookService;

/**
 * RayJob 서비스 구현
 * - Webhook 승인 → YAML 생성 → K8s Job 실행 → DB 저장
 */
@Service
@RequiredArgsConstructor
public class RayJobServiceImpl implements RayJobService {

    private final WebhookService webhookService;
    private final KubernetesJobClient kubernetesJobClient;
    private final RayJobRepository rayJobRepository;

    @Override
    public String createJob(RayJobRequest request) {
        // 승인 절차
        boolean approved = webhookService.approveJobRequest(request);
        if (!approved) throw new RuntimeException("Job 승인 실패");

        // YAML 변환
        String yaml = YamlUtil.toYaml(request);

        // K8s 리소스 생성
        String result = kubernetesJobClient.createJob(request);

        // DB 저장
        RayJobEntity entity = RayJobEntity.builder()
                .jobName(request.getMetadata().getName())
                .namespace(request.getMetadata().getNamespace())
                .requestYaml(yaml)
                .responseYaml(result)
                .build();
        rayJobRepository.save(entity);

        return result;
    }

    @Override
    public String getJob(String namespace, String name) {
        String yaml = kubernetesJobClient.getJob(namespace, name);

        rayJobRepository.findByNamespaceAndJobName(namespace, name).ifPresent(entity -> {
            entity.setResponseYaml(yaml);
            rayJobRepository.save(entity);
        });

        return yaml;
    }

    @Override
    public void deleteJob(String namespace, String name) {
        kubernetesJobClient.deleteJob(namespace, name);
        rayJobRepository.findByNamespaceAndJobName(namespace, name)
                .ifPresent(rayJobRepository::delete);
    }
}
