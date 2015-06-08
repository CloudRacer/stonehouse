package uk.org.mcdonnell.stonehouse;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnections;

public class ProviderContextTest {

    @BeforeClass
    public static void setupClass() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException, IOException {
        new Bootstrap();
    }

    @Test
    public void testGetAllProviders() throws InvalidPropertiesFormatException, IOException, NamingException {
        final ProviderConnections providerConnectionFactory = new ProviderConnections();

        final List<ProviderConnectionFactory> providerConnections = providerConnectionFactory.getAllProviders();

        for (final ProviderConnectionFactory providerConnection : providerConnections) {
            System.out.println(providerConnection.getJNDIInitialContext().getEnvironment().toString());
        }

        assertTrue(true);
    }
}
