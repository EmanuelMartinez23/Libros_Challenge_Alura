package com.alurachallenge.libros.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
///  Converter JSON
public class JsonConverter implements IJsonConverter {
    private  ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public <T> T convertJsonToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converter JSON to objeto", e);
        }
    }
}
