package me.danght.workflow.scheduler.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.danght.workflow.scheduler.msg.ScheduleRequestMessage;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author DangHT
 * @date 2021/03/10
 */
public class ScheduleRequestSerializer implements Serializer<ScheduleRequestMessage> {

    @Override
    public byte[] serialize(String s, ScheduleRequestMessage scheduleRequestMessage) {
        try {
            return BaseMapper.getObjectMapper().writeValueAsBytes(scheduleRequestMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
