package uk.org.mcdonnell.stonehouse;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnections;

public class Application
{

    public List<ProviderConnectionFactory> getAllQueues() throws InvalidPropertiesFormatException, IOException {
        final ProviderConnections providerConnectionFactory = new ProviderConnections();

        return providerConnectionFactory.getAllProviders();
    }

    public static void main(final String[] args)
    {}
}
