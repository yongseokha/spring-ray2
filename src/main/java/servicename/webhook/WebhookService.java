package servicename.webhook;

import org.springframework.stereotype.Service;
import servicename.dto.RayClusterRequest;
import servicename.dto.RayJobRequest;

/**
 * 외부 Webhook 시스템에 클러스터 생성 요청을 승인받는 서비스
 */
@Service
public class WebhookService {

    /**
     * 외부 Webhook API를 호출하여 승인 여부 확인
     * 현재는 샘플로 항상 승인된다고 가정
     */
    public boolean approveClusterRequest(RayClusterRequest request) {
        // 실제 구현: RestTemplate 또는 WebClient로 외부 시스템 호출
        // 예: POST http://webhook-system/approve-cluster

        // 예시:
        // ResponseEntity<Boolean> response = restTemplate.postForEntity(url, request, Boolean.class);
        // return response.getBody();

        // 현재는 승인됐다고 가정
        return true;
    }

    public boolean approveJobRequest(RayJobRequest request) {
        // 실제 구현: 외부 승인 시스템 호출
        return true; // 현재는 승인 성공 가정
    }

}
