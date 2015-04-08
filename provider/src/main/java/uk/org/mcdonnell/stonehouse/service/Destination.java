package uk.org.mcdonnell.stonehouse.service;

import uk.org.mcdonnell.stonehouse.service.Destinations.DestinationType;

public class Destination {

    private String queueName = null;
    private DestinationType destinationType = null;

    @SuppressWarnings("unused")
    private Destination() {

    }

    public Destination(DestinationType destinationType, String queueName) {
        this.setQueueName(queueName);
        this.setDestinationType(destinationType);
    }

    public String getDestinationName() {
        return queueName;
    }

    private void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public DestinationType getDestinationType() {
        return destinationType;
    }

    private void setDestinationType(DestinationType destinationType) {
        this.destinationType = destinationType;
    }
}
