package uk.org.mcdonnell.common.generic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import uk.org.mcdonnell.common.ClasspathHelper;
import uk.org.mcdonnell.common.FileHelper;

public class ClasspathLoaderTest {

    @Test
    public void LoadClasspath() {
        final File pluginFolder = new File("../plugins/plugins");
        String classpath;

        try {
            ClasspathHelper.getInstance().addFolder(pluginFolder, ".*.jar");

            classpath = ClasspathHelper.getInstance().getClasspath();

            final List<File> files = FileHelper.getFileList(pluginFolder, ".*.jar");
            for (final File file : files) {
                assertFalse(classpath.indexOf(file.getName()) == 0);
            }

            assertTrue(true);
        } catch (final Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
