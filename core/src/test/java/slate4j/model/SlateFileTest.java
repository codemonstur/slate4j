package slate4j.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests for {@link SlateFile}. */
class SlateFileTest {

    @Test
    void parsesHeaderAndContent(@TempDir final Path dir) throws IOException {
        final var index = dir.resolve("index.html.md");
        Files.writeString(index, """
            ---
            title: Test API
            language_tabs:
            - shell

            includes:
            - example
            ---
            # Introduction

            Hello world.
            """);
        final var includesDir = dir.resolve("includes");
        Files.createDirectories(includesDir);
        Files.writeString(includesDir.resolve("_example.md"), "## Included");

        final var slateFile = SlateFile.toSlateFile(index.toFile());

        assertEquals("Test API", slateFile.header.title);
        assertEquals(java.util.List.of("shell"), slateFile.header.languages);
        assertTrue(slateFile.content.contains("# Introduction"));
        assertTrue(slateFile.content.contains("Hello world."));
        assertTrue(slateFile.content.contains("## Included"));
        assertNotNull(slateFile.file);
    }

    @Test
    void rejectsFileWithoutHeaderSeparator(@TempDir final Path dir) throws IOException {
        final var index = dir.resolve("index.html.md");
        Files.writeString(index, "title: No separator\n");

        assertThrows(IOException.class, () -> SlateFile.toSlateFile(index.toFile()));
    }
}