package slate4j;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests for {@link MavenDocs}. */
class MavenDocsTest {

    @Test
    void markdownToHtmlRendersHeadings() {
        final var html = MavenDocs.markdownToHtml("# Title\n\nSome text.\n");

        assertTrue(html.contains("<h1"));
        assertTrue(html.contains("Title"));
        assertTrue(html.contains("Some text."));
    }

    @Test
    void markdownToHtmlRendersTables() {
        final var markdown = "| a | b |\n|---|---|\n| 1 | 2 |\n";
        final var html = MavenDocs.markdownToHtml(markdown);

        assertTrue(html.contains("<table"));
        assertTrue(html.contains("1"));
    }

    @Test
    void compileSlateDocumentProducesHtml(@TempDir final Path dir) throws Exception {
        final var index = dir.resolve("index.html.md");
        Files.writeString(index, """
            ---
            title: Slate Test
            language_tabs:
            - shell

            ---
            # Hello

            World.
            """);

        final var html = MavenDocs.compileSlateDocument(index.toFile(), null);

        assertNotNull(html);
        assertTrue(html.contains("<title>Slate Test</title>") || html.contains("Slate Test"));
        assertTrue(html.contains("Hello"));
        assertTrue(html.contains("World."));
        assertTrue(html.contains("data-languages"));
    }

    @Test
    void compileSlateDocumentUsesLogoFile(@TempDir final Path dir) throws Exception {
        final var index = dir.resolve("index.html.md");
        Files.writeString(index, """
            ---
            title: Logo Test
            ---
            # Hi
            """);
        final var logo = dir.resolve("logo.png");
        Files.write(logo, new byte[] {1, 2, 3});

        final var html = MavenDocs.compileSlateDocument(index.toFile(), logo.toFile());

        assertTrue(html.contains("data:image/png;base64,"));
    }
}