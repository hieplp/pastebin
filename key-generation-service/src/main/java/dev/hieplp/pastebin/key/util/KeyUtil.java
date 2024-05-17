package dev.hieplp.pastebin.key.util;

public class KeyUtil {
    /**
     * Alphanumeric characters
     */
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * Key length
     */
    private static final int LENGTH = 8;

    public static String generateKey() {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            key.append(CHARS.charAt((int) (Math.random() * CHARS.length())));
        }
        return key.toString();
    }
}
