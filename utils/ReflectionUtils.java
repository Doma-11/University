/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/**
 *
 * @author boric
 */
public class ReflectionUtils {
    
      public static void readClassInfo(Class<?> clazz, StringBuilder classInfo) {
        appendPackage(clazz, classInfo);
        appendModifiers(clazz, classInfo);
        appendParent(clazz, classInfo, true);
        appendInterfaces(clazz, classInfo);
    }
    
    private static void appendPackage(Class<?> clazz, StringBuilder classInfo) {
        classInfo
                .append("<h4>").append(clazz.getPackage())
                .append("</h4>\n\n");
    }
    
     private static void appendModifiers(Class<?> clazz, StringBuilder classInfo) {

        classInfo
                .append("<h3>")
                .append(Modifier.toString(clazz.getModifiers()))
                .append(" ")
                .append(clazz.getSimpleName())
                .append("</h3>");
    }
     
     private static void appendParent(Class<?> clazz, StringBuilder classInfo, boolean first) {
        Class<?> parent = clazz.getSuperclass();
        if (parent == null) {
            classInfo.append("</h4>");
            return;
        }
        if (first) {
            classInfo.append("<h4>extends");
        }
        classInfo
                .append(" ")
                .append(parent.getSimpleName());
        appendParent(parent, classInfo, false);
    }

    private static void appendInterfaces(Class<?> clazz, StringBuilder classInfo) {
        if (clazz.getInterfaces().length > 0) {
            classInfo.append("<h4>implements");
        }
        for (Class<?> in : clazz.getInterfaces()) {
            classInfo
                    .append(" ")
                    .append(in.getSimpleName());
        }
        classInfo.append("</h4>");
    }
    
    public static void readClassAndMembersInfo(Class<?> clazz, StringBuilder classAndMembersInfo) {
        classAndMembersInfo.append("<p>");
        readClassInfo(clazz, classAndMembersInfo);
        appendFields(clazz, classAndMembersInfo);
        appendMethods(clazz, classAndMembersInfo);
        appendConstructors(clazz, classAndMembersInfo);
        classAndMembersInfo.append("</p>");
    }

    private static void appendFields(Class<?> clazz, StringBuilder classAndMembersInfo) {
        Field[] fields = clazz.getDeclaredFields(); 
        classAndMembersInfo.append("<p>");
        for (Field field : fields) {
            classAndMembersInfo
                    .append("<h5>")
                    .append(field)
                    .append("</h5>");
        }
        classAndMembersInfo.append("</p>");
    }

    private static void appendMethods(Class<?> clazz, StringBuilder classAndMembersInfo) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            classAndMembersInfo.append("<p>");
            appendMethodAnnotations(method, classAndMembersInfo);
            classAndMembersInfo
                    .append(Modifier.toString(method.getModifiers())) // jel private public etc
                    .append("&nbsp;")
                    .append(method.getReturnType().getSimpleName()) // sto vraca metoda: void int double object...
                    .append("&nbsp;")
                    .append(method.getName());// ime metode
            appendParameters(method, classAndMembersInfo); // parametri
            appendExceptions(method, classAndMembersInfo);
        }
        classAndMembersInfo.append("</p>");
    }
    
     private static void appendMethodAnnotations(Executable executable, StringBuilder classAndMembersInfo) {
        for (Annotation annotation : executable.getAnnotations()) {
            classAndMembersInfo
                    .append(annotation)
                    .append("<br>");
        }
    }

    private static void appendParameters(Executable executable, StringBuilder classAndMembersInfo) {
        classAndMembersInfo.append("(");
        for (Parameter parameter : executable.getParameters()) {
            classAndMembersInfo
                    .append(parameter)
                    .append(",&nbsp;");
        }
        if (classAndMembersInfo.toString().endsWith(",&nbsp;")) {
            classAndMembersInfo.delete(classAndMembersInfo.length() - 7, classAndMembersInfo.length());
        }
        classAndMembersInfo.append(")");
    }

    private static void appendExceptions(Executable executable, StringBuilder classAndMembersInfo) {
        Class<?>[] exceptionTypes = executable.getExceptionTypes();
        if (exceptionTypes.length > 0) {
            classAndMembersInfo.append(" throws ");
            for (Class<?> exceptionType : exceptionTypes) {
                classAndMembersInfo
                        .append(exceptionType)
                        .append(", ");
            }
            if (classAndMembersInfo.toString().endsWith(", ")) {
                classAndMembersInfo.delete(classAndMembersInfo.length() - 2, classAndMembersInfo.length());
            }
        }
    }

    private static void appendConstructors(Class<?> clazz, StringBuilder classAndMembersInfo) {
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            classAndMembersInfo.append("<p>");
            appendMethodAnnotations(constructor, classAndMembersInfo);
            classAndMembersInfo
                    .append(Modifier.toString(constructor.getModifiers()))
                    .append(" ")
                    .append(constructor.getName());
            appendParameters(constructor, classAndMembersInfo);
            appendExceptions(constructor, classAndMembersInfo);
            classAndMembersInfo.append("</p>");
        }
    }
}
