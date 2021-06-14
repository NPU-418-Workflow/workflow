package me.danght.workflow.scheduler.serialization;

import me.danght.workflow.scheduler.msg.ProcessRequestMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

/**
 * @author DangHT
 * @date 2021/03/11
 */
public class ProcessRequestDeserializer implements Deserializer<ProcessRequestMessage> {

    @Override
    public ProcessRequestMessage deserialize(String s, byte[] bytes) {
        try {
            return BaseMapper.getObjectMapper().readValue(bytes, ProcessRequestMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
