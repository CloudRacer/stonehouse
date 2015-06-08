package uk.org.mcdonnell.stonehouse.api.destination.statistics;

import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.naming.NamingException;

import uk.org.mcdonnell.common.generic.Reflect;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;
import uk.org.mcdonnell.stonehouse.api.destination.Destinations.DestinationType;

public abstract class DestinationStatisticsFactory implements DestinationStatistics {

    private static enum VENDORS {
        WEBLOGIC,
        UNSUPPORTED
    };

    private ProviderConnectionFactory providerConnection = null;
    private DestinationType destinationType;
    private String destinationName;

    private DestinationStatistics vendorDestinationStatistics;

    public DestinationStatisticsFactory(final ProviderConnectionFactory providerConnection, final DestinationType destinationType, final String destinationName) throws NamingException, JMSException {
        setProviderConnection(providerConnection);
        setDestinationType(destinationType);
        setDestinationName(destinationName);
    }

    public ProviderConnectionFactory getProviderConnection() {
        return providerConnection;
    }

    private void setProviderConnection(final ProviderConnectionFactory providerConnection) {
        this.providerConnection = providerConnection;
    }

    @Override
    public long getPending() throws NamingException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, JMSException, DestinationStatisticsFactoryUnsupportedException {
        Long pending;

        if (getSupportedVendor() == VENDORS.UNSUPPORTED) {
            pending = getTotalNumberOfPendingMessages();
        } else {
            pending = getVendorDestinationStatistics().getPending();
        }

        return pending;
    }

    @Override
    public long getCurrent() throws NamingException, JMSException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, DestinationStatisticsFactoryUnsupportedException {
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
    public long getReceived() throws NamingException, JMSException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, DestinationStatisticsFactoryUnsupportedException {
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
            final QueueBrowser queueBrowser = getProviderConnection().getQueueBrowser(getDestinationName());

            @SuppressWarnings("unchecked")
            final Enumeration<Message> enumeration = queueBrowser.getEnumeration();
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

    private void setDestinationType(final DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    private String getDestinationName() {
        return destinationName;
    }

    private void setDestinationName(final String queueName) {
        destinationName = queueName;
    }

    private DestinationStatistics getVendorDestinationStatistics() throws NamingException, JMSException, DestinationStatisticsFactoryUnsupportedException, InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        if (vendorDestinationStatistics == null) {
            // TODO: instantiate using the reflection functionality in the Reflect class of the utility project.
            // vendorDestinationStatistics = new WebLogicDestinationStatisticsPlugin(getProviderConnection(), getDestinationType(), getDestinationName());
            final String classQualifiedNamePropertyName = "class.qualified.name";
            final String classQualifiedName = getProviderConnection().getJNDIInitialContext().getEnvironment().get(classQualifiedNamePropertyName).toString();

            final Reflect reflect = new Reflect(classQualifiedName, new Object[] { getProviderConnection(), getDestinationType(), getDestinationName() });

            vendorDestinationStatistics = (DestinationStatistics) reflect.getObject();
        }

        return vendorDestinationStatistics;
    }
}
