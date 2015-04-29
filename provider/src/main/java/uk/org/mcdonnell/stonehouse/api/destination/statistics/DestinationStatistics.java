package uk.org.mcdonnell.stonehouse.api.destination.statistics;

import java.lang.reflect.InvocationTargetException;

import javax.jms.JMSException;
import javax.naming.NamingException;

public interface DestinationStatistics {

    long getPending() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, NamingException, JMSException, DestinationStatisticsFactoryUnsupportedException;

    long getCurrent() throws NamingException, JMSException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, DestinationStatisticsFactoryUnsupportedException;

    long getReceived() throws NamingException, JMSException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, DestinationStatisticsFactoryUnsupportedException;
}
