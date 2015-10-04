package uk.org.mcdonnell.stonehouse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import uk.org.mcdonnell.common.ClasspathHelper;

/**
 * Hello world!
 *
 */
public final class Bootstrap {
    private final File DEFAULT_FOLDER = new File("./plugins");
    private final String[] DEFAULT_FILE_EXTENSION = new String[] { "jar" };

    public Bootstrap() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException, IOException {
        new Bootstrap(DEFAULT_FOLDER, DEFAULT_FILE_EXTENSION);
    }

    public Bootstrap(final File folder) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, MalformedURLException, IOException {
        new Bootstrap(folder, DEFAULT_FILE_EXTENSION);
    }

    private Bootstrap(final File folder, final String[] extensions) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, MalformedURLException, IOException {
        ClasspathHelper.getInstance().addFolder(folder, extensions);
    }

    public static void start() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException, IOException {
        new Bootstrap();
    }
}