package uk.org.mcdonnell.stonehouse;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

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

        final Map<Integer, ProviderConnectionFactory> providerConnections = providerConnectionFactory.getAllProviders();

        for (final Map.Entry<Integer, ProviderConnectionFactory> providerConnection : providerConnections.entrySet()) {
            System.out.println(providerConnection.getValue().getJNDIInitialContext().getEnvironment().toString());
        }

        assertTrue(true);
    }
}
