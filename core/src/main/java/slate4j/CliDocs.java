package slate4j;

import jcli.annotations.CliOption;

public class CliDocs {

    @CliOption(name = 'i', longName = "index-file", defaultValue = "src/main/docs/index.html.md")
    public String indexFile;
    @CliOption(name = 'l', longName = "logo-file")
    public String logoFile;
    @CliOption(name = 'o', longName = "output-file", defaultValue = "classes/docs/index.html")
    public String outputFile;

}
