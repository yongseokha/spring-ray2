package servicename.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class YamlUtil {

    private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    /**
     * 객체 → YAML 문자열
     */
    public static String toYaml(Object obj) {
        try {
            return yamlMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("YAML 직렬화 실패", e);
            return null;
        }
    }

    /**
     * YAML 문자열을 파일로 저장
     */
    public static void saveYamlToFile(String yaml, String filePath) {
        try {
            Files.createDirectories(Paths.get(filePath).getParent());
            try (FileWriter writer = new FileWriter(new File(filePath))) {
                writer.write(yaml);
            }
        } catch (Exception e) {
            log.error("YAML 파일 저장 실패", e);
        }
    }
}
