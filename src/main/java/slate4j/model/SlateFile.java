package slate4j.model;

import slate4j.error.InvalidInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import static slate4j.model.SlateHeader.toSlateHeader;

public final class SlateFile {
    public final File file;
    public final SlateHeader header;
    public final String content;

    private SlateFile(final File file, final SlateHeader header, final String content) {
        this.file = file;
        this.header = header;
        this.content = content;
    }

    private static final String slate_content_separator = "---";
    public static SlateFile toSlateFile(final File file) throws IOException, InvalidInput {
        try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            if (!slate_content_separator.equals(reader.readLine())) throw new InvalidInput("File does not have proper header");

            final SlateHeader header = toSlateHeader(readUntil(reader, slate_content_separator));
            final StringBuilder content = new StringBuilder();
            content.append(readUntil(reader, null)).append("\n");

            for (final String include : header.includes) {
                content.append(readIncludeFile(file, include));
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
