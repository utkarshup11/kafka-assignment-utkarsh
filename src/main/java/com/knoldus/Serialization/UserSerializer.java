package com.knoldus.Serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knoldus.Entity.User;
import org.apache.kafka.common.serialization.Serializer;
import java.util.Map;

public class UserSerializer implements Serializer < User > {

    @Override public void configure(Map map, boolean b) {}
    @Override public void close() {}
    @Override public byte[] serialize(String s, User o) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsBytes(o);
        } catch(Exception e) {
            System.out.println("Error ");
        }
        return retVal;
    }
}