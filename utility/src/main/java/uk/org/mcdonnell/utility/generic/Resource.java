package uk.org.mcdonnell.utility.generic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;

import uk.org.mcdonnell.utility.common.BootstrapException;

public class Resource {

    public Resource() {
    }

    public File extractPluginFolder(final String resourceFolder, final File destinationFolder) throws BootstrapException {
        Validate.notNull(resourceFolder, "\"resource\" cannot be null.");
        Validate.notNull(destinationFolder, "\"destinationFolder\" cannot be null.");

        final InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream(String.format("%s/", resourceFolder));
        try {
            if (!destinationFolder.isDirectory()) {
                destinationFolder.mkdirs();
            }

            List<String> files = IOUtils.readLines(resourceStream, Charsets.UTF_8);
            for (String filename : files) {
                InputStream resource = this.getClass().getClassLoader().getResourceAsStream(String.format("%s/%s", resourceFolder, filename));
                OutputStream file = new FileOutputStream(new File(FilenameUtils.concat(destinationFolder.getAbsolutePath(), filename)));
                IOUtils.copy(resource, file);
            }
        } catch (IOException e) {
            throw new BootstrapException(e);
        }

        return destinationFolder;
    }
}
