package uk.org.mcdonnell.stonehouse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import uk.org.mcdonnell.stonehouse.service.DestinationTest;
import uk.org.mcdonnell.stonehouse.service.DestinationsTest;
import uk.org.mcdonnell.stonehouse.service.ProviderConnectionTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ProviderConnectionTest.class, DestinationsTest.class, DestinationTest.class })
public class ProviderTest {
    private final static File DEFAULT_FOLDER = new File("../plugins/plugins");

    @BeforeClass
    public static void setupClass() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException, IOException {
        new Bootstrap(DEFAULT_FOLDER);
    }
}
