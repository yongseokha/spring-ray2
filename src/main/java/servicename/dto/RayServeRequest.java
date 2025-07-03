package servicename.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * RayServe 리소스를 생성하기 위한 요청 객체 VO 클래스.
 * Kubernetes CRD 스펙(RayService) 기준으로 필드를 정의함.
 */
@Data
public class RayServeRequest {
    private String apiVersion;
    private String kind;
    private Metadata metadata;
    private Spec spec;

    @Data
    public static class Metadata {
        private String name;
        private String namespace;
        private Map<String, String> labels;
        private Map<String, String> annotations;
    }

    @Data
    public static class Spec {
        private String serveDeploymentGraphSpecType; // 예: "serve_deployment_graph"
        private ServeDeploymentGraphSpec serveDeploymentGraphSpec;
        private RayClusterConfig rayClusterConfig;
        private String runtimeEnvYAML;
        private Boolean includeDashboard;
    }

    @Data
    public static class ServeDeploymentGraphSpec {
        private List<Deployment> deployments;
    }

    @Data
    public static class Deployment {
        private String name;
        private String routePrefix;
        private String importPath;
        private Map<String, Object> userConfig;
        private Map<String, Object> autoscalingConfig;
        private int numReplicas;
        private String runtimeEnv;
    }

    @Data
    public static class RayClusterConfig {
        private String rayVersion;
        private HeadGroupSpec headGroupSpec;
        private List<WorkerGroupSpec> workerGroupSpecs;
    }

    @Data
    public static class HeadGroupSpec {
        private PodTemplateSpec template;
        private Map<String, String> rayStartParams;
    }

    @Data
    public static class WorkerGroupSpec {
        private String groupName;
        private int replicas;
        private PodTemplateSpec template;
        private Map<String, String> rayStartParams;
        private int minReplicas;
        private int maxReplicas;
    }

    @Data
    public static class PodTemplateSpec {
        private Metadata metadata;
        private PodSpec spec;
    }

    @Data
    public static class PodSpec {
        private List<Container> containers;
        private List<Map<String, Object>> volumes;
    }

    @Data
    public static class Container {
        private String name;
        private String image;
        private List<String> command;
        private List<String> args;
        private List<Map<String, String>> env;
        private Map<String, Object> resources;
        private List<Map<String, Object>> volumeMounts;
    }
}
