package uk.org.mcdonnell.common;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

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
        private SystemClasspath() {}

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
        public void addFolder(final File pluginFolder, final String fileFilter) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, MalformedURLException, IOException {
            final List<File> fileList = FileHelper.getFileList(pluginFolder, fileFilter);

            for (final File file : fileList) {
                System.out.println(String.format("Adding the JAR \"%s\" to the JVM classpath...", file.getAbsolutePath()));

                SystemClasspath.addFile(file.getAbsolutePath());
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
        public static void addFile(final String filename) throws NoSuchMethodException, SecurityException,
                IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException,
                IOException {
            final File f = new File(filename);
            addURL(f.toURI().toURL());
        }

        public String getClasspath() {
            final ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
            final URL[] urls = ((URLClassLoader) sysClassLoader).getURLs();
            String classpath = null;

            for (int i = 0; i < urls.length; i++) {
                if (classpath != null) {
                    classpath = classpath + ":" + urls[i].getFile();
                } else {
                    classpath = urls[i].getFile();
                }
            }

            return classpath;
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
        private static void addURL(final URL url) throws IOException, NoSuchMethodException, SecurityException,
                IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            final URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            final Class<?> sysclass = URLClassLoader.class;

            final Method method = sysclass.getDeclaredMethod("addURL", parameters);
            method.setAccessible(true);
            method.invoke(sysloader, new Object[] {
                    url
            });
        }
    }
}
