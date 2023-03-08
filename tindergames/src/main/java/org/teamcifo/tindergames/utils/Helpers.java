package org.teamcifo.tindergames.utils;

import java.util.UUID;

/**
 * Helpers: static class with public methods commonly used in core classes
 */
public class Helpers {
    public static String generateUUID() {
        UUID newUUID = UUID.randomUUID();
        return newUUID.toString();
    }
}
