package me.danght.workflow.scheduler.serialization;

import me.danght.workflow.scheduler.msg.TaskInstanceMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

/**
 * @author DangHT
 * @date 2021/03/11
 */
public class TaskInstanceDeserializer implements Deserializer<TaskInstanceMessage> {

    @Override
    public TaskInstanceMessage deserialize(String s, byte[] bytes) {
        try {
            return BaseMapper.getObjectMapper().readValue(bytes, TaskInstanceMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
