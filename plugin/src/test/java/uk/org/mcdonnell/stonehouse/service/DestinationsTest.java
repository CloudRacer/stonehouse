package uk.org.mcdonnell.stonehouse.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnections;
import uk.org.mcdonnell.stonehouse.api.destination.Destination;
import uk.org.mcdonnell.stonehouse.api.destination.Destinations;
import uk.org.mcdonnell.stonehouse.helper.ActiveMQHelperBaseIT;
import uk.org.mcdonnell.utility.common.Bootstrap;

public class DestinationsTest extends ActiveMQHelperBaseIT {

    @BeforeClass
    public static void setupClass() throws Exception {
        // Load plugin JAR files.
        Bootstrap.start();

        // Start the ActiveMQ broker.
        getBroker();

        // Create test data.
        createTestData();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        setConnection(null);
        setBroker(null);
    }

    @Test
    public void getAllQueues() throws Exception {
        final ProviderConnections providerConnections = new ProviderConnections();

        final Map<Integer, ProviderConnectionFactory> allProviders = providerConnections.getAllProviders();
        for (final Map.Entry<Integer, ProviderConnectionFactory> providerConnection : allProviders.entrySet()) {
            final Destinations destinations = new Destinations(providerConnection.getValue());

            final Iterator<Entry<String, Destination>> it = destinations.getAllDestinations().entrySet().iterator();
            while (it.hasNext()) {
                final Destination destination = it.next().getValue();
                // System.out.println(String.format("%s - %s - %s - %s - %s", destination.getDestinationType().toString(), destination.getDestinationName(), destination.getPending(), destination.getCurrent(), destination.getReceived()));
                System.out.println(String.format("%s - %s.", destination.getDestinationType().toString(), destination.getDestinationName()));
            }
        }
    }
}
