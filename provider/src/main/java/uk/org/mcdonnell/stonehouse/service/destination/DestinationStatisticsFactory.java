package uk.org.mcdonnell.stonehouse.service.destination;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.naming.NamingException;

import uk.org.mcdonnell.stonehouse.service.connection.ProviderConnection;
import uk.org.mcdonnell.stonehouse.service.destination.Destinations.DestinationType;

public abstract class DestinationStatisticsFactory extends DestinationStatistics {

    private ProviderConnection providerConnection = null;
    private DestinationType destinationType;
    private String destinationName;

    private DestinationStatisticsFactory() {
        super(0);
    }

    public DestinationStatisticsFactory(ProviderConnection providerConnection, DestinationType destinationType, String destinationName) throws NamingException, JMSException {
        super(0);

        setProviderConnection(providerConnection);
        setDestinationType(destinationType);
        setDestinationName(destinationName);
    }

    private ProviderConnection getProviderConnection() {
        return providerConnection;
    }

    private void setProviderConnection(ProviderConnection providerConnection) {
        this.providerConnection = providerConnection;
    }

    private void fetchStatistics() throws NamingException, JMSException {
        if (getProviderConnection().getJNDIInitialContext().getEnvironment().get("java.naming.factory.initial").toString().toLowerCase().startsWith("weblogic")) {
            System.out.println("JMS Message Provider vendor identified as WebLogic.");
            // TODO: get statistics by reflection and use the WebLogic native helper method instead of the default routine.
            super.setPending(getTotalNumberOfPendingMessages());
        } else {
            super.setPending(getTotalNumberOfPendingMessages());
        }
    }

    private long getTotalNumberOfPendingMessages() throws NamingException, JMSException {
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

    private DestinationType getDestinationType() {
        return destinationType;
    }

    private void setDestinationType(DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    private String getDestinationName() {
        return destinationName;
    }

    private void setDestinationName(String queueName) {
        this.destinationName = queueName;
    }

    @Override
    public long getPending() {
        try {
            fetchStatistics();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        // TODO Auto-generated method stub
        return super.getPending();
    }
}
