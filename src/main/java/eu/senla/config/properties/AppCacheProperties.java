package eu.senla.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties
public class AppCacheProperties {

    List<String> cacheNames = new ArrayList<>();
    private Map<String, CacheProperties> caches = new HashMap<>();

    @Data
    public static class CacheProperties {
        private Duration expiry = Duration.ZERO;
    }

    public interface CacheNames{
        String ALL_TASKS = "allTasks";
        String TASK_BY_ID = "taskById";
        String ALL_USERS = "allUsers";
        String USER_BY_ID = "userById";
    }
}
