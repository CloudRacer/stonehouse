package uk.org.mcdonnell.stonehouse;

import static org.junit.Assert.assertTrue;

import java.io.File;
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
        final File pluginFolder = new File(".\\plugins");
        try {
            new Bootstrap();

            // final Set<String> expectedPluginFilenames = new HashSet<String>(Arrays.asList(".\\plugins\\activemq-all-5.11.1.jar", ".\\plugins\\plugin-1.0-SNAPSHOT.jar", ".\\plugins\\provider-1.0-SNAPSHOT.jar", ".\\plugins\\weblogic-1.0-SNAPSHOT.jar", ".\\plugins\\wlfullclient.jar"));
            final Set<File> expectedPluginFilenames = new HashSet<File>();
            expectedPluginFilenames.add(new File(String.format("%s%s", pluginFolder, "\\activemq-all-5.11.1.jar")));
            expectedPluginFilenames.add(new File(String.format("%s%s", pluginFolder, "\\plugin-1.0-SNAPSHOT.jar")));
            expectedPluginFilenames.add(new File(String.format("%s%s", pluginFolder, "\\provider-1.0-SNAPSHOT.jar")));
            expectedPluginFilenames.add(new File(String.format("%s%s", pluginFolder, "\\weblogic-1.0-SNAPSHOT.jar")));
            expectedPluginFilenames.add(new File(String.format("%s%s", pluginFolder, "\\wlfullclient.jar")));

            final List<File> files = FileHelper.getFileList(pluginFolder, ".*.jar");
            final Collection<File> missingPlugins = CollectionUtils.subtract(files, expectedPluginFilenames);
            assertTrue(String.format("Plugin(s) %s were not loaded.", missingPlugins.toString()), missingPlugins.size() == 0);
        } catch (final Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
