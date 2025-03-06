package com.psicovirtual.community.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.json.JsonSanitizer;
import lombok.extern.slf4j.Slf4j;

import static com.psicovirtual.community.utils.Constants.DOUBLE_QUOTE;

@Slf4j
public class ConversionUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ConversionUtils(){}

    /**
     * Method to convert an object to JSON
     * @param object
     * @return
     * @param <T>
     * @throws JsonProcessingException
     */
    public static <T> String parseObjectToJson(T object) throws JsonProcessingException {
        String json = null;
        objectMapper.registerModule(new JavaTimeModule());
        try {
             json = objectMapper.writeValueAsString(object);
            log.info("converted json: " + json);
        } catch (JsonProcessingException e) {
            log.error("Error converting object to JSON: " + e.getMessage());
            throw e;
        }
        return json;
    }

    /**
     * Method to convert JSON to object
     * @param json
     * @param clazz
     * @return
     * @param <T>
     * @throws JsonProcessingException
     */
    public static <T> T parseJsonToObject(String json, Class<T> clazz) throws JsonProcessingException {
        T object= null;
        objectMapper.registerModule(new JavaTimeModule());
        try {
            String sanitizedJson = JsonSanitizer.sanitize(json);
            if (sanitizedJson.startsWith(DOUBLE_QUOTE) && sanitizedJson.endsWith(DOUBLE_QUOTE)) {
                // Unescape the JSON string
                sanitizedJson = objectMapper.readValue(sanitizedJson, String.class);
            }
            log.info("sanitized json: " + sanitizedJson);
            object = objectMapper.readValue(sanitizedJson, clazz);
            log.info("converted object: " + object);
        } catch (JsonProcessingException e) {
            log.error("Error converting JSON to object: " + e.getMessage());
            throw e;
        }
        return object;
    }
}
