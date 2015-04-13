package uk.org.mcdonnell.stonehouse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import uk.org.mcdonnell.common.generic.ClasspathLoader;

/**
 * Hello world!
 * 
 */
public class Bootstrap
{
    public Bootstrap() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException, IOException {
        final File pluginFolder = new File("./plugins");

        ClasspathLoader.getInstance().addFolder(pluginFolder, ".*.jar");
    }
}
