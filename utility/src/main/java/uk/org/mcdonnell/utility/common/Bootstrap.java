package uk.org.mcdonnell.utility.common;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.io.FilenameUtils;

import uk.org.mcdonnell.utility.generic.Resource;

/**
 * Load JAR files from the plugin folder into the Classpath.
 * 
 * @author john
 *
 */
public final class Bootstrap {
    public final static String PLUGIN_FOLDERNAME = "plugins";
    private final static File DEFAULT_FOLDER = new File(FilenameUtils.normalizeNoEndSeparator(new File(String.format("./%s", PLUGIN_FOLDERNAME)).getAbsolutePath()));
    private final static String JAR_FILE_EXTENSION = "jar";
    public final static String[] DEFAULT_FILE_EXTENSION = new String[] { JAR_FILE_EXTENSION };

    public Bootstrap() throws BootstrapException {
        start();
    }

    private static void start() throws BootstrapException {
        File pluginsFolder = getPluginFolder();
        loadPlugins(pluginsFolder, DEFAULT_FILE_EXTENSION);
    }

    private static void loadPlugins(final File folder, final String[] extensions) throws BootstrapException {
        try {
            uk.org.mcdonnell.utility.generic.ClasspathHelper.getInstance().addFolder(folder, extensions);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
            throw new BootstrapException(e);
        }
    }

    private static File getPluginFolder() throws BootstrapException {
        Resource resource = new Resource();
        return resource.extractPluginFolder(PLUGIN_FOLDERNAME, DEFAULT_FOLDER);
    }
}