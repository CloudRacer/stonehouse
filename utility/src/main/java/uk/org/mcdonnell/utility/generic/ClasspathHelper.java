package uk.org.mcdonnell.utility.generic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public final class ClasspathHelper {
    private static SystemClasspath instance;

    // Singleton initialiser
    static {
        if (instance == null) {
            instance = new SystemClasspath();
        }
    }

    /**
     * Singleton access method.
     *
     * @return Singleton
     */
    public static SystemClasspath getInstance() {
        return instance;
    }

    /**
     * Add a JAR to the JVM classpath, during runtime.
     */
    public static class SystemClasspath {

        static Logger log = Logger.getLogger(SystemClasspath.class.getName());

        private SystemClasspath() {
        }

        /**
         * Parameters of the method to add a URL to the System classes.
         */
        private static final Class<?>[] parameters = new Class[] {
                URL.class
        };

        /**
         * Add all the files in the folder that match the filter.
         *
         * @param pluginFolder
         * @throws NoSuchMethodException
         * @throws IllegalAccessException
         * @throws InvocationTargetException
         * @throws MalformedURLException
         * @throws IOException
         */
        public void addFolder(final File pluginFolder, final String[] extensions) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, MalformedURLException, IOException {
            log.info(String.format("Adding the JAR files in the folder \"%s\" to the JVM classpath...", pluginFolder.getAbsolutePath()));

            final Collection<File> fileList = FileUtils.listFiles(pluginFolder, extensions, true);

            for (final File file : fileList) {
                SystemClasspath.addFile(file.toURI().toURL());
            }
        }

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
        private static void addFile(final URL url) throws MalformedURLException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
            if (!isJarInClasspath(url)) {
                addURL(url);
            }
        }

        private static List<URL> getClasspath() {
            final ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
            final URL[] urlArray = ((URLClassLoader) sysClassLoader).getURLs();
            List<URL> urls = Arrays.asList(urlArray);

            return urls;
        }

        /**
         * Adds the content, pointed to by the URL, to the JVM classpath.
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
        private static void addURL(final URL url) throws IOException, NoSuchMethodException, SecurityException,
                IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            log.info(String.format("Adding the JAR \"%s\" to the JVM classpath...", url.getFile()));

            final URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            final Class<?> sysclass = URLClassLoader.class;

            final Method method = sysclass.getDeclaredMethod("addURL", parameters);
            method.setAccessible(true);
            method.invoke(sysloader, new Object[] {
                    url
            });
        }

        private static boolean isJarInClasspath(URL url) throws MalformedURLException {
            List<URL> urls = getClasspath();
            return urls.contains(url);
        }
    }
}
