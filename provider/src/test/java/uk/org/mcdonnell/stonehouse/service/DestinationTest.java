package uk.org.mcdonnell.stonehouse.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.junit.Test;

import uk.org.mcdonnell.stonehouse.service.Destinations.DestinationType;

public class DestinationTest {

    @Test
    public void getDetination() throws InvalidPropertiesFormatException, IOException, NamingException, JMSException {
        final String TEST_QUEUE_NAME = "Conv10AMSendQueue";

        ProviderConnectionFactory providerConnectionFactory = new ProviderConnectionFactory();
        ProviderConnection providerConnection = providerConnectionFactory.getAllProviders().get(0);
        Destination destination = new Destination(providerConnection, DestinationType.QUEUE, TEST_QUEUE_NAME);

        assertTrue(destination.getDestinationName() == TEST_QUEUE_NAME);

        destination.getTotalNumberOfPendingMessages();

        assertTrue(destination.getTotalNumberOfPendingMessages() == 0);
    }
}
