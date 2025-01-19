package com.alurachallenge.libros.service;

public interface IJsonConverter {
    <T> T convertJsonToObject(String json, Class<T> clazz);
}
