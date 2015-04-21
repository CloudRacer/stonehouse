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

    private WebLogicDestinationStatistics() throws NamingException, JMSException {
        super(null, null, null);
    };

    public WebLogicDestinationStatistics(ProviderConnection providerConnection, DestinationType destinationType, String queueName) throws NamingException, JMSException {
        super(providerConnection, destinationType, queueName);
    }

    @Override
    public long getPending() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, NamingException {
        final String JMS_HELPER_CLASS_NAME = "weblogic.jms.extensions.JMSRuntimeHelper";
        final String JMS_DESTINATION_METHOD_NAME = "getJMSDestinationRuntimeMBean";
        final String JMS_MESSAGE_CURRENT_COUNT_METHOD_NAME = "getMessagesCurrentCount";

        // Instantiate JMS helper object.
        Class<?> contextClass = Class.forName("javax.naming.Context");
        // Class<?> sessionClass = Class.forName("javax.jms.Session");
        Class<?> jmsDestinationClass = Class.forName("javax.jms.Destination");
        // Class<?>[] parameters = { contextClass, String.class, String.class };
        Class<?>[] parameters = { contextClass, jmsDestinationClass };
        Reflect jmsHelperReflection = new Reflect(JMS_HELPER_CLASS_NAME, JMS_DESTINATION_METHOD_NAME, parameters);

        // Instantiate Destination object.
        Object[] paramaters = new Object[] { getProviderConnection().getJNDIInitialContext(), getDestination() };
        Object jmsJMSDestinationRuntimeMBeanObject = jmsHelperReflection.executeMethod(paramaters);

        // Fetch Message count.
        Reflect jmsJMSDestinationRuntimeMBeanObjectReflection = new Reflect(jmsJMSDestinationRuntimeMBeanObject, JMS_MESSAGE_CURRENT_COUNT_METHOD_NAME);
        long messageCurrentCount = (long) jmsJMSDestinationRuntimeMBeanObjectReflection.executeMethod();

        setPending(messageCurrentCount);

        return messageCurrentCount;
    }
}
