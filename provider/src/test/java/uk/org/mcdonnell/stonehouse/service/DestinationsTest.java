package uk.org.mcdonnell.stonehouse.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnection;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;
import uk.org.mcdonnell.stonehouse.api.destination.Destination;
import uk.org.mcdonnell.stonehouse.api.destination.Destinations;

public class DestinationsTest {

    @Test
    public void getAllQueues() throws Exception {
        ProviderConnectionFactory providerConnectionFactory = new ProviderConnectionFactory();

        List<ProviderConnection> providerConnections = providerConnectionFactory.getAllProviders();
        for (ProviderConnection providerConnection : providerConnections) {
            Destinations destinations = new Destinations(providerConnection);

            Iterator<Entry<String, Destination>> it = destinations.getAllDestinations().entrySet().iterator();
            while (it.hasNext()) {
                Destination destination = it.next().getValue();
                System.out.println(String.format("%s - %s - %s - %s - %s", destination.getDestinationType().toString(), destination.getDestinationName(), destination.getPending(), destination.getCurrent(), destination.getReceived()));
            }
        }
    }
}
