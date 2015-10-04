package uk.org.mcdonnell.common.generic;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import uk.org.mcdonnell.common.ClasspathHelper;

public class ClasspathLoaderTest {

    @Test
    public void LoadClasspath() {
        try {
            final File pluginFolder = new File("../stonehouse/plugins");
            final String[] extensions = new String[] { "jar" };

            ClasspathHelper.getInstance().addFolder(pluginFolder, extensions);

            final Collection<File> files = FileUtils.listFiles(pluginFolder, extensions, true);
            for (final File file : files) {
                assertTrue(String.format("Plugin \"%s\" was not loaded into the classpath.", file.getAbsolutePath()), isJarInClasspath(file));
            }
        } catch (final Exception e) {
            Assert.fail(e.getMessage());
        }
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
                    System.out.println(jarFile + " exist");
                    return true;
                }
            }

            System.out.println(jarFile + " not exist");
            return false;
        } else {
            return false;
        }
    }
}
