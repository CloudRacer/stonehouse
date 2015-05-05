package uk.org.mcdonnell.common.generic;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.List;
import java.util.Vector;

public class FileManipulation
{
    private static URL installationFolder;

    /**
     * Searches the specified folder for files that match one or more of the Regular Expressions.
     * 
     * @param directory
     *            the folder to search. All sub-folders will also be searched.
     * @param filter
     *            a list Regular Expressions, delimited by ";", to describe files to include. Only
     *            one expression needs to match a filename in order for it to be included.
     * @return
     */
    public static List<File> getFileList(final File directory, final String filter)
    {
        final FilenameFilter filenameFilter = new FilenameFilter()
        {
            @Override
            public boolean accept(final File dir, final String name)
            {
                final String[] filters = filter.split(";");
                String filter = null;
                boolean match = false;

                int i = 0;
                while (i < filters.length && !match)
                {
                    filter = filters[i];

                    match = Search.isRegExFound(name, filter);

                    i++;
                }
                return match;
            }
        };

        final List<File> files = listFiles(directory, filenameFilter, true);

        return files;
    }

    /**
     * Searches the specified folder for files that match one or more of the Regular Expressions.
     * 
     * @param directory
     *            the folder to search.
     * @param filter
     *            a list Regular Expressions, delimited by ";", to describe files to include. Only
     *            one expression needs to match a filename in order for it to be included.
     * @param recurce
     *            if true, sub-folders will also be search.
     * @return
     */
    private static List<File> listFiles(final File directory, final FilenameFilter filter, final boolean recurse)
    {
        final Vector<File> files = new Vector<File>();
        final File[] entries = directory.listFiles();
        if (entries != null)
        {
            for (final File entry : entries)
            {
                if (filter == null || filter.accept(directory, entry.getName()))
                {
                    files.add(entry);
                }

                if (recurse && entry.isDirectory())
                {
                    files.addAll(listFiles(entry, filter, recurse));
                }
            }
        }

        return files;
    }

    public static URL getInstallationFolder()
    {
        if (installationFolder == null)
        {
            installationFolder = FileManipulation.class.getProtectionDomain().getCodeSource().getLocation();
        }

        return installationFolder;
    }
}
