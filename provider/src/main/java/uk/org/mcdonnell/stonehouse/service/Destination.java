package uk.org.mcdonnell.stonehouse.service;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.InitialContext;
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
        InitialContext initialContext = getProviderConnection().getJNDIInitialContext();

        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("weblogic.jms.ConnectionFactory");
        connectionFactory.createConnection();

        return 0;
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
