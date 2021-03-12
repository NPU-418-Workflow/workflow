package me.danght.workflow.common.serialization;

import me.danght.workflow.common.msg.TaskRequestMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

/**
 * @author DangHT
 * @date 2021/03/11
 */
public class TaskRequestDeserializer implements Deserializer<TaskRequestMessage> {

    @Override
    public TaskRequestMessage deserialize(String s, byte[] bytes) {
        try {
            return BaseMapper.getObjectMapper().readValue(bytes, TaskRequestMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
