package me.danght.workflow.common.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.danght.workflow.common.msg.ProcessInstanceMessage;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author DangHT
 * @date 2021/03/11
 */
public class ProcessInstanceSerializer implements Serializer<ProcessInstanceMessage> {

    @Override
    public byte[] serialize(String s, ProcessInstanceMessage processInstanceMessage) {
        try {
            return BaseMapper.getObjectMapper().writeValueAsBytes(processInstanceMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
