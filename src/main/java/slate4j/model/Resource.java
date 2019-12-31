package slate4j.model;

import slate4j.tools.IO;

import java.io.IOException;

public interface Resource {

    public static Resource newResource(final String resource) throws IOException {
        final String data = IO.toString(Resource.class.getResourceAsStream(resource));
        return new Resource() {
            @Override
            public String toString() {
                return data;
            }
        };
    }

}
