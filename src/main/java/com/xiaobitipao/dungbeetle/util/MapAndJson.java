package com.xiaobitipao.dungbeetle.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaobitipao.dungbeetle.exception.http.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

@Converter
public class MapAndJson implements AttributeConverter<Map<String, Object>, String> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        try {
            return mapper.writeValueAsString(stringObjectMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        try {
            Map<String, Object> map = mapper.readValue(s, HashMap.class);
            return map;
        } catch (Exception e) {
            // TODO: log
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }
}
