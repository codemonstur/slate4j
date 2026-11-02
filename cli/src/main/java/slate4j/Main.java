package slate4j;

import jcli.CliParserBuilder;
import slate4j.model.SlateFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static jcli.CliParserBuilder.newCliParser;

public enum Main {;

    public static void main(final String... args) throws Exception {
        final CliDocs arguments = newCliParser(CliDocs::new)
                .onErrorPrintHelpAndExit()
                .onHelpPrintHelpAndExit()
                .parse(args);

        final File indexFile = new File(arguments.indexFile);
        final File logoFile = (arguments.logoFile == null || arguments.logoFile.isBlank())
                ? null : new File(arguments.logoFile);
        final Path outputPath = Path.of(arguments.outputFile);

        final String html = SlateCompiler.compileSlateDocument(indexFile, logoFile);
        Files.createDirectories(outputPath.getParent());
        Files.writeString(outputPath, html, CREATE, TRUNCATE_EXISTING);
    }

}
