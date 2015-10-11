package uk.org.mcdonnell.stonehouse.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InvalidPropertiesFormatException;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnections;
import uk.org.mcdonnell.stonehouse.api.destination.Destination;
import uk.org.mcdonnell.stonehouse.api.destination.Destinations.DestinationType;
import uk.org.mcdonnell.stonehouse.api.destination.statistics.DestinationStatisticsFactoryUnsupportedException;
import uk.org.mcdonnell.stonehouse.helper.ActiveMQHelperBaseIT;
import uk.org.mcdonnell.utility.common.Bootstrap;

public class DestinationTest extends ActiveMQHelperBaseIT {
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
    public void getSingleDestination() throws InvalidPropertiesFormatException, IOException, NamingException, JMSException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, DestinationStatisticsFactoryUnsupportedException {
        final String TEST_QUEUE_NAME = "test_queue_1";

        final ProviderConnections providerConnections = new ProviderConnections();
        final ProviderConnectionFactory providerConnectionFactory = providerConnections.getAllProviders().get(1);
        final Destination destination = new Destination(providerConnectionFactory, DestinationType.QUEUE, TEST_QUEUE_NAME);

        assertTrue(destination.getDestinationName() == TEST_QUEUE_NAME);
        Assert.assertEquals(2, destination.getPending());
    }
}
