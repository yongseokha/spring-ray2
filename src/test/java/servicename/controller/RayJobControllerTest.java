package servicename.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import servicename.SampleApplication;
import servicename.dto.RayJobRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * RayJobController 통합 테스트
 */
@SpringBootTest(classes = SampleApplication.class)
@AutoConfigureMockMvc
class RayJobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createRayJob() throws Exception {
        // 요청 객체 구성
        RayJobRequest request = new RayJobRequest();
        request.setApiVersion("ray.io/v1");
        request.setKind("RayJob");

        RayJobRequest.Metadata metadata = new RayJobRequest.Metadata();
        metadata.setName("test-job");
        metadata.setNamespace("default");
        request.setMetadata(metadata);

        RayJobRequest.Spec spec = new RayJobRequest.Spec();
        spec.setEntrypoint("python main.py");
        spec.setShutdownAfterJobFinishes(true);
        spec.setClusterSelector("ray-cluster-dev");
        request.setSpec(spec);

        // POST 요청 테스트
        mockMvc.perform(post("/api/job")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
