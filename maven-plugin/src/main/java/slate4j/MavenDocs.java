package slate4j;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Mojo(defaultPhase = org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_RESOURCES, name = "compile")
public final class MavenDocs extends AbstractMojo {

    @Parameter(defaultValue = "true")
    public boolean enabled;

    @Parameter(defaultValue = "src/main/docs/index.html.md")
    public File indexFile;
    @Parameter
    public File logoFile;
    @Parameter(defaultValue = "target/classes/docs/index.html")
    public File outputFile;

    @Override
    public void execute() throws MojoFailureException {
        if (!enabled) return;

        try {
            final String html = SlateCompiler.compileSlateDocument(indexFile, logoFile);
            outputFile.getParentFile().mkdirs();
            Files.writeString(outputFile.toPath(), html, CREATE, TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new MojoFailureException(e.getMessage(), e);
        }
    }
}