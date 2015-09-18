package uk.org.mcdonnell.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SearchHelper {
    private SearchHelper() {}

    public static Matcher findRegEx(final String data, final String regex) {
        Matcher matcher = null;

        if (data != null) {
            final Pattern pattern = Pattern.compile(regex);
            matcher = pattern.matcher(data);
        }

        return matcher;
    }

    public static boolean isRegExFound(final String data, final String regex) {
        final Matcher matcher = findRegEx(data, regex);

        return matcher != null ? matcher.matches() : false;
    }
}
