package me.danght.workflow.common.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.danght.workflow.common.msg.TaskInstanceMessage;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author DangHT
 * @date 2021/03/11
 */
public class TaskInstanceSerializer implements Serializer<TaskInstanceMessage> {

    @Override
    public byte[] serialize(String s, TaskInstanceMessage taskInstanceMessage) {
        try {
            return BaseMapper.getObjectMapper().writeValueAsBytes(taskInstanceMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
