// File: src/test/java/servicename/controller/RayClusterControllerTest.java

package servicename.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import servicename.SampleApplication;
import servicename.dto.RayClusterRequest;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * RayClusterController 통합 테스트
 */
@SpringBootTest(classes = SampleApplication.class)
@AutoConfigureMockMvc
class RayClusterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createRayCluster() throws Exception {
        // 요청 객체 생성
        RayClusterRequest request = new RayClusterRequest();
        request.setApiVersion("cluster.ray.io/v1");
        request.setKind("RayCluster");

        RayClusterRequest.Metadata metadata = new RayClusterRequest.Metadata();
        metadata.setName("test-cluster");
        metadata.setNamespace("default");
        request.setMetadata(metadata);

        RayClusterRequest.Spec spec = new RayClusterRequest.Spec();
        spec.setRayVersion("2.9.3");
        spec.setEnableInTreeAutoscaling(false);
        spec.setAutoscalerOptions(new HashMap<>());

        // HeadGroupSpec 생성
        RayClusterRequest.HeadGroupSpec headGroupSpec = new RayClusterRequest.HeadGroupSpec();
        headGroupSpec.setRayStartParams(new HashMap<>());

        RayClusterRequest.PodTemplateSpec templateSpec = new RayClusterRequest.PodTemplateSpec();
        templateSpec.setMetadata(metadata); // 재사용
        RayClusterRequest.PodSpec podSpec = new RayClusterRequest.PodSpec();
        templateSpec.setSpec(podSpec);
        headGroupSpec.setTemplate(templateSpec);

        spec.setHeadGroupSpec(headGroupSpec);
        request.setSpec(spec);

        // POST 요청 테스트
        mockMvc.perform(post("/api/cluster")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
