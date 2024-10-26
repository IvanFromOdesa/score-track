package com.teamk.scoretrack.module.commons.mongo.mapper;

import com.teamk.scoretrack.module.commons.util.ReflectUtils;
import org.bson.Document;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO: testing
public class MongoManualDocumentMapper {
    public static <T> T mapDocumentToClass(Document doc, Class<T> clazz) {
        T instance = ReflectUtils.newInstanceOf(clazz);
        if (instance == null) {
            throw new IllegalStateException("Could not create an instance of class: " + clazz.getName());
        }
        List<Field> fields = ReflectUtils.getAllFields(clazz);
        Map<String, Object> docMap = doc.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        fields.forEach(field -> {
            String fieldName = field.getName();
            Object value = docMap.get(fieldName);
            if (value == null && Object.class.isAssignableFrom(field.getType())) {
                value = mapNestedDocument(docMap, fieldName, field.getType());
            }
            if (value != null) {
                if (Enum.class.isAssignableFrom(field.getType())) {
                    Method valueOf;
                    try {
                        valueOf = field.getType().getMethod("valueOf", String.class);
                        value = valueOf.invoke(null, value);
                    } catch (ReflectiveOperationException e) {
                        throw new RuntimeException(e);
                    }
                }
                field.setAccessible(true);
                try {
                    field.set(instance, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return instance;
    }

    private static Object mapNestedDocument(Map<String, Object> docMap, String prefix, Class<?> fieldType) {
        Map<String, Object> nestedDocMap = docMap.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(prefix + "."))
                .collect(Collectors.toMap(
                        entry -> entry.getKey().substring((prefix + ".").length()),
                        Map.Entry::getValue));
        if (nestedDocMap.isEmpty()) {
            return null;
        }
        try {
            return createInstanceWithArguments(fieldType, nestedDocMap);
        } catch (Exception e) {
            throw new RuntimeException("Could not create an instance of class: " + fieldType.getName(), e);
        }
    }

    private static Object createInstanceWithArguments(Class<?> fieldType, Map<String, Object> nestedDocMap) throws Exception {
        Constructor<?>[] constructors = fieldType.getConstructors();
        if (constructors.length == 0) {
            return null;
        }
        Constructor<?> constructor = constructors[0];
        Parameter[] params = constructor.getParameters();
        Object[] args = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            String paramName = params[i].getName();
            args[i] = nestedDocMap.get(paramName);
        }
        return ReflectUtils.newInstanceOf(fieldType, args);
    }
}
