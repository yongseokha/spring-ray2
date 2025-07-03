package servicename.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicename.dto.RayServeRequest;
import servicename.entity.RayServeEntity;
import servicename.service.RayServeService;

import java.util.List;

/**
 * RayServe 리소스에 대한 REST API 컨트롤러.
 * 생성, 조회, 삭제 등의 HTTP 요청을 처리한다.
 */
@RestController
@RequestMapping("/api/serve")
@RequiredArgsConstructor
public class RayServeController {

    private final RayServeService rayServeService;


    /**
     * Serve 리소스 생성 요청
     */
    @PostMapping("/deploy")
    public ResponseEntity<RayServeEntity> createServe(@RequestBody RayServeRequest request) {
        RayServeEntity entity = rayServeService.createServe(request);
        return ResponseEntity.ok(entity);
    }

    /**
     * Serve 리소스 조회 요청 (YAML 반환)
     */
    @GetMapping("/{namespace}/{name}")
    public ResponseEntity<String> getServe(
            @PathVariable String namespace,
            @PathVariable String name) {
        return ResponseEntity.ok(rayServeService.getServe(namespace, name));
    }

    /**
     * Serve 리소스 삭제 요청
     */
    @DeleteMapping("/{namespace}/{name}")
    public ResponseEntity<String> deleteServe(
            @PathVariable String namespace,
            @PathVariable String name) {
        rayServeService.deleteServe(namespace, name);
        return ResponseEntity.ok("Serve deleted: " + name);
    }

    /**
     * 모든 Serve 리소스 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<List<RayServeEntity>> getAllServes() {
        return ResponseEntity.ok(rayServeService.getAllServes());
    }
}
