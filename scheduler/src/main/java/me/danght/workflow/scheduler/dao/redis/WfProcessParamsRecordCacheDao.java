package me.danght.workflow.scheduler.dao.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.redis.client.RedisClient;
import me.danght.workflow.scheduler.dataobject.ProcessParamsRecordDO;
import me.danght.workflow.scheduler.serialization.BaseMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WfProcessParamsRecordCacheDao {

    /**
     * wProcessDefinition:流程定义主键
     */
    private static final String KEY_PATTERN = "enginePpName:%s;aiId:%s";

    @Inject
    RedisClient redisClient;

    private static String buildKey(String enginePpName,String aiId) {
        return String.format(KEY_PATTERN, enginePpName,aiId);
    }
    public ProcessParamsRecordDO get(String enginePpName, String aiId) {
        String key = buildKey(enginePpName,aiId);
        String value = redisClient.get(key).toString();
        ProcessParamsRecordDO processParamsRecordDO = null;
        try {
            processParamsRecordDO = BaseMapper.getObjectMapper().readValue(value, ProcessParamsRecordDO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return processParamsRecordDO;
    }

    public void set(String enginePpName,String aiId, ProcessParamsRecordDO object) {
        String key = buildKey(enginePpName,aiId);
        String value = null;
        try {
            value = BaseMapper.getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (value != null) redisClient.append(key, value);
    }
}
