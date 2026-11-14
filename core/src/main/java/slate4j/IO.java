package slate4j;

import java.io.*;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * The {@code IO} enum.
 */
public enum IO {;

    /**
     * Returns the result of {@code resourceToString}.
     *
     * @param resource the resource
     * @return the result
     * @throws IOException if the operation fails
     */
    public static String resourceToString(final String resource) throws IOException {
        try (final InputStream in = IO.class.getResourceAsStream(resource)) {
            final StringBuilder out = new StringBuilder();
            byte[] buffer = new byte[1024]; int read;
            while ((read = in.read(buffer)) != -1) {
                out.append(new String(buffer, 0, read, UTF_8));
            }
            return out.toString();
        }
    }

    private static final Base64.Encoder encoder = Base64.getEncoder();
    /**
     * Returns the result of {@code encodeBase64}.
     *
     * @param data the data
     * @return the result
     */
    public static String encodeBase64(final byte[] data) {
        return encoder.encodeToString(data);
    }

    /**
     * Returns the result of {@code existsFile}.
     *
     * @param file the file
     * @return whether the condition holds
     */
    public static boolean existsFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    /**
     * Returns the result of {@code readAsBytes}.
     *
     * @param file the file
     * @return the result
     * @throws IOException if the operation fails
     */
    public static byte[] readAsBytes(final File file) throws IOException {
        try (final InputStream in = new FileInputStream(file)) {
            return streamToBytes(in);
        }
    }
    /**
     * Returns the result of {@code resourceToByteArray}.
     *
     * @param resource the resource
     * @return the result
     * @throws IOException if the operation fails
     */
    public static byte[] resourceToByteArray(final String resource) throws IOException {
        try (final InputStream in = IO.class.getResourceAsStream(resource)) {
            return streamToBytes(in);
        }
    }

    private static byte[] streamToBytes(final InputStream in) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final byte[] buffer = new byte[1024];
        int read; while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        return out.toByteArray();
    }

}
