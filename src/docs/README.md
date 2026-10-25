# slate4j

Compiles [Slate](https://github.com/lord/slate)-style API documentation to a
single self-contained HTML file — without Ruby, Node, or Docker.

The output looks like the classic [Slate](https://github.com/lord/slate) three-column
API docs: sidebar navigation, syntax-highlighted code samples, and a responsive
layout, all inlined into one file you can commit or publish anywhere.

Available as **both** a Maven plugin and a bob plugin.

## Usage as a Maven plugin

```xml
<plugin>
  <groupId>com.github.codemonstur</groupId>
  <artifactId>slate4j</artifactId>
  <version>4</version>
  <executions>
    <execution>
      <goals>
        <goal>compile</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

The goal binds to `generate-resources` and reads
`src/main/docs/index.html.md`, writing `target/classes/docs/index.html`.

### Configuration

| Parameter | Default | Description |
| --- | --- | --- |
| `enabled` | `true` | Master switch. |
| `indexFile` | `src/main/docs/index.html.md` | Markdown source. |
| `logoFile` | — | Optional logo image to inline. |
| `outputFile` | `classes/docs/index.html` | Generated HTML. |

## Usage as a bob plugin

```
plugin codemonstur/slate4j:4
```

```
bob docs [-i index.html.md] [-l logo.png] [-o output.html]
```

(This replaces the older `slate4j-bobplugin`.)

## How it works

1. **flexmark** converts the Markdown to HTML (with the tables extension enabled).
2. **jsoup** post-processes the DOM: applies Slate's structure and CSS classes, and
   inlines images and stylesheets so the result is a single file.

## Known differences from real Slate

- No search icon.
- Syntax highlighting differs from the Ruby/rouge original.
- Images are inlined (base64), which is usually a feature but bloats the file.

## Dependencies

`flexmark-all`, `jsoup`, `gson`, `bobthebuildtool`, plus the Maven plugin API.

## Building

```sh
make build
make test
```

## License

MIT
