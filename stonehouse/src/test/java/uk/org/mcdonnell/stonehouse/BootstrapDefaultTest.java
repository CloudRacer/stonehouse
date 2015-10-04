package uk.org.mcdonnell.stonehouse;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import uk.org.mcdonnell.common.FileHelper;

public class BootstrapDefaultTest {

    @Test
    public void loadDefaultBootstrap() {
        final File pluginFolder = new File("./plugins");
        try {
            new Bootstrap();

            // final Set<String> expectedPluginFilenames = new HashSet<String>(Arrays.asList("./plugins/activemq-all-5.11.1.jar", "./plugins/plugin-1.0-SNAPSHOT.jar", "./plugins/provider-1.0-SNAPSHOT.jar", "./plugins/weblogic-1.0-SNAPSHOT.jar", "./plugins/wlfullclient.jar"));
            final Set<File> expectedPluginFilenames = new HashSet<File>();
            expectedPluginFilenames.add(new File(String.format("%s%s", pluginFolder, "/activemq-all-5.11.1.jar")));
            expectedPluginFilenames.add(new File(String.format("%s%s", pluginFolder, "/plugin-1.0-SNAPSHOT.jar")));
            expectedPluginFilenames.add(new File(String.format("%s%s", pluginFolder, "/provider-1.0-SNAPSHOT.jar")));
            expectedPluginFilenames.add(new File(String.format("%s%s", pluginFolder, "/weblogic-1.0-SNAPSHOT.jar")));
            expectedPluginFilenames.add(new File(String.format("%s%s", pluginFolder, "/wlfullclient.jar")));

            final List<File> files = FileHelper.getFileList(pluginFolder, ".*.jar");
            final Collection<File> missingPlugins = CollectionUtils.subtract(files, expectedPluginFilenames);
            assertTrue(String.format("Plugin(s) %s were not found.", missingPlugins.toString()), missingPlugins.size() == 0);

            for (final File file : files) {
                assertTrue(String.format("Plugin \"%s\" was not loaded into the classpath.", file.getAbsolutePath()), isJarInClasspath(file));
            }
        } catch (final Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    public static boolean isJarInClasspath(File jarFile) {
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        if (classLoader instanceof URLClassLoader) {
            @SuppressWarnings("resource")
            final URLClassLoader classLoader2 = (URLClassLoader) classLoader;
            final URL[] urls = classLoader2.getURLs();

            for (final URL url : urls) {
                final File file = new File(url.getFile());

                if (file.getPath().endsWith(jarFile.getName())) {
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
