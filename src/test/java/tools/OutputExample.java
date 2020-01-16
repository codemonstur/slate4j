package tools;

import java.io.File;
import java.io.IOException;

import static slate4j.MavenDocs.compileSlateDocument;

public class OutputExample {

    public static void main(final String... args) throws IOException {
        final File defaultSource = new File("src/test/resources/source/index.html.md");

        final String actual = compileSlateDocument(defaultSource, null);
        System.out.println(actual);
    }

}
