package uk.org.mcdonnell.stonehouse.service.destination;

public abstract class DestinationStatistics {

    private long pending;

    @SuppressWarnings("unused")
    private DestinationStatistics() {}

    public DestinationStatistics(long pending) {
        setPending(pending);
    }

    public long getPending() {
        return pending;
    }

    protected void setPending(long pending) {
        this.pending = pending;
    };
}
