package uk.org.mcdonnell.stonehouse.service.destination;

import java.lang.reflect.InvocationTargetException;

import javax.naming.NamingException;

public interface DestinationStatistics {

    long getPending() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, NamingException;

    void setPending(long pending);
}
