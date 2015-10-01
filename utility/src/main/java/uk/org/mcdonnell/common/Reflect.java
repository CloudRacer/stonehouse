package uk.org.mcdonnell.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class Reflect {

    private String className;
    private String methodName;
    private Class<?>[] methodArguments;
    private Class<?> clazz;
    private Object[] constructorArguments;
    private Class<?>[] constructorArgumentTypes;
    private Method method;
    private Object object;

    public Reflect(final String className, final Object[] constructorArguments) {
        setClassName(className);
        setConstructorArguments(constructorArguments);
    }

    public Reflect(final String className, final String methodName, final Class<?>[] methodArguments) {
        setClassName(className);
        setMethodName(methodName);
        setArguments(methodArguments);
    }

    public Reflect(final Object object, final String methodName) {
        this(object, methodName, null);
    }

    public Reflect(final Object object, final String methodName, final Class<?>[] arguments) {
        setObject(object);
        setMethodName(methodName);
        setArguments(arguments);
    }

    public Object executeMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        return executeMethod(null);
    }

    public Object executeMethod(final Object[] arguments) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        return getMethod().invoke(getObject(), arguments);
    }

    private Method getMethodDeclaration(final String methodName, final Class<?>[] arguments) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Method method = getClazz().getMethod(methodName, arguments);

        return method;
    }

    private String getClassName() {
        return className;
    }

    private void setClassName(final String className) {
        this.className = className;
    }

    private Class<?> getClazz() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        if (clazz == null) {
            if (getClassName() != null) {
                clazz = Class.forName(getClassName());
            } else {
                clazz = getObject().getClass();
            }
        }

        return clazz;
    }

    private Method getMethod() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (method == null) {
            setMethod(getMethodDeclaration(getMethodName(), getArguments()));
        }

        return method;
    }

    private void setMethod(final Method method) {
        this.method = method;
    }

    private Class<?>[] getArguments() {
        return methodArguments;
    }

    private void setArguments(final Class<?>[] arguments) {
        methodArguments = arguments;
    }

    private String getMethodName() {
        return methodName;
    }

    private void setMethodName(final String methodName) {
        this.methodName = methodName;
    }

    public Object getObject() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        if (object == null) {
            if (getConstructorArguments() != null) {
                object = getClazz().getConstructor(getConstructorArgumentTypes()).newInstance(getConstructorArguments());
            } else {
                object = getClazz().newInstance();
            }
        }
        return object;
    }

    private void setObject(final Object object) {
        this.object = object;
    }

    private Object[] getConstructorArguments() {
        return constructorArguments;
    }

    private void setConstructorArguments(final Object[] constructorArguments) {
        this.constructorArguments = constructorArguments;
        setConstructorArgumentTypes(null);
    }

    private Class<?>[] getConstructorArgumentTypes() {
        if (getConstructorArguments() == null) {
            setConstructorArgumentTypes(null);
        } else {
            constructorArgumentTypes = new Class[constructorArguments.length];
            for (int i = 0; i < getConstructorArguments().length; i++) {
                constructorArgumentTypes[i] = getConstructorArguments()[i].getClass();
            }
        }
        return constructorArgumentTypes;
    }

    private void setConstructorArgumentTypes(final Class<?>[] constructorArgumentTypes) {
        this.constructorArgumentTypes = constructorArgumentTypes;
    }
}
