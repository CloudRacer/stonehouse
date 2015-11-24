package uk.org.mcdonnell.stonehouse;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import uk.org.mcdonnell.utility.common.Bootstrap;

public class BootstrapDefaultTest {

    @Test
    public void loadDefaultBootstrap() {
        final File pluginFolder = new File("./plugins");
        try {
            // Load plugin JAR files.
            Bootstrap.start();

            final Set<File> expectedPluginFilenames = new HashSet<File>(
                    Arrays.asList(
                            new File(String.format("%s/%s", pluginFolder, "activemq-all-5.11.1.jar")),
                            new File(String.format("%s/%s", pluginFolder, "plugin-1.0-SNAPSHOT.jar")),
                            new File(String.format("%s/%s", pluginFolder, "provider-1.0-SNAPSHOT.jar"))));

            final Collection<File> files = FileUtils.listFiles(pluginFolder, new String[] { "jar" }, true);

            assertEqualCollections(expectedPluginFilenames, files);

            // Check that each file in the plugin folder is also in the Classpath.
            for (final File file : files) {
                assertTrue(String.format("Plugin \"%s\" was not loaded into the classpath.", file.getAbsolutePath()), isJarInClasspath(file));
            }
        } catch (final Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    private void assertEqualCollections(final Set<File> expected, final Collection<File> actual) {
        // Check for files that we expected but not found.
        final Collection<File> missingPlugins = CollectionUtils.subtract(actual, expected);
        assertTrue(String.format("Plugin(s) %s were not found.", Arrays.toString(missingPlugins.toArray())), missingPlugins.size() == 0);

        // Check for files that we found but not expected.
        final Collection<File> unexpectedPlugins = CollectionUtils.subtract(expected, actual);
        assertTrue(String.format("Plugin(s) %s were not expected.", Arrays.toString(unexpectedPlugins.toArray())), unexpectedPlugins.size() == 0);
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

            System.out.println(jarFile + " not exist");
            return false;
        } else {
            return false;
        }
    }
}
