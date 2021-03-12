package me.danght.workflow.scheduler.dao.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.redis.client.RedisClient;
import me.danght.workflow.common.serialization.BaseMapper;
import me.danght.workflow.scheduler.dataobject.WfProcessParamsRecordDO;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WfProcessParamsRecordCacheDao {
    private static final String KEY_PATTERN = "enginePpName:%s;aiId:%s"; // wProcessDefinition:流程定义主键

    @Inject
    RedisClient redisClient;

    private static String buildKey(String enginePpName,String aiId) {
        return String.format(KEY_PATTERN, enginePpName,aiId);
    }
    public WfProcessParamsRecordDO get(String enginePpName, String aiId) {
        String key = buildKey(enginePpName,aiId);
        String value = redisClient.get(key).toString();
        WfProcessParamsRecordDO wfProcessParamsRecordDO = null;
        try {
            wfProcessParamsRecordDO = BaseMapper.getObjectMapper().readValue(value, WfProcessParamsRecordDO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return wfProcessParamsRecordDO;
    }

    public void set(String enginePpName,String aiId, WfProcessParamsRecordDO object) {
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
