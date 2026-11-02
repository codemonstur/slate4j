package slate4j.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** Tests for {@link SlateHeader}. */
class SlateHeaderTest {

    @Test
    void parsesTitleLanguagesFootersAndIncludes() throws IOException {
        final var input = """
            title: Example API
            language_tabs:
            - shell
            - ruby

            toc_footers:
            - <a href="https://example.com">Example</a>

            includes:
            - errors
            """;

        final var header = SlateHeader.toSlateHeader(input);

        assertEquals("Example API", header.title);
        assertEquals(java.util.List.of("shell", "ruby"), header.languages);
        assertEquals(java.util.List.of("<a href=\"https://example.com\">Example</a>"), header.tocFooters);
        assertEquals(java.util.List.of("errors"), header.includes);
    }

    @Test
    void missingListsStayNull() throws IOException {
        final var header = SlateHeader.toSlateHeader("title: Minimal\n");

        assertEquals("Minimal", header.title);
        assertNull(header.languages);
        assertNull(header.tocFooters);
        assertNull(header.includes);
    }

    @Test
    void emptyTitleDefaultsToEmptyString() throws IOException {
        final var header = SlateHeader.toSlateHeader("\n");

        assertEquals("", header.title);
    }
}