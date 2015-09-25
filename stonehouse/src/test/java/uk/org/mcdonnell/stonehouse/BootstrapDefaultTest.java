package uk.org.mcdonnell.stonehouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import uk.org.mcdonnell.common.ClasspathHelper;
import uk.org.mcdonnell.common.FileHelper;

public class BootstrapDefaultTest {

    @Test
    public void loadDefaultBootstrap() {
        final File pluginFolder = new File("./plugins");
        String classpath;

        try {
            new Bootstrap();

            classpath = ClasspathHelper.getInstance().getClasspath();

            final List<File> files = FileHelper.getFileList(pluginFolder, ".*.jar");
            assertEquals(5, files.size());
            for (final File file : files) {
                assertFalse(classpath.indexOf(file.getName()) == 0);
            }
        } catch (final Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
