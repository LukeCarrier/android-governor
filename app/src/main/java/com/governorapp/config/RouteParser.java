package com.governorapp.config;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Route parser.
 * <p/>
 * Parses out dynamic components from a path.
 */
public class RouteParser {
    /**
     * Used to extract dynamic components of a URL.
     * <p/>
     * /messaging/threads/{integer:personId}
     */
    private final static Pattern pattern = Pattern.compile("([a-z]+:[a-zA-Z]+)");

    /**
     * Map of type aliases to regular expression fragments.
     */
    private final static Map<String, String> typeRegexMap;

    /**
     * Map of type aliases to Java type classes.
     */
    private final static Map<String, Class<?>> typeTypeMap;

    /**
     * Class initializer.
     */
    static {
        Map<String, String> tmpTypeRegexMap = new HashMap<String, String>();
        Map<String, Class<?>> tmpTypeTypeMap = new HashMap<String, Class<?>>();


        typeRegexMap = tmpTypeRegexMap;
        typeTypeMap = tmpTypeTypeMap;
    }

    /**
     * The route's original path.
     */
    private final String path;
    /**
     * The matcher.
     */
    private final Matcher matcher;

    /**
     * Constructor.
     *
     * @param path The route's path to test.
     */
    public RouteParser(String path) {
        this.path = path;
        this.matcher = pattern.matcher(path);
    }

    /**
     * Does the route contain dynamic components?
     *
     * @return True if dynamic components are encountered, else false.
     */
    public boolean isDynamic() {
        return this.matcher.find();
    }

    /**
     * The path contains dynamic components, compile it into a regular expression.
     *
     * @return The compiled regular expression.
     */
    public String getPathRegex() {
        String result = this.path;
        String match;
        String[] parts;

        matcher.reset();

        while (matcher.find()) {
            match = matcher.group(1);
            parts = match.split(":");
            result = result.replace(match, "(" + typeRegexMap.get(parts[0]) + ")");
        }

        return result;
    }
}
