package me.zdany.jchatapi;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    public enum Type {

        INFO(System.out), WARN(System.err), ERROR(System.err);

        private final PrintStream stream;

        Type(PrintStream stream) {
            this.stream = stream;
        }
    }

    public static void log(Type type, String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy-hh:mm:ss");
        type.stream.println("[" + formatter.format(LocalDateTime.now()) + "](" + type + "): " + message);
    }

    public static void info(String message) {
        log(Type.INFO, message);
    }

    public static void warn(String message) {
        log(Type.WARN, message);
    }

    public static void error(String message) {
        log(Type.ERROR, message);
    }
}
