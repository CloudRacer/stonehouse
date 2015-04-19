package uk.org.mcdonnell.stonehouse.service.destination.vendor;

import java.lang.reflect.InvocationTargetException;

import javax.jms.JMSException;
import javax.naming.NamingException;

import uk.org.mcdonnell.common.generic.Reflect;
import uk.org.mcdonnell.stonehouse.service.connection.ProviderConnection;
import uk.org.mcdonnell.stonehouse.service.destination.Destination;
import uk.org.mcdonnell.stonehouse.service.destination.Destinations.DestinationType;

public class WebLogicDestinationStatistics extends Destination {

    private WebLogicDestinationStatistics() throws NamingException, JMSException {
        super(null, null, null);
    };

    public WebLogicDestinationStatistics(ProviderConnection providerConnection, DestinationType destinationType, String queueName) throws NamingException, JMSException {
        super(providerConnection, destinationType, queueName);
        // TODO Auto-generated constructor stub
    }

    @Override
    public long getPending() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, NamingException {
        final String JMS_HELPER_CLASS_NAME = "weblogic.jms.extensions.JMSRuntimeHelper";
        final String JMS_DESTINATION_METHOD_NAME = "getJMSDestinationRuntimeMBean";
        final String JMS_MESSAGE_CURRENT_COUNT_METHOD_NAME = "getMessagesCurrentCount";

        // Instantiate JMS helper object.
        Reflect jmsHelperReflection = new Reflect(JMS_HELPER_CLASS_NAME);
        Object[] paramaters = new Object[] { getProviderConnection().getJNDIInitialContext(), getDestinationName() };

        // Instantiate Destination object.
        Object jmsJMSDestinationRuntimeMBeanObject = jmsHelperReflection.executeMethod(JMS_DESTINATION_METHOD_NAME, paramaters);

        // Fetch Message count.
        Reflect jmsJMSDestinationRuntimeMBeanObjectReflection = new Reflect(jmsJMSDestinationRuntimeMBeanObject);
        long messageCurrentCount = (long) jmsJMSDestinationRuntimeMBeanObjectReflection.executeMethod(JMS_MESSAGE_CURRENT_COUNT_METHOD_NAME);

        super.setPending(messageCurrentCount);

        return super.getPending();
    }
}
