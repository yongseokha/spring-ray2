package servicename.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicename.dto.RayJobRequest;
import servicename.service.RayJobService;

/**
 * RayJob 관련 REST API 컨트롤러
 * - Job 생성 등 기능 제공
 */
@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class RayJobController {

    private final RayJobService rayJobService;

    /**
     * RayJob 생성 요청
     * - Webhook 승인 → K8s에 배포 → DB 저장
     */
    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody RayJobRequest request) {
        String result = rayJobService.createJob(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{namespace}/{name}")
    public ResponseEntity<String> getJob(
            @PathVariable String namespace,
            @PathVariable String name) {
        String yaml = rayJobService.getJob(namespace, name);
        return ResponseEntity.ok(yaml);
    }

    @DeleteMapping("/{namespace}/{name}")
    public ResponseEntity<String> deleteJob(
            @PathVariable String namespace,
            @PathVariable String name) {
        rayJobService.deleteJob(namespace, name);
        return ResponseEntity.ok("RayJob deleted: " + name);
    }

}
