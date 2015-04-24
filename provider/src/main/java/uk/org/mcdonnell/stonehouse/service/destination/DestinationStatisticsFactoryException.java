package uk.org.mcdonnell.stonehouse.service.destination;

@SuppressWarnings("serial")
public class DestinationStatisticsFactoryException extends Exception {
    @SuppressWarnings("unused")
    private DestinationStatisticsFactoryException() {}

    // Constructor that accepts a message
    public DestinationStatisticsFactoryException(String vendor)
    {
        super((String.format("The vendor \"%s\" is unsupported.", vendor)));
    }
}
