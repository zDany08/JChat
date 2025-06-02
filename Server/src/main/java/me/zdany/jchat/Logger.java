package me.zdany.jchat;

import java.io.PrintStream;

public class Logger
{
    public enum Type
    {
        INFO(System.out), WARN(System.err), ERROR(System.err);

        private final PrintStream stream;

        Type(PrintStream stream)
        {
            this.stream = stream;
        }
    }

    public static void log(Type type, String message)
    {
        type.stream.println("[" + type + "]: " + message);
    }

    public static void info(String message)
    {
        log(Type.INFO, message);
    }

    public static void warn(String message)
    {
        log(Type.WARN, message);
    }

    public static void error(String message)
    {
        log(Type.ERROR, message);
    }
}
