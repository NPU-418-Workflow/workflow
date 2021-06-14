package me.danght.workflow.scheduler.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.danght.workflow.scheduler.msg.TaskRequestMessage;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author DangHT
 * @date 2021/03/11
 */
public class TaskRequestSerializer implements Serializer<TaskRequestMessage> {

    @Override
    public byte[] serialize(String s, TaskRequestMessage taskRequestMessage) {
        try {
            return BaseMapper.getObjectMapper().writeValueAsBytes(taskRequestMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
