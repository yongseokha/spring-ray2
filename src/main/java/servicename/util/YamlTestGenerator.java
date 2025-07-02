package servicename.util;

import servicename.dto.RayClusterRequest;

import java.util.HashMap;

public class YamlTestGenerator {

    public static void main(String[] args) {
        RayClusterRequest request = new RayClusterRequest();
        request.setApiVersion("cluster.ray.io/v1");
        request.setKind("RayCluster");

        RayClusterRequest.Metadata metadata = new RayClusterRequest.Metadata();
        metadata.setName("sample-cluster");
        metadata.setNamespace("default");
        request.setMetadata(metadata);

        RayClusterRequest.Spec spec = new RayClusterRequest.Spec();
        spec.setRayVersion("2.9.3");
        spec.setEnableInTreeAutoscaling(false);
        spec.setAutoscalerOptions(new HashMap<>());

        RayClusterRequest.HeadGroupSpec headGroup = new RayClusterRequest.HeadGroupSpec();
        headGroup.setRayStartParams(new HashMap<>());

        RayClusterRequest.PodTemplateSpec template = new RayClusterRequest.PodTemplateSpec();
        template.setMetadata(metadata);
        template.setSpec(new RayClusterRequest.PodSpec());
        headGroup.setTemplate(template);

        spec.setHeadGroupSpec(headGroup);
        request.setSpec(spec);

        // YAML 파일 저장
        String yaml = YamlUtil.toYaml(request);
        YamlUtil.saveYamlToFile(yaml, "src/test/resources/yamls/sample-cluster.yaml");

        System.out.println("✅ sample-cluster.yaml 생성 완료!");
    }
}
