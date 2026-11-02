package tools;

import slate4j.IO;

import java.io.IOException;

import static slate4j.IO.resourceToByteArray;

public class ImageToBase64 {

    public static void main(final String... args) throws IOException {
        final byte[] image = resourceToByteArray("/static/img/logo.png");
        System.out.println(IO.encodeBase64(image));
    }

}
