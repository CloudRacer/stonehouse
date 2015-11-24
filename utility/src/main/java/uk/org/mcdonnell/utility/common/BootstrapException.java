package uk.org.mcdonnell.utility.common;

public class BootstrapException extends Exception {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -5650832208193246617L;

    @SuppressWarnings("unused")
    private BootstrapException() {
    }

    public BootstrapException(Exception cause) {
        super(cause);
    }

}
