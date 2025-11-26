package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Helper utilities for examples - formatting and display helpers
 */
public class HelperUtils {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    /**
     * Print a section header
     */
    public static void printSection(String title) {
        System.out.println("------------------------------------------------------------------");
        System.out.println(title);
        System.out.println("------------------------------------------------------------------");
        System.out.println();
    }

    /**
     * Print a banner
     */
    public static void printBanner(String title, String subtitle) {
        System.out.println("+------------------------------------------------------------------+");
        System.out.println("|  " + padRight(title, 62) + "  |");
        if (subtitle != null && !subtitle.isEmpty()) {
            System.out.println("|  " + padRight(subtitle, 62) + "  |");
        }
        System.out.println("+------------------------------------------------------------------+");
        System.out.println();
    }

    /**
     * Print a streaming event with timestamp
     */
    public static void printEvent(int eventNumber, String eventType, String details) {
        String timestamp = LocalDateTime.now().format(TIME_FORMATTER);
        System.out.println(String.format("  [%s] Event #%d - %s", timestamp, eventNumber, eventType));
        if (details != null && !details.isEmpty()) {
            System.out.println("        " + details);
        }
    }

    /**
     * Print tick data in a formatted way
     */
    public static void printTick(String symbol, double bid, double ask, long volume) {
        System.out.println(String.format("        %s: Bid=%.5f Ask=%.5f Vol=%d",
            symbol, bid, ask, volume));
    }

    /**
     * Print a box around text
     */
    public static void printBox(String text) {
        int length = text.length();
        String border = "+" + repeat("-", length + 2) + "+";
        System.out.println(border);
        System.out.println("| " + text + " |");
        System.out.println(border);
    }

    /**
     * Print waiting message
     */
    public static void printWaiting(String message) {
        System.out.println();
        System.out.println("  >>> " + message);
        System.out.println();
    }

    /**
     * Print completion message
     */
    public static void printCompletion(String message) {
        System.out.println();
        System.out.println("  [OK] " + message);
        System.out.println();
    }

    /**
     * Print error message
     */
    public static void printError(String message) {
        System.out.println();
        System.out.println("  [ERROR] " + message);
        System.out.println();
    }

    /**
     * Pad string to the right
     */
    private static String padRight(String s, int n) {
        if (s.length() >= n) {
            return s.substring(0, n);
        }
        return s + repeat(" ", n - s.length());
    }

    /**
     * Repeat string n times
     */
    private static String repeat(String s, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
