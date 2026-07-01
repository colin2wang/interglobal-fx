package com.globalfx.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class JsonUtil {

    private JsonUtil() {}

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public static String toJson(Object obj) {
        try { return MAPPER.writeValueAsString(obj); }
        catch (JsonProcessingException e) { throw new RuntimeException("JSON serialization error", e); }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try { return MAPPER.readValue(json, clazz); }
        catch (JsonProcessingException e) { throw new RuntimeException("JSON deserialization error", e); }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeRef) {
        try { return MAPPER.readValue(json, typeRef); }
        catch (JsonProcessingException e) { throw new RuntimeException("JSON deserialization error", e); }
    }

    public static <T> java.util.List<T> parseArray(String json, Class<T> clazz) {
        try { return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(java.util.List.class, clazz)); }
        catch (JsonProcessingException e) { throw new RuntimeException("JSON deserialization error", e); }
    }

    public static java.util.Map<String, Object> toMap(Object obj) {
        return MAPPER.convertValue(obj, new TypeReference<>() {});
    }
}
