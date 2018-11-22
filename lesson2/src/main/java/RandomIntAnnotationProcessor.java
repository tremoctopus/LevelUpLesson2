import exceptions.AnnotationInvalidFieldTypeException;

import java.lang.reflect.Field;
import java.util.Random;

public class RandomIntAnnotationProcessor {
    public static void setField(Object object) throws IllegalAccessException {
        Class<?> objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();

        for (Field field: fields) {
            RandomInt annotation = field.getAnnotation(RandomInt.class);
            if (annotation != null) {
                int number = new Random()
                        .nextInt(annotation.max() - annotation.min() + 1) + annotation.min();
                try {
                    setFieldValue(field, object, number);
                } catch (AnnotationInvalidFieldTypeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void setFieldValue(Field field, Object o, int value) throws IllegalAccessException, AnnotationInvalidFieldTypeException {

        String typeName = field.getType().getSimpleName();
        if(isFieldTypeInteger(typeName)){
            field.setAccessible(true);
            field.set(o, value);
        }else {
            throw new AnnotationInvalidFieldTypeException("Field type is not valid. Required: Integer, found: " + typeName);
        }
    }

    private static boolean isFieldTypeInteger(String typeName){
        return typeName.equals("int") || typeName.equals("Integer");
    }
}
