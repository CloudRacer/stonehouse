package uk.org.mcdonnell.stonehouse.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uk.org.mcdonnell.stonehouse.service.Destinations.DestinationType;

public class DestinationTest {

    @Test
    public void getDetination() {
        final String TEST_QUEUE_NAME = "Conv10AMSendQueue";

        Destination destination = new Destination(DestinationType.QUEUE, TEST_QUEUE_NAME);

        assertTrue(destination.getDestinationName() == TEST_QUEUE_NAME);
    }
}
