package uk.org.mcdonnell.common.generic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {

    private String className;
    private String methodName;
    private Class<?>[] arguments;
    private Class<?> clazz;
    private Method method;
    private Object object;

    @SuppressWarnings("unused")
    private Reflect() {}

    public Reflect(String className, String methodName, Class<?>[] arguments) {
        setClassName(className);
        setMethodName(methodName);
        setArguments(arguments);
    }

    public Reflect(Object object, String methodName) {
        this(object, methodName, null);
    }

    public Reflect(Object object, String methodName, Class<?>[] arguments) {
        setObject(object);
        setMethodName(methodName);
        setArguments(arguments);
    }

    public Object executeMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        return executeMethod(null);
    }

    public Object executeMethod(Object[] arguments) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        return getMethod().invoke(getObject(), arguments);
    }

    private Method getMethodDeclaration(String methodName, Class<?>[] arguments) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Method method = getClazz().getMethod(methodName, arguments);

        return method;
    }

    private String getClassName() {
        return className;
    }

    private void setClassName(String className) {
        this.className = className;
    }

    private Class<?> getClazz() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (clazz == null) {
            if (getClassName() != null) {
                clazz = Class.forName(getClassName());
            } else
            {
                clazz = getObject().getClass();
            }
        }

        return clazz;
    }

    private Method getMethod() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (method == null) {
            setMethod(getMethodDeclaration(getMethodName(), getArguments()));
        }

        return method;
    }

    private void setMethod(Method method) {
        this.method = method;
    }

    private Class<?>[] getArguments() {
        return arguments;
    }

    private void setArguments(Class<?>[] arguments) {
        this.arguments = arguments;
    }

    private String getMethodName() {
        return methodName;
    }

    private void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    private Object getObject() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (object == null) {
            object = getClazz().newInstance();
        }
        return object;
    }

    private void setObject(Object object) {
        this.object = object;
    }
}
