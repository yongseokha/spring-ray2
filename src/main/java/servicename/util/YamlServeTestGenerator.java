package servicename.util;

import servicename.dto.RayServeRequest;
import servicename.dto.RayServeRequest.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 테스트용 RayServe YAML 생성기 (Java 8 대응)
 */
public class YamlServeTestGenerator {

    public static void main(String[] args) {
        RayServeRequest request = new RayServeRequest();
        request.setApiVersion("ray.io/v1");
        request.setKind("RayService");

        // 메타데이터 설정
        Metadata metadata = new Metadata();
        metadata.setName("sample-serve");
        metadata.setNamespace("default");

        Map<String, String> labels = new HashMap<>();
        labels.put("app", "ray");
        metadata.setLabels(labels);

        request.setMetadata(metadata);

        // ServeDeployment 설정
        Deployment deployment = new Deployment();
        deployment.setName("my-deploy");
        deployment.setRoutePrefix("/");
        deployment.setImportPath("example.serve:app");
        deployment.setNumReplicas(1);

        List<Deployment> deployments = new ArrayList<>();
        deployments.add(deployment);

        ServeDeploymentGraphSpec graphSpec = new ServeDeploymentGraphSpec();
        graphSpec.setDeployments(deployments);

        // HeadGroup 설정
        HeadGroupSpec headSpec = new HeadGroupSpec();
        Map<String, String> headParams = new HashMap<>();
        headParams.put("dashboard-host", "0.0.0.0");
        headSpec.setRayStartParams(headParams);

        // RayClusterConfig 설정
        RayClusterConfig clusterConfig = new RayClusterConfig();
        clusterConfig.setRayVersion("2.9.3");
        clusterConfig.setHeadGroupSpec(headSpec);
        clusterConfig.setWorkerGroupSpecs(new ArrayList<>()); // 비워두기

        // Spec 설정
        Spec spec = new Spec();
        spec.setServeDeploymentGraphSpecType("serve_deployment_graph");
        spec.setServeDeploymentGraphSpec(graphSpec);
        spec.setRayClusterConfig(clusterConfig);
        spec.setIncludeDashboard(true);

        request.setSpec(spec);

        // YAML 출력
        String yaml = YamlUtil.toYaml(request);
        System.out.println(yaml);

        // 파일 저장
        YamlUtil.saveYamlToFile(yaml, "generated/sample-rayserve.yaml");
    }
}
