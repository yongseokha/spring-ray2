package servicename.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * RayCluster 리소스를 생성하기 위한 요청 객체.
 * Kubernetes CRD의 구조를 반영하여 계층적으로 정의됨.
 */
@Data
public class RayClusterRequest {

    /**
     * API 버전 (예: cluster.ray.io/v1)
     */
    private String apiVersion;

    /**
     * 리소스 종류 (예: RayCluster)
     */
    private String kind;

    /**
     * 리소스 메타데이터 (이름, 네임스페이스 등)
     */
    private Metadata metadata;

    /**
     * RayCluster에 대한 실제 스펙 정의
     */
    private Spec spec;

    /**
     * metadata: 리소스의 이름, 네임스페이스, 라벨, 주석 정의
     */
    @Data
    public static class Metadata {
        private String name;                           // 리소스 이름
        private String namespace;                      // 배포 네임스페이스
        private Map<String, String> labels;            // Kubernetes 라벨
        private Map<String, String> annotations;       // 주석
    }

    /**
     * spec: RayCluster 전체 사양 정의
     */
    @Data
    public static class Spec {
        private String rayVersion;                     // Ray 버전 (예: 2.9.1)
        private HeadGroupSpec headGroupSpec;           // 헤드 노드 그룹 스펙
        private List<WorkerGroupSpec> workerGroupSpecs;// 워커 노드 그룹 목록
        private Boolean enableInTreeAutoscaling;       // 내부 오토스케일링 활성화 여부
        private Map<String, Object> autoscalerOptions; // 오토스케일러 추가 옵션
    }

    /**
     * HeadGroupSpec: 헤드 노드 정의 (템플릿 및 Ray 시작 파라미터 포함)
     */
    @Data
    public static class HeadGroupSpec {
        private PodTemplateSpec template;              // pod 템플릿 정의
        private Map<String, String> rayStartParams;    // Ray 시작 시 파라미터
    }

    /**
     * WorkerGroupSpec: 워커 노드 그룹 정의
     */
    @Data
    public static class WorkerGroupSpec {
        private String groupName;                      // 워커 그룹 이름
        private Integer replicas;                      // 복제 수
        private PodTemplateSpec template;              // pod 템플릿 정의
        private Map<String, String> rayStartParams;    // Ray 시작 시 파라미터
        private Integer minReplicas;                   // 최소 복제 수
        private Integer maxReplicas;                   // 최대 복제 수
    }

    /**
     * PodTemplateSpec: pod 템플릿 정의 (metadata + spec)
     */
    @Data
    public static class PodTemplateSpec {
        private Metadata metadata;                     // pod 메타데이터
        private PodSpec spec;                          // pod 사양
    }

    /**
     * PodSpec: pod 내 컨테이너 및 볼륨 정의
     */
    @Data
    public static class PodSpec {
        private List<Container> containers;            // 컨테이너 목록
        private List<Map<String, Object>> volumes;     // 볼륨 정의
    }

    /**
     * Container: 단일 컨테이너 정의
     */
    @Data
    public static class Container {
        private String name;                           // 컨테이너 이름
        private String image;                          // 사용 이미지
        private List<String> command;                  // 커맨드 명령어
        private List<String> args;                     // 명령어 인자
        private List<Map<String, String>> env;         // 환경변수
        private Map<String, Object> resources;         // 리소스 (cpu, memory)
        private List<Map<String, Object>> volumeMounts;// 볼륨 마운트 정보
    }
}
