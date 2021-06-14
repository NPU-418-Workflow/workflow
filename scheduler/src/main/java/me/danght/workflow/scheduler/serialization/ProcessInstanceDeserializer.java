package me.danght.workflow.scheduler.serialization;

import me.danght.workflow.scheduler.msg.ProcessInstanceMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

/**
 * @author DangHT
 * @date 2021/03/11
 */
public class ProcessInstanceDeserializer implements Deserializer<ProcessInstanceMessage> {

    @Override
    public ProcessInstanceMessage deserialize(String s, byte[] bytes) {
        try {
            return BaseMapper.getObjectMapper().readValue(bytes, ProcessInstanceMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
