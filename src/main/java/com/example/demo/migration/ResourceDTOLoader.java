package com.example.demo.migration;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ResourceDTOLoader {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ResourceDTOLoader() {
        // do not create
    }

    public static <T> List<T> load(String resourcePath, Class<T> tClass) {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, tClass);

        try (InputStream inputStream = ResourceDTOLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            return OBJECT_MAPPER.readValue(inputStream, type);
        } catch (IOException iox) {
            throw new ReadResourceDTOException("Failed to read resource " + resourcePath, iox);
        }
    }
}
