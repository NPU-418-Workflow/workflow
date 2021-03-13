package me.danght.workflow.form.dao.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.redis.client.RedisClient;
import me.danght.workflow.common.serialization.BaseMapper;
import me.danght.workflow.form.dataobject.ProcessParamsRelationDO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ProcessParamsRelationCacheDao {

    /**
     *  wProcessDefinition:流程定义主键
     */
    private static final String KEY_PATTERN = "businessName:%s;pdId:%s";

    @Inject
    RedisClient redisClient;

    private static String buildKey(String businessName,String pdId) {
        return String.format(KEY_PATTERN, businessName,pdId);
    }

    public ProcessParamsRelationDO get(String businessName, String pdId) {
        String key = buildKey(businessName,pdId);
        String value = redisClient.get(key).toString();
        try {
            return BaseMapper.getObjectMapper().readValue(value, ProcessParamsRelationDO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void set(String businessName,String pdId, ProcessParamsRelationDO object) {
        String key = buildKey(businessName,pdId);
        String value = null;
        try {
            value = BaseMapper.getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (value != null) redisClient.set(List.of(key, value));
    }
}
