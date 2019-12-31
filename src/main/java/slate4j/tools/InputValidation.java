package slate4j.tools;

public enum InputValidation {;

    public static <T> T orDefault(final T value, final T defaultValue) {
        return (value != null) ? value : defaultValue;
    }

}
