package uk.org.mcdonnell.stonehouse.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InvalidPropertiesFormatException;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnections;
import uk.org.mcdonnell.stonehouse.api.destination.Destination;
import uk.org.mcdonnell.stonehouse.api.destination.Destinations.DestinationType;
import uk.org.mcdonnell.stonehouse.api.destination.statistics.DestinationStatisticsFactoryUnsupportedException;

public class DestinationTest {

    @Test
    public void getSingleDestination() throws InvalidPropertiesFormatException, IOException, NamingException, JMSException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, DestinationStatisticsFactoryUnsupportedException {
        final String TEST_QUEUE_NAME = "test_queue_1";

        final ProviderConnections providerConnections = new ProviderConnections();
        final ProviderConnectionFactory providerConnectionFactory = providerConnections.getAllProviders().get(1);
        final Destination destination = new Destination(providerConnectionFactory, DestinationType.QUEUE, TEST_QUEUE_NAME);

        assertTrue(destination.getDestinationName() == TEST_QUEUE_NAME);
        Assert.assertEquals(2, destination.getPending());
    }
}
