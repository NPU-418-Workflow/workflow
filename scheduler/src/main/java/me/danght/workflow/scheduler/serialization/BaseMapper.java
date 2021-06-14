package me.danght.workflow.scheduler.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author DangHT
 * @date 2021/03/07
 */
public class BaseMapper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

}