package me.danght.workflow.scheduler.dao.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.redis.client.RedisClient;
import me.danght.workflow.scheduler.dataobject.Token;
import me.danght.workflow.scheduler.serialization.BaseMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TokenCacheDao {

    /**
     * wProcessDefinition:流程定义主键
     */
    private static final String KEY_PATTERN = "token:%s";

    @Inject
    RedisClient redisClient;

    private static String buildKey(String id) {
        return String.format(KEY_PATTERN, id);
    }

    public Token get(String id) {
        String key = buildKey(id);
        String value = redisClient.get(key).toString();
        Token token = null;
        try {
            token = BaseMapper.getObjectMapper().readValue(value, Token.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return token;
    }

    public void set(String id, Token object) {
        String key = buildKey(id);
        String value = null;
        try {
            value = BaseMapper.getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (value != null) redisClient.append(key, value);
    }
}
