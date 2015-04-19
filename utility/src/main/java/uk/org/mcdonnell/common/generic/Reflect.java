package uk.org.mcdonnell.common.generic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {

    private String className;
    private Object reflectedObject;
    private Class<?> clazz;

    @SuppressWarnings("unused")
    private Reflect() {}

    public Reflect(String className) {
        setClassName(className);
    }

    public Object executeMethod(String methodName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        return executeMethod(methodName, null);
    }

    public Object executeMethod(String methodName, Object[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        Method method = getClazz().getMethod(methodName);

        return method.invoke(getReflectedObject(), args);
    }

    private Object getReflectedObject() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (reflectedObject == null) {
            setClazz(Class.forName(getClassName()));
            Constructor<?> constructor = getClazz().getConstructor();
            reflectedObject = constructor.newInstance();
        }

        return reflectedObject;
    }

    private String getClassName() {
        return className;
    }

    private void setClassName(String className) {
        this.className = className;
    }

    private Class<?> getClazz() {
        return clazz;
    }

    private void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
