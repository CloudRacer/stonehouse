package uk.org.mcdonnell.common.generic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClasspathLoader
{
    private static ClasspathLoader instance;

    // Singleton initialiser
    static
    {
        instance = new ClasspathLoader();
    }

    /**
     * Singleton access method.
     * 
     * @return Singleton
     */
    public static ClasspathLoader getInstance()
    {
        return instance;
    }

    /**
     * Add a JAR to the JVM classpath, during runtime.
     */
    protected static class SystemClasspath
    {
        private SystemClasspath()
        {}

        /**
         * Parameters of the method to add a URL to the System classes.
         */
        private static final Class<?>[] parameters = new Class[]
        {
                URL.class
        };

        /**
         * Adds a file to the JVM classpath.
         * 
         * @param filename
         *            the name of a JAR file
         * @throws MalformedURLException
         * @throws InvocationTargetException
         * @throws IllegalArgumentException
         * @throws IllegalAccessException
         * @throws SecurityException
         * @throws NoSuchMethodException
         * @throws IOException
         */
        public static void addFile(String filename) throws NoSuchMethodException, SecurityException,
                IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException,
                IOException
        {
            File f = new File(filename);
            addURL(f.toURI().toURL());
        }

        /**
         * Adds the content pointed by the URL to the JVM classpath.
         * 
         * @param url
         *            the URL pointing to the content to be added
         * @throws IOException
         * @throws SecurityException
         * @throws NoSuchMethodException
         * @throws InvocationTargetException
         * @throws IllegalArgumentException
         * @throws IllegalAccessException
         */
        private static void addURL(URL url) throws IOException, NoSuchMethodException, SecurityException,
                IllegalAccessException, IllegalArgumentException, InvocationTargetException
        {
            URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            Class<?> sysclass = URLClassLoader.class;

            Method method = sysclass.getDeclaredMethod("addURL", parameters);
            method.setAccessible(true);
            method.invoke(sysloader, new Object[]
            {
                    url
            });
        }
    }
}
