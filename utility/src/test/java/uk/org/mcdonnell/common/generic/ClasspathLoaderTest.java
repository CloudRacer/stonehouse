package uk.org.mcdonnell.common.generic;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ClasspathLoaderTest {

    @Test
    public void LoadClasspath() {
        File pluginFolder = new File("../plugins/plugins");
        String classpath;

        try {
            ClasspathLoader.getInstance().addFolder(pluginFolder, ".*.jar");

            classpath = ClasspathLoader.getInstance().getClasspath();

            List<File> files = FileManipulation.getFileList(pluginFolder, ".*.jar");
            for (File file : files) {
                assertTrue(classpath.indexOf(file.getName()) != 0);
            }

            assertTrue(true);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
