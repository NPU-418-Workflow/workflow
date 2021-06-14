package me.danght.workflow.scheduler.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.danght.workflow.scheduler.msg.ProcessRequestMessage;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author DangHT
 * @date 2021/03/11
 */
public class ProcessRequestSerializer implements Serializer<ProcessRequestMessage> {

    @Override
    public byte[] serialize(String s, ProcessRequestMessage processRequestMessage) {
        try {
            return BaseMapper.getObjectMapper().writeValueAsBytes(processRequestMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
