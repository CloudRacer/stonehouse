package uk.org.mcdonnell.stonehouse.api.destination;

import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnection;
import uk.org.mcdonnell.stonehouse.api.destination.Destinations.DestinationType;
import uk.org.mcdonnell.stonehouse.api.destination.statistics.DestinationStatisticsFactory;

public class Destination extends DestinationStatisticsFactory {

    private String queueName = null;
    private DestinationType destinationType = null;

    private Destination() throws NamingException, JMSException {
        super(null, null, null);
    }

    public Destination(final ProviderConnection providerConnection, final DestinationType destinationType, final String queueName) throws NamingException, JMSException {
        super(providerConnection, destinationType, queueName);

        this.setDestinationName(queueName);
        this.setDestinationType(destinationType);
    }

    public String getDestinationName() {
        return queueName;
    }

    private void setDestinationName(final String queueName) {
        this.queueName = queueName;
    }

    public DestinationType getDestinationType() {
        return destinationType;
    }

    private void setDestinationType(final DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    public javax.jms.Destination getDestination() throws NamingException {
        final InitialContext initialContext = getProviderConnection().getJNDIInitialContext();
        final javax.jms.Destination destination = (javax.jms.Destination) initialContext.lookup(String.format("queue/%s", getDestinationName()));

        return destination;
    }
}
