package uk.org.mcdonnell.stonehouse.api.destination;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnection;

public class Destinations {

    public enum DestinationType {
        QUEUE, TOPIC
    };

    private ProviderConnection providerConnection = null;

    private Hashtable<String, Destination> destinationList = null;

    public Destinations(final ProviderConnection providerConnection) {
        setProviderConnection(providerConnection);
    }

    public Hashtable<String, Destination> getAllDestinations() throws Exception {
        if (getDestinationList() == null) {
            setDestinationList(new Hashtable<String, Destination>());

            try {
                final String jndiPrefixQueue = "jndi.prefix.queue";
                // final String jndiPrefixTopic = "jndi.prefix.topic";
                final String jndiPrefixQueueValue = (String) getProviderConnection().getJNDIInitialContext().getEnvironment().get(jndiPrefixQueue);
                // final String jndiPrefixTopicValue = (String) getProviderConnection().getJNDIInitialContext().getEnvironment().get(jndiPrefixTopic);

                addDestinations(DestinationType.QUEUE, getProviderConnection().getJNDIInitialContext().list(jndiPrefixQueueValue));
                // TODO: add Topic support.
                // addDestinations(DestinationType.TOPIC, getProviderConnection().getJNDIInitialContext().list(jndiPrefixTopicValue));
            } catch (final Exception e) {
                // Rest the the Queue List.
                setDestinationList(null);

                throw e;
            }
        }

        return getDestinationList();
    }

    private void addDestinations(final DestinationType destinationType, final NamingEnumeration<NameClassPair> jndiList) throws NamingException, JMSException {
        while (jndiList.hasMore()) {
            final String jndiName = jndiList.next().getName();

            final Destination destination = new Destination(getProviderConnection(), destinationType, jndiName);
            getDestinationList().put(destination.getDestinationName(), destination);
        }
    }

    private ProviderConnection getProviderConnection() {
        return providerConnection;
    }

    private void setProviderConnection(final ProviderConnection providerConnection) {
        this.providerConnection = providerConnection;
    }

    private Hashtable<String, Destination> getDestinationList() {
        return destinationList;
    }

    private void setDestinationList(final Hashtable<String, Destination> destinationList) {
        this.destinationList = destinationList;
    }
}
