package uk.org.mcdonnell.common.generic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search
{
    private Search()
    {
    }

    public static Matcher findRegEx(String data, String regex)
    {
        Matcher matcher = null;

        if (data != null)
        {
            Pattern pattern = Pattern.compile(regex);
            matcher = pattern.matcher(data);
        }

        return matcher;
    }
    
    public static boolean isRegExFound(String data, String regex){
    	Matcher matcher = findRegEx(data, regex);
    	
    	return (matcher != null ? matcher.matches() : false );
    }
}
