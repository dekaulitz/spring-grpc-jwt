package com.github.spring.grpc.util;

import com.github.spring.grpc.entity.UserEntity;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Log4j2
public class ReflectionHelper {


    public static Object fromProto(Class<?> obj, Object reflectionObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Method[] method = obj.getMethods();
        Object newInstance = obj.getDeclaredConstructor().newInstance();
        for (int i = 0; i < method.length; i++) {
            if (isSetter(method[i])) {
                try {
                    Method currentMethod = reflectionObject.getClass().getMethod(method[i]
                            .getName().replace("set", "get"));
                    method[i].invoke(newInstance, currentMethod.invoke(reflectionObject));
                } catch (NoSuchMethodException e) {
                    e.getStackTrace();
                }
            }
        }
        return newInstance;
    }

//    public static Object toProto(Class<?> builderClass, Object reflectionObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
////        Field[] fields = reflectionObject.getClass().getFields();
////        for (int i = 0; i < fields.length; i++) {
////            builderClass.getMethod(toSetter(fields[i].getName())).invoke(builderClass,"fahmi@gmcil.com");
////                    //.invoke(builderClass, reflectionObject.getClass().getMethod(toGetter(fields[i].getName())).invoke(reflectionObject));
////        }
//        Method[] methods=builderClass.getMethods();
//        for (int i=0;i<methods.length;i++){
//            methods.
//            log.info(methods[i].getName());
//        }
//        return builderClass;
//    }

    public static String toSetter(String field) {
        return "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    public static String toGetter(String field) {
        return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }


    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get")) return false;
        if (method.getParameterTypes().length != 0) return false;
        if (void.class.equals(method.getReturnType())) return false;
        return true;
    }

    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set")) return false;
        if (method.getParameterTypes().length != 1) return false;
        return true;
    }

    public static Object toProto(org.grpc.proto.UserEntity.Builder builder, UserEntity user) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Method[] methods = builder.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (isSetter(methods[i])) {
                if (methods[i].getName().equals("setName")) {
                    try {
                        methods[i].invoke(builder, "fahmi@gm.com");
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error(e.getMessage());
                    }
                }
            }

        }

        return builder;
    }


//    public static Object toProto(Class<UserEntity.Builder> builderClass, UserEntity user) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
////        Field[] fields = user.getClass().getFields();
////        for (int i = 0; i < fields.length; i++) {
////            builderClass.getMethod(toSetter(fields[i].getName())).invoke(builderClass,
////                    user.getClass().getMethod(toGetter(fields[i].getName())).invoke(user));
////        }
//        Method[] methods = builderClass.getDeclaredMethods();
//        Object ob = builderClass.getConstructor().newInstance();
//        for (int i = 0; i < methods.length; i++) {
//            log.info(methods[i].getName());
////            if (methods[i].getName().equals("setEmail")) {
////
////               // methods[i].invoke(ob, "fahmi");
////            }
//        }
//        return null;
//    }
}
