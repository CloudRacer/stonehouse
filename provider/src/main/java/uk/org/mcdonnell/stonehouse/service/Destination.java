package uk.org.mcdonnell.stonehouse.service;

public class Destination {

    private String queueName = null;

    @SuppressWarnings("unused")
    private Destination() {

    }

    public Destination(String queueName) {
        this.setQueueName(queueName);
    }

    public String getQueueName() {
        return queueName;
    }

    private void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
