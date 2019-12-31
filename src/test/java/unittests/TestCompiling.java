package unittests;

import org.junit.Test;
import slate4j.MavenDocs;
import slate4j.error.InvalidInput;
import slate4j.tools.IO;

import java.io.File;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

public class TestCompiling {

    @Test
    public void compileDefaultSlateSource() throws IOException, InvalidInput {
        final String expected = IO.toString(TestCompiling.class.getResourceAsStream("/output/index2.html"), UTF_8);
        final File defaultSource = new File("src/test/resources/source/index.html.md");

        final String actual = MavenDocs.compileSlateDocument(defaultSource);
        assertEquals("Invalid generated HTML", expected, actual);
    }


}
