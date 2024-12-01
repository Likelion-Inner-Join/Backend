package com.likelion.innerjoin.post.util;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.innerjoin.post.exception.JsonConvertException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class JsonConverter implements AttributeConverter<List<String>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try{
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e){
            throw new JsonConvertException("Json 파싱 과정에서 오류가 발생하였습니다.");
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try{
            return objectMapper.readValue(dbData, new TypeReference<>() {
            });
        } catch (Exception e){
            throw new JsonConvertException("Json 파싱 과정에서 오류가 발생하였습니다.");
        }
    }
}
