package eu.senla.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class BeanUtils {

    @SneakyThrows
    public <T> void copyNonNullValues(T source, T target) {
        Field[] declaredFields = source.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            Object value = declaredField.get(source);
            if(value != null){
                declaredField.set(target, value);
            }
        }
    }
}
