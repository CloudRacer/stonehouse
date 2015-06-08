package uk.org.mcdonnell.stonehouse.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnections;
import uk.org.mcdonnell.stonehouse.api.destination.Destination;
import uk.org.mcdonnell.stonehouse.api.destination.Destinations;

public class DestinationsTest {

    @Test
    public void getAllQueues() throws Exception {
        final ProviderConnections providerConnectionFactory = new ProviderConnections();

        final List<ProviderConnectionFactory> providerConnections = providerConnectionFactory.getAllProviders();
        for (final ProviderConnectionFactory providerConnection : providerConnections) {
            final Destinations destinations = new Destinations(providerConnection);

            final Iterator<Entry<String, Destination>> it = destinations.getAllDestinations().entrySet().iterator();
            while (it.hasNext()) {
                final Destination destination = it.next().getValue();
                System.out.println(String.format("%s - %s - %s - %s - %s", destination.getDestinationType().toString(), destination.getDestinationName(), destination.getPending(), destination.getCurrent(), destination.getReceived()));
            }
        }
    }
}
