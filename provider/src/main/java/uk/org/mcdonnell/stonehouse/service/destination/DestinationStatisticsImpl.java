package uk.org.mcdonnell.stonehouse.service.destination;

import java.lang.reflect.InvocationTargetException;

import javax.naming.NamingException;

public abstract class DestinationStatisticsImpl implements DestinationStatistics {

    private long pending;

    @SuppressWarnings("unused")
    private DestinationStatisticsImpl() {}

    public DestinationStatisticsImpl(long pending) {
        setPending(pending);
    }

    public long getPending() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, NamingException {
        return pending;
    }

    public void setPending(long pending) {
        this.pending = pending;
    }
}
