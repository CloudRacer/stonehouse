package uk.org.mcdonnell.stonehouse.service;

import java.util.Enumeration;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
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
        ConnectionFactory connectionFactory = (QueueConnectionFactory) initialContext.lookup("weblogic.jms.ConnectionFactory");
        connectionFactory.createConnection();
        QueueConnectionFactory queueConnectionFactory =
                (QueueConnectionFactory) initialContext.lookup("weblogic.jms.ConnectionFactory");
        QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
        QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        // TODO: Again, put the JNDI queue root into configuration.
        Queue queue = (Queue) initialContext.lookup(String.format("queue/%s", getDestinationName()));
        QueueBrowser queueBrowser = queueSession.createBrowser(queue);
        @SuppressWarnings("unchecked")
        Enumeration<Message> enumeration = queueBrowser.getEnumeration();

        long messageCount = 0;
        while (enumeration.hasMoreElements()) {
            enumeration.nextElement();
            messageCount++;
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
