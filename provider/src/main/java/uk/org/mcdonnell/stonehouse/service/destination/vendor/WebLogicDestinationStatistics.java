package uk.org.mcdonnell.stonehouse.service.destination.vendor;

import java.lang.reflect.InvocationTargetException;

import javax.jms.JMSException;
import javax.naming.NamingException;

import uk.org.mcdonnell.common.generic.Reflect;
import uk.org.mcdonnell.stonehouse.service.connection.ProviderConnection;
import uk.org.mcdonnell.stonehouse.service.destination.Destination;
import uk.org.mcdonnell.stonehouse.service.destination.DestinationStatistics;
import uk.org.mcdonnell.stonehouse.service.destination.Destinations.DestinationType;

public class WebLogicDestinationStatistics extends Destination implements DestinationStatistics {

    Object jmsJMSDestinationRuntimeMBeanObject;

    private WebLogicDestinationStatistics() throws NamingException, JMSException {
        super(null, null, null);
    };

    public WebLogicDestinationStatistics(ProviderConnection providerConnection, DestinationType destinationType, String queueName) throws NamingException, JMSException {
        super(providerConnection, destinationType, queueName);
    }

    @Override
    public long getPending() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, NamingException {
        final String messagePendingCountMethodName = "getMessagesPendingCount";

        // Fetch Message count.
        Reflect methodReflection = new Reflect(getWebLogicDestination(), messagePendingCountMethodName);
        long messagePendingCount = (long) methodReflection.executeMethod();

        return messagePendingCount;
    }

    @Override
    public long getCurrent() throws NamingException, JMSException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final String messageCurrentCountMethodName = "getMessagesCurrentCount";

        // Fetch Message count.
        Reflect methodReflection = new Reflect(getWebLogicDestination(), messageCurrentCountMethodName);
        long messageCurrentCount = (long) methodReflection.executeMethod();

        return messageCurrentCount;
    }

    @Override
    public long getReceived() throws NamingException, JMSException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final String messageReceivedCountMethodName = "getMessagesReceivedCount";

        // Fetch Message count.
        Reflect methodReflection = new Reflect(getWebLogicDestination(), messageReceivedCountMethodName);
        long messageReceivedCount = (long) methodReflection.executeMethod();

        return messageReceivedCount;
    }

    private Object getWebLogicDestination() throws ClassNotFoundException, NamingException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (jmsJMSDestinationRuntimeMBeanObject == null) {
            final String jmsHelperClassName = "weblogic.jms.extensions.JMSRuntimeHelper";
            final String jmsDestinationMethodName = "getJMSDestinationRuntimeMBean";

            // Instantiate JMS helper object.
            Class<?> contextClass = Class.forName("javax.naming.Context");
            // Class<?> sessionClass = Class.forName("javax.jms.Session");
            Class<?> jmsDestinationClass = Class.forName("javax.jms.Destination");
            // Class<?>[] parameters = { contextClass, String.class, String.class };
            Class<?>[] parameters = { contextClass, jmsDestinationClass };
            Reflect jmsHelperReflection = new Reflect(jmsHelperClassName, jmsDestinationMethodName, parameters);

            // Instantiate Destination object.
            Object[] paramaters = new Object[] { getProviderConnection().getJNDIInitialContext(), getDestination() };
            jmsJMSDestinationRuntimeMBeanObject = jmsHelperReflection.executeMethod(paramaters);
        }

        return jmsJMSDestinationRuntimeMBeanObject;
    }
}