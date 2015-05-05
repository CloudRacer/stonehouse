package uk.org.mcdonnell.stonehouse.api.connection;

import java.util.Hashtable;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProviderConnection {
    Hashtable<String, String> jndiInitialContextEnvironment;
    InitialContext jndiInitialContext;

    public ProviderConnection() {}

    public void setJNDIInitialContextEnvironment(
            final Hashtable<String, String> jndiInitialContextEnvironment) {
        this.jndiInitialContextEnvironment = jndiInitialContextEnvironment;
    }

    private Hashtable<String, String> getJNDIInitialContextEnvironment() {
        if (jndiInitialContextEnvironment == null) {
            jndiInitialContextEnvironment = new Hashtable<String, String>();
        }

        return jndiInitialContextEnvironment;
    }

    public InitialContext getJNDIInitialContext() throws NamingException {
        if (jndiInitialContext == null) {
            jndiInitialContext = new InitialContext(
                    getJNDIInitialContextEnvironment());
        }

        return jndiInitialContext;
    }

    public QueueBrowser getQueueBrowser(final String queueName) throws NamingException, JMSException {
        final QueueSession queueSession = getQueueSession();
        final InitialContext initialContext = getJNDIInitialContext();
        final Queue queue = (Queue) initialContext.lookup(String.format("queue/%s", queueName));
        final QueueBrowser queueBrowser = queueSession.createBrowser(queue);
        return queueBrowser;
    }

    private QueueSession getQueueSession() throws NamingException, JMSException {
        final QueueConnection queueConnection = getQueueConnection();
        final QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        return queueSession;
    }

    private QueueConnection getQueueConnection() throws NamingException, JMSException {
        final InitialContext initialContext = getJNDIInitialContext();
        final ConnectionFactory connectionFactory = (QueueConnectionFactory) initialContext.lookup("weblogic.jms.ConnectionFactory");
        connectionFactory.createConnection();
        final QueueConnectionFactory queueConnectionFactory =
                (QueueConnectionFactory) initialContext.lookup("weblogic.jms.ConnectionFactory");
        final QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
        return queueConnection;
    }
}
