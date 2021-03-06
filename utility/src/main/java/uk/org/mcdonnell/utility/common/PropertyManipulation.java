package uk.org.mcdonnell.utility.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class PropertyManipulation extends Properties {
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_PROVIDER_PROPERTY_FILENAME = "provider.properties";

    private File file;

    private String filename;

    private static URL installationFolder;

    public PropertyManipulation() throws InvalidPropertiesFormatException, IOException {
        this(DEFAULT_PROVIDER_PROPERTY_FILENAME);
    }

    public PropertyManipulation(final String filename) throws InvalidPropertiesFormatException, IOException {
        setFilename(filename);

        if (getFile().exists()) {
            final FileInputStream fis = new FileInputStream(getFile());
            load(fis);
        }
    }

    public String getFilename() throws FileNotFoundException, IOException {
        final File file = new File(filename);

        if (!file.isAbsolute()) {
            final String absoluteFilename = String.format("%s%s%s", getFolder().getCanonicalPath(), File.separatorChar, filename);

            final File absoluteFile = new File(absoluteFilename);
            if (!absoluteFile.exists()) {
                final File parentFolder = new File(absoluteFile.getParentFile().getCanonicalPath());
                if (!parentFolder.isDirectory()) {
                    parentFolder.mkdirs();
                }

                saveDefaultProperties(getDefaultProperties(absoluteFile.getName()), absoluteFile);
            }

            filename = absoluteFilename;
        } else {
            if (!file.exists()) {
                saveDefaultProperties(getDefaultProperties(file.getName()), file);
            }
        }

        return filename;
    }

    private void setFilename(final String filename) {
        this.filename = filename;
    }

    public File getFile() throws FileNotFoundException, IOException {
        if (file == null) {
            file = new File(getFilename());
        }

        return file;
    }

    private File getFolder() throws FileNotFoundException, IOException {
        // If the "configuration" folder exists, use it, otherwise use "config".
        // Check if "config" exists.
        File folder = getConfigurationFolder(new File(".").getCanonicalFile());
        if (!folder.isDirectory()) {
            folder = getConfigurationFolder(new File(getInstallationFolder().getPath())
                    .getCanonicalFile().getParentFile().getParentFile());
        }

        return folder;
    }

    private File getConfigurationFolder(final File baseFolder) throws FileNotFoundException, IOException {
        // The metadata folder is only present in a Runtime installation.
        final String METADATA_FOLDER_NAME = System.getProperty("com.bf.viaduct.metadata.location", "metadata");
        final String CONFIG_FOLDER_NAME = "config";
        final String CONFIGURATION_FOLDER_NAME = "configuration";

        // If the "metadata" folder exists, use it, otherwise check for existence of the
        // "configuration" folder.
        File folder = new File(String.format("%s%s%s", baseFolder.getCanonicalFile(), File.separatorChar,
                METADATA_FOLDER_NAME));
        if (!folder.isDirectory()) {
            // If the "configuration" folder exists, use it, otherwise use the "config" folder.
            folder = new File(String.format("%s%s%s", baseFolder.getCanonicalFile(), File.separatorChar,
                    CONFIGURATION_FOLDER_NAME));
            if (!folder.isDirectory()) {
                folder = new File(String.format("%s%s%s", baseFolder.getCanonicalFile(), File.separatorChar,
                        CONFIG_FOLDER_NAME));
            }
        }

        return folder;
    }

    private void saveDefaultProperties(final Properties properties, final File file) throws FileNotFoundException, IOException {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);

            properties.store(outputStream, String.format("Property defaults applied automatically on %s.", new Date()));
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private String getDefaultPropertiesFilename(final String filename) throws FileNotFoundException, IOException {
        return String.format("%s%s.default", "/", new File(filename).getName());
    }

    private Properties getDefaultProperties(final String filename) throws FileNotFoundException, IOException {
        Properties defaultProperties = null;

        if (defaultProperties == null) {
            defaultProperties = new Properties();
            final InputStream inputStream = PropertyManipulation.class.getResourceAsStream(getDefaultPropertiesFilename(filename));
            defaultProperties.load(inputStream);
            inputStream.close();
        }

        return defaultProperties;
    }

    private URL getInstallationFolder() {
        if (installationFolder == null) {
            installationFolder = PropertyManipulation.class.getProtectionDomain().getCodeSource().getLocation();
        }

        return installationFolder;
    }
}
