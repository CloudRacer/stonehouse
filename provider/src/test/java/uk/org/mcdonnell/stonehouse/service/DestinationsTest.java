package uk.org.mcdonnell.stonehouse.service;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.junit.Test;

public class DestinationsTest {

    @Test
    public void getAllQueues() throws NamingException, InvalidPropertiesFormatException, IOException, JMSException {
        ProviderConnectionFactory providerConnectionFactory = new ProviderConnectionFactory();

        List<ProviderConnection> providerConnections = providerConnectionFactory.getAllProviders();
        for (ProviderConnection providerConnection : providerConnections) {
            Destinations destinations = new Destinations(providerConnection);

            Iterator<Entry<String, Destination>> it = destinations.getAllDestinations().entrySet().iterator();
            while (it.hasNext()) {
                Destination destination = it.next().getValue();
                System.out.println(String.format("%s - %s - %s", destination.getDestinationType().toString(), destination.getDestinationName(), destination.getTotalNumberOfPendingMessages()));
            }
        }
    }
}
