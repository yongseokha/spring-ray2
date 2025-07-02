package servicename.dto;

import lombok.Data;
import java.util.Map;

/**
 * RayJob 리소스를 생성하기 위한 요청 객체
 * Kubernetes CRD에 정의된 모든 필드를 포함
 */
@Data
public class RayJobRequest {

    private String apiVersion;   // 예: ray.io/v1
    private String kind;         // 예: RayJob
    private Metadata metadata;   // 리소스 이름, 네임스페이스 등
    private Spec spec;           // Job 동작 사양

    @Data
    public static class Metadata {
        private String name;                 // RayJob 이름
        private String namespace;            // 실행 네임스페이스
        private Map<String, String> labels;
        private Map<String, String> annotations;
    }

    @Data
    public static class Spec {
        private String entrypoint;                  // 실행할 Python 스크립트 또는 커맨드
        private Map<String, Object> runtimeEnv;     // RAY_RUNTIME_ENV (e.g. pip deps, env vars)
        private Map<String, String> metadata;       // 사용자 정의 정보
        private Boolean shutdownAfterJobFinishes;   // Job 종료 후 클러스터 자동 종료 여부
        private String clusterSelector;             // 실행 대상 RayCluster 이름 or label
        private String rayJobId;                    // Optional: 지정된 Job ID
    }
}
