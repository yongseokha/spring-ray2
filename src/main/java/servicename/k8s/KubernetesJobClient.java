package servicename.k8s;

import org.springframework.stereotype.Component;
import servicename.dto.RayJobRequest;

/**
 * Kubernetes API를 통해 RayJob 리소스를 생성하는 클라이언트
 */
@Component
public class KubernetesJobClient {

    /**
     * Kubernetes에 RayJob 리소스 생성
     */
    public String createJob(RayJobRequest request) {
        System.out.println("RayJob 생성 요청: " +
                request.getMetadata().getNamespace() + "/" + request.getMetadata().getName());
        return "RayJob " + request.getMetadata().getName() + " 생성 완료";
    }

    public String getJob(String namespace, String name) {
        System.out.println("RayJob 조회: " + namespace + "/" + name);
        return "Mock YAML for RayJob " + name;
    }

    public void deleteJob(String namespace, String name) {
        System.out.println("RayJob 삭제: " + namespace + "/" + name);
    }

}
