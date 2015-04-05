package uk.org.mcdonnell.common.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class PropertyManipulation extends Properties
{
    private static final long serialVersionUID = 1L;

    private File file;

    private String filename;

    @SuppressWarnings("unused")
    private PropertyManipulation()
    {
    }

    public PropertyManipulation(String filename) throws InvalidPropertiesFormatException, IOException
    {
        setFilename(filename);

        if (getFile().exists())
        {
            FileInputStream fis = new FileInputStream(getFile());
            load(fis);
        }
    }

    public String getFilename() throws FileNotFoundException, IOException
    {
        File file = new File(filename);

        if (!file.isAbsolute())
        {
            String absoluteFilename =
                    String.format("%s%s%s", getFolder().getCanonicalPath(), File.separatorChar, filename);

            File absoluteFile = new File(absoluteFilename);
            if (!absoluteFile.exists())
            {
                File parentFolder = new File(absoluteFile.getParentFile().getCanonicalPath());
                if (!parentFolder.isDirectory())
                {
                    parentFolder.mkdirs();
                }

                saveDefaultProperties(getDefaultProperties(absoluteFile.getName()), absoluteFile);
            }

            filename = absoluteFilename;
        }
        else
        {
            if (!file.exists())
            {
                saveDefaultProperties(getDefaultProperties(file.getName()), file);
            }
        }

        return filename;
    }

    private void setFilename(String filename)
    {
        this.filename = filename;
    }

    public File getFile() throws FileNotFoundException, IOException
    {
        if (file == null)
        {
            file = new File(getFilename());
        }

        return file;
    }

    private File getFolder() throws FileNotFoundException, IOException
    {
        // If the "configuration" folder exists, use it, otherwise use "config".
        // Check if "config" exists.
        File folder = getConfigurationFolder(new File(".").getCanonicalFile());
        if (!folder.isDirectory())
        {
            folder =
                    getConfigurationFolder(new File(FileManipulation.getInstallationFolder().getPath())
                            .getCanonicalFile().getParentFile().getParentFile());
        }

        return folder;
    }

    private File getConfigurationFolder(File baseFolder) throws FileNotFoundException, IOException
    {
        // The metadata folder is only present in a Runtime installation.
        final String METADATA_FOLDER_NAME = System.getProperty("com.bf.viaduct.metadata.location", "metadata");
        final String CONFIG_FOLDER_NAME = "config";
        final String CONFIGURATION_FOLDER_NAME = "configuration";

        // If the "metadata" folder exists, use it, otherwise check for existence of the
        // "configuration" folder.
        File folder =
                new File(String.format("%s%s%s", baseFolder.getCanonicalFile(), File.separatorChar,
                        METADATA_FOLDER_NAME));
        if (!folder.isDirectory())
        {
            // If the "configuration" folder exists, use it, otherwise use the "config" folder.
            folder =
                    new File(String.format("%s%s%s", baseFolder.getCanonicalFile(), File.separatorChar,
                            CONFIGURATION_FOLDER_NAME));
            if (!folder.isDirectory())
            {
                folder =
                        new File(String.format("%s%s%s", baseFolder.getCanonicalFile(), File.separatorChar,
                                CONFIG_FOLDER_NAME));
            }
        }

        return folder;
    }

    private void saveDefaultProperties(Properties properties, File file) throws FileNotFoundException, IOException
    {
        OutputStream outputStream = null;
        try
        {
            outputStream = new FileOutputStream(file);

            properties.store(outputStream, String.format("Property defaults applied automatically on %s.", new Date()));
        }
        finally
        {
            if (outputStream != null)
            {
                outputStream.close();
            }
        }
    }

    private String getDefaultPropertiesFilename(String filename) throws FileNotFoundException, IOException
    {
        return String.format("%s%s.default", "/", new File(filename).getName());
    }

    private Properties getDefaultProperties(String filename) throws FileNotFoundException, IOException
    {
        Properties defaultProperties = null;

        if (defaultProperties == null)
        {
            defaultProperties = new Properties();
            InputStream inputStream =
                    PropertyManipulation.class.getResourceAsStream(getDefaultPropertiesFilename(filename));
            defaultProperties.load(inputStream);
            inputStream.close();
        }

        return defaultProperties;
    }
}
