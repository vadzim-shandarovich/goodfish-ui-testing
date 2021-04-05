package org.example.settings;

/**
 * Keeps base project URI and paths to site sections
 */
public final class ProjectURI {
    public static final String BASE = "https://goodfish.by";
    public static final String DEFAULT_PATH = "/";
    public static final String CART_PATH = "/cart/";
    public static final String CATALOG_PATH = "/catalog/";

    private ProjectURI() {
        throw new IllegalStateException("Utility class");
    }
}
