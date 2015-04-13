package uk.org.mcdonnell.common.generic;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class ClasspathLoaderTest {

    @Test
    public void LoadClasspath() {
        File pluginFolder = new File("../plugins/plugins");

        try {
            ClasspathLoader.getInstance().addFolder(pluginFolder);

            assertTrue(true);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
