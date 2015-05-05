package uk.org.mcdonnell.stonehouse;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import javax.naming.NamingException;

import org.junit.Test;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnection;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;

public class QueueTest {

    @Test
    public void testGetAllQueues() throws InvalidPropertiesFormatException, IOException, NamingException {
        final ProviderConnectionFactory providerConnectionFactory = new ProviderConnectionFactory();

        final List<ProviderConnection> providerConnections = providerConnectionFactory.getAllProviders();

        for (final ProviderConnection providerConnection : providerConnections) {
            System.out.println(providerConnection.getJNDIInitialContext().getEnvironment().toString());
        }
    }
}
