package uk.org.mcdonnell.stonehouse.api.destination.statistics;

@SuppressWarnings("serial")
public class DestinationStatisticsFactoryUnsupportedException extends Exception {
    @SuppressWarnings("unused")
    private DestinationStatisticsFactoryUnsupportedException() {}

    // Constructor that accepts a message
    public DestinationStatisticsFactoryUnsupportedException(String vendor)
    {
        super((String.format("The vendor \"%s\" is unsupported.", vendor)));
    }
}
