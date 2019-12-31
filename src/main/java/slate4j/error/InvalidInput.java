package slate4j.error;

public final class InvalidInput extends Exception {
    public InvalidInput(final String message) {
        super(message);
    }
    public InvalidInput(final Exception e) {
        super(e);
    }
}
