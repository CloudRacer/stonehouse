package uk.org.mcdonnell.stonehouse;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnections;
import uk.org.mcdonnell.utility.common.Bootstrap;
import uk.org.mcdonnell.utility.common.BootstrapException;

public class ProviderContextTest {

    @BeforeClass
    public static void setupClass() throws BootstrapException {
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
