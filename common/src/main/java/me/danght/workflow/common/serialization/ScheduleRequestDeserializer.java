package me.danght.workflow.common.serialization;

import me.danght.workflow.common.msg.ScheduleRequestMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

/**
 * @author DangHT
 * @date 2021/03/11
 */
public class ScheduleRequestDeserializer implements Deserializer<ScheduleRequestMessage> {

    @Override
    public ScheduleRequestMessage deserialize(String s, byte[] bytes) {
        try {
            return BaseMapper.getObjectMapper().readValue(bytes, ScheduleRequestMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
