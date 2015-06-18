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

public class ProviderConnectionFactory {
    Hashtable<String, String> jndiInitialContextEnvironment;
    InitialContext jndiInitialContext;

    public ProviderConnectionFactory() {}

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
        // TODO: not all queue JNDI names have a prefix.
        final Queue queue = (Queue) initialContext.lookup(String.format("%s%s", getJNDIInitialContext().getEnvironment().get("jndi.prefix.queue"), queueName));
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
        // TODO: create the connection factory differently for each vendor; using properties for the configuration file (http://java.dzone.com/articles/jms-activemq).
        final ConnectionFactory connectionFactory = (QueueConnectionFactory) initialContext.lookup(getVendorJNDIConnectionFactoryName());
        connectionFactory.createConnection();
        final QueueConnectionFactory queueConnectionFactory =
                (QueueConnectionFactory) initialContext.lookup(getVendorJNDIConnectionFactoryName());
        final QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
        return queueConnection;
    }

    private String getVendorJNDIConnectionFactoryName() throws NamingException {
        return getJNDIInitialContext().getEnvironment().get("jndi.connection.factory").toString();
    }
}
