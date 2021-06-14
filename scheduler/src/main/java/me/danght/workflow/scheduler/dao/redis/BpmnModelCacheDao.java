package me.danght.workflow.scheduler.dao.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.redis.client.RedisClient;
import me.danght.workflow.scheduler.element.BpmnModel;
import me.danght.workflow.scheduler.serialization.BaseMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class BpmnModelCacheDao {

    /**
     * wProcessDefinition:流程定义主键
     */
    private static final String KEY_PATTERN = "BpmnModel:%s";

    @Inject
    RedisClient redisClient;

    private static String buildKey(String id) {
        return String.format(KEY_PATTERN, id);
    }

    public BpmnModel get(String id) {
        String key = buildKey(id);
        if (!redisClient.exists(List.of(key)).toBoolean()) {
            return null;
        }
        String value = redisClient.get(key).toString();
        BpmnModel bpmnModel = null;
        try {
            bpmnModel = BaseMapper.getObjectMapper().readValue(value, BpmnModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bpmnModel;
    }

    public void set(String id, BpmnModel model) {
        String key = buildKey(id);
        String value = null;
        try {
            value = BaseMapper.getObjectMapper().writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (value != null) redisClient.append(key, value);
    }
}
