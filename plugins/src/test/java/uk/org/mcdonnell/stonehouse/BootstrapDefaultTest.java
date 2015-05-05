package uk.org.mcdonnell.stonehouse;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import uk.org.mcdonnell.common.generic.ClasspathLoader;
import uk.org.mcdonnell.common.generic.FileManipulation;

public class BootstrapDefaultTest {

    @Test
    public void loadDefaultBootstrap() {
        final File pluginFolder = new File("./plugins");
        String classpath;

        try {
            new Bootstrap();

            classpath = ClasspathLoader.getInstance().getClasspath();

            final List<File> files = FileManipulation.getFileList(pluginFolder, ".*.jar");
            for (final File file : files) {
                assertFalse(classpath.indexOf(file.getName()) == 0);
            }

            assertTrue(true);
        } catch (final Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
