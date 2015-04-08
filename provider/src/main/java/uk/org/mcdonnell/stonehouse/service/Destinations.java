package uk.org.mcdonnell.stonehouse.service;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class Destinations {

    private ProviderConnection providerConnection = null;

    private Hashtable<String, Destination> queueList = null;

    @SuppressWarnings("unused")
    private Destinations() {

    }

    public Destinations(ProviderConnection providerConnection) {
        setProviderConnection(providerConnection);
    }

    public Hashtable<String, Destination> getAllQueues() throws NamingException, JMSException {
        if (queueList == null) {
            queueList = new Hashtable<String, Destination>();

            try {
                // TODO: Add this to the configuration file - one for each provider.
                final String JNDI_ROOT = "queue";
                NamingEnumeration<NameClassPair> jndiRootList = getProviderConnection().getJNDIInitialContext().list(JNDI_ROOT);

                while (jndiRootList.hasMore()) {
                    final String queueName = jndiRootList.next().getName();

                    // TODO: Identify Queues and add them to the Queue list.
                    final Destination queue = new Destination(queueName);
                    queueList.put(queue.getQueueName(), queue);
                }
            } catch (Exception e) {
                // Rest the the Queue List.
                queueList = null;

                throw e;
            }
        }

        return queueList;
    }

    private ProviderConnection getProviderConnection() {
        return providerConnection;
    }

    private void setProviderConnection(ProviderConnection providerConnection) {
        this.providerConnection = providerConnection;
    }
}
