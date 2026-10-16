package slate4j;

import bobthebuildtool.pojos.buildfile.Project;
import bobthebuildtool.pojos.error.VersionTooOld;
import jcli.errors.InvalidCommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static bobthebuildtool.services.Functions.isNullOrEmpty;
import static bobthebuildtool.services.Update.requireBobVersion;
import static java.nio.file.Files.exists;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static jcli.CliParserBuilder.newCliParser;

public enum BobPlugin {;

    public static void installPlugin(final Project project) throws VersionTooOld {
        requireBobVersion("7");
        project.addCommand("slate4j", "Generates documentation in slatedocs format", BobPlugin::generateSlateDocs);
    }

    private static int generateSlateDocs(final Project project, final Map<String, String> env, final String[] args)
            throws InvalidCommandLine, IOException {
        final CliDocs arguments = newCliParser(CliDocs::new).parse(args);

        final Path indexFile = project.parentDir.resolve(arguments.indexFile);
        if (!exists(indexFile)) return 0;

        final Path logoFile = isNullOrEmpty(arguments.logoFile) ? null : project.parentDir.resolve(arguments.logoFile);
        final Path outputPath = project.getBuildTarget().resolve(arguments.outputFile);

        final String html = MavenDocs.compileSlateDocument(
                indexFile.toFile(), logoFile == null ? null : logoFile.toFile());
        outputPath.getParent().toFile().mkdirs();
        Files.writeString(outputPath, html, CREATE, TRUNCATE_EXISTING);

        return 0;
    }

}
