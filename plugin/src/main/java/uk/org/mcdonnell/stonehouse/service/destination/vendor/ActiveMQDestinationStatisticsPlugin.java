package uk.org.mcdonnell.stonehouse.service.destination.vendor;

import java.lang.reflect.InvocationTargetException;

import javax.jms.JMSException;
import javax.naming.NamingException;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;
import uk.org.mcdonnell.stonehouse.api.destination.Destination;
import uk.org.mcdonnell.stonehouse.api.destination.Destinations.DestinationType;
import uk.org.mcdonnell.stonehouse.api.destination.statistics.DestinationStatistics;
import uk.org.mcdonnell.stonehouse.api.destination.statistics.DestinationStatisticsFactoryUnsupportedException;

public class ActiveMQDestinationStatisticsPlugin extends Destination implements DestinationStatistics {

    public ActiveMQDestinationStatisticsPlugin(ProviderConnectionFactory providerConnection, DestinationType destinationType, String queueName) throws NamingException, JMSException {
        super(providerConnection, destinationType, queueName);
    }

    @Override
    public long getCurrent() throws NamingException, JMSException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, DestinationStatisticsFactoryUnsupportedException {
        // TODO AMQ getCurrent() under construction.
        return 0;
    }

    @Override
    public long getPending() throws NamingException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, JMSException, DestinationStatisticsFactoryUnsupportedException {
        // TODO AMQ getPending() under construction.
        return 0;
    }

    @Override
    public long getReceived() throws NamingException, JMSException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, DestinationStatisticsFactoryUnsupportedException {
        // TODO AMQ getReceived() under construction.
        return 0;
    }
}
