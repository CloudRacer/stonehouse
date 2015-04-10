package uk.org.mcdonnell.stonehouse.service;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class Destinations {

    public enum DestinationType {
        QUEUE, TOPIC
    };

    private ProviderConnection providerConnection = null;

    private Hashtable<String, Destination> destinationList = null;

    @SuppressWarnings("unused")
    private Destinations() {

    }

    public Destinations(ProviderConnection providerConnection) {
        setProviderConnection(providerConnection);
    }

    public Hashtable<String, Destination> getAllDestinations() throws NamingException, JMSException {
        if (getDestinationList() == null) {
            setDestinationList(new Hashtable<String, Destination>());

            try {
                // TODO: Add this to the configuration file - one for each provider.
                addDestinations(DestinationType.QUEUE, getProviderConnection().getJNDIInitialContext().list("queue"));
                addDestinations(DestinationType.TOPIC, getProviderConnection().getJNDIInitialContext().list("topic"));
            } catch (Exception e) {
                // Rest the the Queue List.
                setDestinationList(null);

                throw e;
            }
        }

        return getDestinationList();
    }

    private void addDestinations(DestinationType destinationType, NamingEnumeration<NameClassPair> jndiList) throws NamingException {
        while (jndiList.hasMore()) {
            final String jndiName = jndiList.next().getName();

            // TODO: Identify Queues and add them to the Queue list.
            final Destination destination = new Destination(getProviderConnection(), destinationType, jndiName);
            getDestinationList().put(destination.getDestinationName(), destination);
        }
    }

    private ProviderConnection getProviderConnection() {
        return providerConnection;
    }

    private void setProviderConnection(ProviderConnection providerConnection) {
        this.providerConnection = providerConnection;
    }

    private Hashtable<String, Destination> getDestinationList() {
        return destinationList;
    }

    private void setDestinationList(Hashtable<String, Destination> destinationList) {
        this.destinationList = destinationList;
    }
}
