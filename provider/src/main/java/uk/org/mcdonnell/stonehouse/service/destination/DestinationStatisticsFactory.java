package uk.org.mcdonnell.stonehouse.service.destination;

import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.naming.NamingException;

import uk.org.mcdonnell.stonehouse.service.connection.ProviderConnection;
import uk.org.mcdonnell.stonehouse.service.destination.Destinations.DestinationType;
import uk.org.mcdonnell.stonehouse.service.destination.vendor.WebLogicDestinationStatisticsPlugin;

public abstract class DestinationStatisticsFactory implements DestinationStatistics {

    private static enum VENDORS {
        WEBLOGIC,
        UNSUPPORTED
    };

    private ProviderConnection providerConnection = null;
    private DestinationType destinationType;
    private String destinationName;

    private DestinationStatistics vendorDestinationStatistics;

    @SuppressWarnings("unused")
    private DestinationStatisticsFactory() {}

    public DestinationStatisticsFactory(ProviderConnection providerConnection, DestinationType destinationType, String destinationName) throws NamingException, JMSException {
        setProviderConnection(providerConnection);
        setDestinationType(destinationType);
        setDestinationName(destinationName);
    }

    public ProviderConnection getProviderConnection() {
        return providerConnection;
    }

    private void setProviderConnection(ProviderConnection providerConnection) {
        this.providerConnection = providerConnection;
    }

    @Override
    public long getPending() throws NamingException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, JMSException, DestinationStatisticsFactoryException {
        Long pending;

        if (getSupportedVendor() == VENDORS.UNSUPPORTED) {
            pending = getTotalNumberOfPendingMessages();
        } else {
            pending = getVendorDestinationStatistics().getPending();
        }

        return pending;
    }

    @Override
    public long getCurrent() throws NamingException, JMSException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, DestinationStatisticsFactoryException {
        Long current;

        if (getSupportedVendor() == VENDORS.UNSUPPORTED) {
            // Convert to a "trace" entry in a log.
            // System.out.println("JMS Message Provider vendor identified as WebLogic.");

            current = (long) 0;
        } else {
            current = getVendorDestinationStatistics().getCurrent();
        }

        return current;
    }

    @Override
    public long getReceived() throws NamingException, JMSException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, DestinationStatisticsFactoryException {
        Long received;

        if (getSupportedVendor() == VENDORS.UNSUPPORTED) {
            // Convert to a "trace" entry in a log.
            // System.out.println("JMS Message Provider vendor identified as WebLogic.");

            received = (long) 0;
        } else {
            received = getVendorDestinationStatistics().getReceived();
        }

        return received;
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

    private VENDORS getSupportedVendor() throws NamingException {
        VENDORS supportedVendor;

        if (getVendor().startsWith("weblogic")) {
            supportedVendor = VENDORS.WEBLOGIC;
        } else {
            supportedVendor = VENDORS.UNSUPPORTED;
        }

        // TODO: Convert to a "trace" entry in a log.
        // System.out.println("JMS Message Provider vendor identified as WebLogic.");
        return supportedVendor;
    }

    private String getVendor() throws NamingException {
        return getProviderConnection().getJNDIInitialContext().getEnvironment().get("java.naming.factory.initial").toString().toLowerCase();
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

    private DestinationStatistics getVendorDestinationStatistics() throws NamingException, JMSException, DestinationStatisticsFactoryException {
        if (vendorDestinationStatistics == null) {
            switch (getSupportedVendor()) {
            case WEBLOGIC:
                vendorDestinationStatistics = new WebLogicDestinationStatisticsPlugin(getProviderConnection(), getDestinationType(), getDestinationName());
                break;
            case UNSUPPORTED:
                throw new DestinationStatisticsFactoryException(getVendor());
            }
        }
        return vendorDestinationStatistics;
    }
}
