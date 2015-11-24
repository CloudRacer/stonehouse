package uk.org.mcdonnell.utility.generic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

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
            final Collection<File> fileList = FileUtils.listFiles(pluginFolder, extensions, true);

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
        private static void addFile(final String filename) throws NoSuchMethodException, SecurityException,
                IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException,
                IOException {
            final File file = new File(filename);
            if (!isJarInClasspath(file)) {
                addURL(file.toURI().toURL());
            }
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

        private static boolean isJarInClasspath(File jarFile) {
            final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

            if (classLoader instanceof URLClassLoader) {
                @SuppressWarnings("resource")
                final URLClassLoader classLoader2 = (URLClassLoader) classLoader;
                final URL[] urls = classLoader2.getURLs();

                for (final URL url : urls) {
                    final File file = new File(url.getFile());

                    if (file.getAbsolutePath().equals(jarFile.getAbsolutePath())) {
                        return true;
                    }
                }

                return false;
            } else {
                return false;
            }
        }
    }
}
