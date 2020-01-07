package slate4j;

import com.google.gson.Gson;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;
import slate4j.error.InvalidInput;
import slate4j.model.SlateFile;
import slate4j.model.SlateHeading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_RESOURCES;
import static slate4j.model.Resource.newResource;
import static slate4j.tools.App.buildMavenTask;

@Mojo( defaultPhase = GENERATE_RESOURCES, name = "compile" )
public final class MavenDocs extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        buildMavenTask(this, log -> {
            compileSlateDocument(new File("src/main/docs/index.html.md"));
        });
    }

    public static String compileSlateDocument(final File file) throws IOException, InvalidInput {
        final Gson gson = new Gson();
        final SlateFile slateFile = SlateFile.toSlateFile(file);

        final Document wrapper = Jsoup.parse(newResource("/static/wrapper.html").toString());
        wrapper.selectFirst("title").text(slateFile.header.title);
        wrapper.selectFirst("body").attr("data-languages", gson.toJson(slateFile.header.languages));
        for (final var langSelector : wrapper.select("div.lang-selector")) {
            for (final var lang : slateFile.header.languages) {
                langSelector.appendChild(
                    wrapper.createElement("a")
                        .attr("href", "#")
                        .attr("data-language-name", lang)
                        .text(lang));
            }
        }

        final Element body = Jsoup.parseBodyFragment(markdownToHtml(slateFile.content)).body();
        insertTableOfContents(wrapper, body);

        final Element element = wrapper.selectFirst("div.content");
        for (final var child : body.children()) {
            element.appendChild(child);
        }

        return wrapper.html();
    }

    private static void insertTableOfContents(final Document wrapper, final Element body) {
        final List<SlateHeading> toc = discoverH1AndH2(body);

        final Element tocElement = wrapper.selectFirst("#toc");
        for (final SlateHeading heading : toc) {
            final Element topLi = wrapper.createElement("li").appendChild(
                wrapper.createElement("a")
                    .attr("href", "#" + heading.id)
                    .attr("class", "toc-h1 toc-link")
                    .attr("data-title", heading.name)
                    .text(heading.name));
            if (!heading.subHeadings.isEmpty()) {
                final Element ul = wrapper.createElement("ul").attr("class", "toc-list-h2");
                for (final SlateHeading subHeading : heading.subHeadings) {
                    ul.appendChild(
                        wrapper.createElement("li").appendChild(
                            wrapper.createElement("a")
                                .attr("href", "#" + subHeading.id)
                                .attr("class", "toc-h2 toc-link")
                                .attr("data-title", subHeading.name)
                                .text(subHeading.name)));
                }
                topLi.appendChild(ul);
            }
            tocElement.appendChild(topLi);
        }
    }

    private static List<SlateHeading> discoverH1AndH2(Element body) {
        final List<SlateHeading> toc = new ArrayList<>();

        body.traverse(new NodeVisitor() {
            private int index = 0;
            public void head(Node node, int depth) {
                if (node instanceof Element) {
                    final Element element = (Element) node;
                    final String name = element.tagName();
                    if (name.equals("h1") || name.equals("h2")) {
                        final var sh = new SlateHeading(toLevel(name), toUniqueId(index++, element), element.text(), new ArrayList<>());
                        element.attr("id", sh.id);
                        if (sh.level == 1) toc.add(sh);
                        else toc.get(toc.size()-1).subHeadings.add(sh);
                    }
                }
            }

            private int toLevel(final String name) {
                return Integer.parseInt(name.substring(1, 2));
            }

            private String toUniqueId(final int index, final Element element) {
                return element.text().replaceAll(" ", "-").toLowerCase();
            }

            public void tail(Node node, int depth) {}
        });

        return toc;
    }

    public static String markdownToHtml(final String markdown) {
        final MutableDataSet options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, List.of(TablesExtension.create()));
        final Parser parser = Parser.builder(options).build();
        final HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        return renderer.render(parser.parse(markdown));
    }

}
