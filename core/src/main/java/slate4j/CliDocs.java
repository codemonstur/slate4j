package slate4j;

import jcli.annotations.CliOption;

/**
 * The {@code CliDocs} class.
 */
public class CliDocs {

    /**
     * The {@code indexFile} field.
     */
    @CliOption(name = 'i', longName = "index-file", defaultValue = "src/main/docs/index.html.md")
    public String indexFile;
    /**
     * The {@code logoFile} field.
     */
    @CliOption(name = 'l', longName = "logo-file")
    public String logoFile;
    /**
     * The {@code outputFile} field.
     */
    @CliOption(name = 'o', longName = "output-file", defaultValue = "classes/docs/index.html")
    public String outputFile;


    /**
     * Creates a new instance.
     */
    public CliDocs() {
    }
}
