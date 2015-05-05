package uk.org.mcdonnell.stonehouse;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnection;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;

public class Application
{

    public List<ProviderConnection> getAllQueues() throws InvalidPropertiesFormatException, IOException {
        final ProviderConnectionFactory providerConnectionFactory = new ProviderConnectionFactory();

        return providerConnectionFactory.getAllProviders();
    }

    public static void main(final String[] args)
    {}
}
