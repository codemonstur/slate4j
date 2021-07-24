
[![GitHub Release](https://img.shields.io/github/release/codemonstur/slate4j.svg)](https://github.com/codemonstur/slate4j/releases) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.codemonstur/slate4j/badge.svg)](http://mvnrepository.com/artifact/com.github.codemonstur/slate4j)
[![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://opensource.org/licenses/mit-license.php)

# Slate4j

Produces an HTML file that looks similar and has the same functionality as slate.
The output isn't exactly the same but is close enough to work with.

These are the known changes and issues:
- The search icon isn't there
- The syntax highlighting works with a different library and in a different way
- Images are inlined
- It is not possible to override all the code the way you can in slate

The big benefit of this code is that it is nothing more than a self contained maven plugin.
No need to install ruby or node or mess around with docker trying to get the original slate to work.

## Configuration

Add this to the pom:

```
<plugin>
  <groupId>com.github.codemonstur</groupId>
  <artifactId>slate4j</artifactId>
  <version>0.1.0</version>
  <executions>
    <execution>
      <phase>generate-resources</phase>
      <goals><goal>compile</goal></goals>
    </execution>
  </executions>
</plugin>
```

Slate4j will read slate markdown from `src/main/docs/index.html.md`.
And generate output in `target/classes/docs/index.html`.

