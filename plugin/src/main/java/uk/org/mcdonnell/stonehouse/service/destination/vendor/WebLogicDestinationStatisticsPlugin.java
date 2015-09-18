package uk.org.mcdonnell.stonehouse.service.destination.vendor;

import java.lang.reflect.InvocationTargetException;

import javax.jms.JMSException;
import javax.naming.NamingException;

import uk.org.mcdonnell.common.Reflect;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;
import uk.org.mcdonnell.stonehouse.api.destination.Destination;
import uk.org.mcdonnell.stonehouse.api.destination.Destinations.DestinationType;
import uk.org.mcdonnell.stonehouse.api.destination.statistics.DestinationStatistics;

public class WebLogicDestinationStatisticsPlugin extends Destination implements DestinationStatistics {

    Object jmsJMSDestinationRuntimeMBeanObject;

    private WebLogicDestinationStatisticsPlugin() throws NamingException, JMSException {
        super(null, null, null);
    };

    public WebLogicDestinationStatisticsPlugin(final ProviderConnectionFactory providerConnection, final DestinationType destinationType, final String queueName) throws NamingException, JMSException {
        super(providerConnection, destinationType, queueName);
    }

    @Override
    public long getPending() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, NamingException {
        final String messagePendingCountMethodName = "getMessagesPendingCount";

        // Fetch Message count.
        final Reflect methodReflection = new Reflect(getWebLogicDestination(), messagePendingCountMethodName);
        final long messagePendingCount = (Long) methodReflection.executeMethod();

        return messagePendingCount;
    }

    @Override
    public long getCurrent() throws NamingException, JMSException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final String messageCurrentCountMethodName = "getMessagesCurrentCount";

        // Fetch Message count.
        final Reflect methodReflection = new Reflect(getWebLogicDestination(), messageCurrentCountMethodName);
        final long messageCurrentCount = (Long) methodReflection.executeMethod();

        return messageCurrentCount;
    }

    @Override
    public long getReceived() throws NamingException, JMSException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final String messageReceivedCountMethodName = "getMessagesReceivedCount";

        // Fetch Message count.
        final Reflect methodReflection = new Reflect(getWebLogicDestination(), messageReceivedCountMethodName);
        final long messageReceivedCount = (Long) methodReflection.executeMethod();

        return messageReceivedCount;
    }

    private Object getWebLogicDestination() throws ClassNotFoundException, NamingException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (jmsJMSDestinationRuntimeMBeanObject == null) {
            final String jmsHelperClassName = "weblogic.jms.extensions.JMSRuntimeHelper";
            final String jmsDestinationMethodName = "getJMSDestinationRuntimeMBean";

            // Instantiate JMS helper object.
            final Class<?> contextClass = Class.forName("javax.naming.Context");
            // Class<?> sessionClass = Class.forName("javax.jms.Session");
            final Class<?> jmsDestinationClass = Class.forName("javax.jms.Destination");
            // Class<?>[] parameters = { contextClass, String.class, String.class };
            final Class<?>[] parameters = { contextClass, jmsDestinationClass };
            final Reflect jmsHelperReflection = new Reflect(jmsHelperClassName, jmsDestinationMethodName, parameters);

            // Instantiate Destination object.
            final Object[] paramaters = new Object[] { getProviderConnection().getJNDIInitialContext(), getDestination() };
            jmsJMSDestinationRuntimeMBeanObject = jmsHelperReflection.executeMethod(paramaters);
        }

        return jmsJMSDestinationRuntimeMBeanObject;
    }
}