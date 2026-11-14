package slate4j.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code SlateHeader} class.
 */
public final class SlateHeader {
    /**
     * The {@code title} field.
     */
    public final String title;
    /**
     * The {@code languages} field.
     */
    public final List<String> languages;
    /**
     * The {@code tocFooters} field.
     */
    public final List<String> tocFooters;
    /**
     * The {@code includes} field.
     */
    public final List<String> includes;

    /**
     * Creates a new instance.
     *
     * @param title the title
     * @param languages the languages
     * @param tocFooters the toc footers
     * @param includes the includes
     */
    public SlateHeader(final String title, final List<String> languages, final List<String> tocFooters,
                       final List<String> includes) {
        this.title = title;
        this.languages = languages;
        this.tocFooters = tocFooters;
        this.includes = includes;
    }

    private static final String
        title_marker = "title:",
        languages_marker = "language_tabs:",
        footers_marker = "toc_footers:",
        includes_marker = "includes:";

    /**
     * Returns the result of {@code toSlateHeader}.
     *
     * @param input the input
     * @return the result
     * @throws IOException if the operation fails
     */
    public static SlateHeader toSlateHeader(final String input) throws IOException {
        try (final BufferedReader reader = new BufferedReader(new StringReader(input))) {
            String title = "";
            List<String> languages = null;
            List<String> tocFooters = null;
            List<String> includes = null;

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                if (line.startsWith(title_marker)) {
                    title = line.substring(title_marker.length()).trim();
                    continue;
                }
                if (line.startsWith(languages_marker)) {
                    languages = readList(reader);
                    continue;
                }
                if (line.startsWith(footers_marker)) {
                    tocFooters = readList(reader);
                    continue;
                }
                if (line.startsWith(includes_marker)) {
                    includes = readList(reader);
                }
            }

            return new SlateHeader(title, languages, tocFooters, includes);
        }
    }

    private static List<String> readList(final BufferedReader reader) throws IOException {
        final List<String> list = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isBlank()) break;

            list.add(line.trim().substring(1).trim());
        }
        return list;
    }

}
