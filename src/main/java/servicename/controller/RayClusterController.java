package servicename.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicename.dto.RayClusterRequest;
import servicename.service.RayClusterService;
import servicename.entity.RayClusterEntity;
import java.util.List;
/**
 * RayCluster 관련 REST API 컨트롤러
 * 클러스터 생성/삭제/조회 등의 기능 제공
 */
@RestController
@RequestMapping("/api/cluster")
@RequiredArgsConstructor
public class RayClusterController {

    private final RayClusterService rayClusterService;

    /**
     * 클러스터 생성 요청 처리
     * - 외부 Webhook 승인 요청
     * - 승인되면 Kubernetes API를 통해 클러스터 생성
     */
    @PostMapping
    public ResponseEntity<String> createCluster(@RequestBody RayClusterRequest request) {
        String result = rayClusterService.createCluster(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{namespace}/{name}")
    public ResponseEntity<String> getCluster(
            @PathVariable String namespace,
            @PathVariable String name) {
        String yaml = rayClusterService.getCluster(namespace, name);
        return ResponseEntity.ok(yaml);
    }

    @PutMapping("/{namespace}/{name}")
    public ResponseEntity<String> updateCluster(
            @PathVariable String namespace,
            @PathVariable String name,
            @RequestBody RayClusterRequest request) {
        String result = rayClusterService.updateCluster(namespace, name, request);
        return ResponseEntity.ok(result);
    }

    /**
     * 클러스터 삭제 요청
     */
    @DeleteMapping("/{namespace}/{name}")
    public ResponseEntity<String> deleteCluster(
            @PathVariable String namespace,
            @PathVariable String name) {
        rayClusterService.deleteCluster(namespace, name);
        return ResponseEntity.ok("Cluster deleted: " + name);
    }

    @GetMapping("/list")
    public ResponseEntity<List<RayClusterEntity>> getAllClusters() {
        return ResponseEntity.ok(rayClusterService.getAllClusters());
    }
}
