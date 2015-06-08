package uk.org.mcdonnell.stonehouse.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InvalidPropertiesFormatException;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.junit.Test;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnections;
import uk.org.mcdonnell.stonehouse.api.destination.Destination;
import uk.org.mcdonnell.stonehouse.api.destination.Destinations.DestinationType;
import uk.org.mcdonnell.stonehouse.api.destination.statistics.DestinationStatisticsFactoryUnsupportedException;

public class DestinationTest {

    @Test
    public void getSingleDestination() throws InvalidPropertiesFormatException, IOException, NamingException, JMSException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, DestinationStatisticsFactoryUnsupportedException {
        final String TEST_QUEUE_NAME = "Crane31AMProcessQueue";

        final ProviderConnections providerConnectionFactory = new ProviderConnections();
        final ProviderConnectionFactory providerConnection = providerConnectionFactory.getAllProviders().get(0);
        final Destination destination = new Destination(providerConnection, DestinationType.QUEUE, TEST_QUEUE_NAME);

        assertTrue(destination.getDestinationName() == TEST_QUEUE_NAME);
        assertTrue(destination.getPending() == 0);

        System.out.println(String.format("%s: %s.", destination.getDestinationName(), destination.getPending()));
    }
}
