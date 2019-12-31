package slate4j.tools;

import org.apache.maven.plugin.logging.Log;

import java.util.function.Consumer;

public interface Logger {

    void info(String message);
    void warn(String message);

    static Logger newLogger(final Log log) {
        return newLogger(log::info, log::warn);
    }
    static Logger newLogger(final Consumer<String> info, final Consumer<String> warn) {
        return new Logger() {
            public void info(String message) {
                info.accept(message);
            }
            public void warn(String message) {
                warn.accept(message);
            }
        };
    }

}
