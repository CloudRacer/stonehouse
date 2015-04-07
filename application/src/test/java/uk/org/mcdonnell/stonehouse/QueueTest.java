package uk.org.mcdonnell.stonehouse;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import javax.naming.NamingException;

import org.junit.Test;

import uk.org.mcdonnell.stonehouse.service.ProviderConnection;
import uk.org.mcdonnell.stonehouse.service.ProviderConnectionFactory;

public class QueueTest {

    @Test
    public void testGetAllQueue() throws InvalidPropertiesFormatException, IOException, NamingException {
        ProviderConnectionFactory providerConnectionFactory = new ProviderConnectionFactory();

        List<ProviderConnection> providerConnections = providerConnectionFactory.getAllProviders();

        for (ProviderConnection providerConnection : providerConnections) {
            System.out.println(providerConnection.getJNDIInitialContext().getEnvironment().toString());
        }
    }
}
