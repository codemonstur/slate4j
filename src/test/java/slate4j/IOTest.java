package slate4j;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests for the {@link IO} helper. */
class IOTest {

    @Test
    void resourceToStringReadsBundledResource() throws Exception {
        final var content = IO.resourceToString("/static/img/logo.base64.txt");
        assertNotNull(content);
        assertFalse(content.isBlank());
    }

    @Test
    void encodeBase64EncodesBytes() {
        assertEquals("aGVsbG8=", IO.encodeBase64("hello".getBytes()));
    }

    @Test
    void existsFileChecksRegularFile(@TempDir final Path dir) throws Exception {
        final var file = dir.resolve("sample.txt").toFile();
        Files.writeString(dir.resolve("sample.txt"), "data");
        assertTrue(IO.existsFile(file));
        assertFalse(IO.existsFile(null));
        assertFalse(IO.existsFile(dir.toFile()));
    }

    @Test
    void readAsBytesReadsFile(@TempDir final Path dir) throws Exception {
        final var path = dir.resolve("sample.bin");
        final var expected = new byte[] {1, 2, 3, 7};
        Files.write(path, expected);
        assertArrayEquals(expected, IO.readAsBytes(path.toFile()));
    }

    @Test
    void resourceToByteArrayReadsBundledResource() throws Exception {
        final var bytes = IO.resourceToByteArray("/static/img/logo.base64.txt");
        assertNotNull(bytes);
        assertTrue(bytes.length > 0);
    }
}