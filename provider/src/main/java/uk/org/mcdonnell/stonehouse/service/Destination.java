package uk.org.mcdonnell.stonehouse.service;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.naming.NamingException;

import uk.org.mcdonnell.stonehouse.service.Destinations.DestinationType;

public class Destination {

    private ProviderConnection providerConnection = null;
    private String queueName = null;
    private DestinationType destinationType = null;

    @SuppressWarnings("unused")
    private Destination() {

    }

    public Destination(ProviderConnection providerConnection, DestinationType destinationType, String queueName) {
        this.setProviderConnection(providerConnection);
        this.setQueueName(queueName);
        this.setDestinationType(destinationType);
    }

    public long getTotalNumberOfPendingMessages() throws NamingException, JMSException {
        long messageCount = 0;

        // TODO: count the messages in a Topic also.
        if (getDestinationType() == DestinationType.QUEUE) {
            // TODO: Again, put the JNDI queue root into configuration.
            QueueBrowser queueBrowser = getProviderConnection().getQueueBrowser(getDestinationName());

            @SuppressWarnings("unchecked")
            Enumeration<Message> enumeration = queueBrowser.getEnumeration();
            while (enumeration.hasMoreElements()) {
                enumeration.nextElement();
                messageCount++;
            }
        }

        return messageCount;
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

    private ProviderConnection getProviderConnection() {
        return providerConnection;
    }

    private void setProviderConnection(ProviderConnection providerConnection) {
        this.providerConnection = providerConnection;
    }
}
