package uk.org.mcdonnell.stonehouse.service.destination;

import javax.jms.JMSException;
import javax.naming.NamingException;

import uk.org.mcdonnell.stonehouse.service.connection.ProviderConnection;
import uk.org.mcdonnell.stonehouse.service.destination.Destinations.DestinationType;

public class Destination extends DestinationStatisticsFactory {

    private String queueName = null;
    private DestinationType destinationType = null;

    private Destination() throws NamingException, JMSException {
        super(null, null, null);
    }

    public Destination(ProviderConnection providerConnection, DestinationType destinationType, String queueName) throws NamingException, JMSException {
        super(providerConnection, destinationType, queueName);

        this.setDestinationName(queueName);
        this.setDestinationType(destinationType);
    }

    public String getDestinationName() {
        return queueName;
    }

    private void setDestinationName(String queueName) {
        this.queueName = queueName;
    }

    public DestinationType getDestinationType() {
        return destinationType;
    }

    private void setDestinationType(DestinationType destinationType) {
        this.destinationType = destinationType;
    }
}
