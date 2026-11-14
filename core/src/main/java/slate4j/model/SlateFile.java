package slate4j.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import static slate4j.model.SlateHeader.toSlateHeader;

/**
 * The {@code SlateFile} class.
 */
public final class SlateFile {
    /**
     * The {@code file} field.
     */
    public final File file;
    /**
     * The {@code header} field.
     */
    public final SlateHeader header;
    /**
     * The {@code content} field.
     */
    public final String content;

    private SlateFile(final File file, final SlateHeader header, final String content) {
        this.file = file;
        this.header = header;
        this.content = content;
    }

    private static final String slate_content_separator = "---";
    /**
     * Returns the result of {@code toSlateFile}.
     *
     * @param file the file
     * @return the result
     * @throws IOException if the operation fails
     */
    public static SlateFile toSlateFile(final File file) throws IOException {
        try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            if (!slate_content_separator.equals(reader.readLine()))
                throw new IOException("File does not have proper header");

            final SlateHeader header = toSlateHeader(readUntil(reader, slate_content_separator));
            final StringBuilder content = new StringBuilder();
            content.append(readUntil(reader, null)).append("\n");

            if (header.includes != null) {
                for (final String include : header.includes) {
                    content.append(readIncludeFile(file, include)).append("\n");
                }
            }

            return new SlateFile(file, header, content.toString());
        }
    }

    private static String readIncludeFile(final File file, final String include) throws IOException {
        return Files.readString(new File(file.getParentFile(), "includes/_"+include+".md").toPath());
    }

    private static String readUntil(final BufferedReader reader, final String end) throws IOException {
        final StringBuilder section = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (end != null && end.equals(line)) break;
            section.append(line).append("\n");
        }
        return section.toString();
    }

}
