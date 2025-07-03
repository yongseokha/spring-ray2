package servicename.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import servicename.dto.RayServeRequest;
import servicename.entity.RayServeEntity;
import servicename.repository.RayServeRepository;
import servicename.util.YamlUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * RayServeService 구현체.
 * Serve 리소스 생성/조회/삭제 기능을 실제로 수행한다.
 */
@Service
@RequiredArgsConstructor
public class RayServeServiceImpl implements RayServeService {

    private final RayServeRepository rayServeRepository;
    private final ObjectMapper objectMapper;

    @Override
    public RayServeEntity createServe(RayServeRequest request) {
        try {
            // 요청 객체를 YAML 문자열로 변환
            String yaml = YamlUtil.toYaml(request);

            // Entity 저장
            RayServeEntity entity = RayServeEntity.builder()
                    .name(request.getMetadata().getName())
                    .namespace(request.getMetadata().getNamespace())
                    .createdAt(LocalDateTime.now())
                    .originalRequest(yaml)
                    .yamlPath("ray-serve-" + request.getMetadata().getName() + ".yaml") // 임시 path
                    .build();

            return rayServeRepository.save(entity);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create RayServe resource", e);
        }
    }

    @Override
    public String getServe(String namespace, String name) {
        return rayServeRepository.findByNameAndNamespace(name, namespace)
                .map(RayServeEntity::getOriginalRequest)
                .orElseThrow(() -> new IllegalArgumentException("Serve not found"));
    }

    @Override
    public void deleteServe(String namespace, String name) {
        RayServeEntity entity = rayServeRepository.findByNameAndNamespace(name, namespace)
                .orElseThrow(() -> new IllegalArgumentException("Serve not found"));
        rayServeRepository.delete(entity);
    }

    @Override
    public List<RayServeEntity> getAllServes() {
        return rayServeRepository.findAll();
    }
}
