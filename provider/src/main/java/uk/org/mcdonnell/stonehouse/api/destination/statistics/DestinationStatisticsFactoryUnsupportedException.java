package uk.org.mcdonnell.stonehouse.api.destination.statistics;

@SuppressWarnings("serial")
public class DestinationStatisticsFactoryUnsupportedException extends Exception {
    // Constructor that accepts a message
    public DestinationStatisticsFactoryUnsupportedException(final String vendor)
    {
        super(String.format("The vendor \"%s\" is unsupported.", vendor));
    }
}
