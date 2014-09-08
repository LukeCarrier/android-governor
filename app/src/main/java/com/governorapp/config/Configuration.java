package com.governorapp.config;

import android.content.SharedPreferences;

import com.governorapp.config.route.AssetRoute;
import com.governorapp.config.route.DynamicMethodRoute;
import com.governorapp.config.route.MethodRoute;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Root configuration node.
 */
public class Configuration {
    /**
     * The enabled state of CORS.
     * <p/>
     * If enabled, CORS will cause a header containing "access-control-allow-origin: *" to be sent
     * with responses.
     */
    protected boolean enableCors;

    /**
     * Server's port number.
     * <p/>
     * The port number must be above 1024, as we won't run as a privileged application.
     */
    protected int port;

    /**
     * Request routing table (dynamic routes).
     */
    private Map<String, Route> dynamicRoutes;

    /**
     * Request routing table (static routes).
     */
    private Map<String, Route> staticRoutes;

    /**
     * Constructor.
     */
    public Configuration() {
        dynamicRoutes = new HashMap<String, Route>();
        staticRoutes = new HashMap<String, Route>();
    }

    /**
     * Add a dynamic route.
     *
     * @param path  The (valid regular expression) path to respond to.
     * @param route The route to respond with.
     */
    public void addDynamicRoute(String path, Route route) {
        this.dynamicRoutes.put(path, route);
    }

    /**
     * Add a static route.
     *
     * @param path  The path to respond to.
     * @param route The route to respond with.
     */
    public void addStaticRoute(String path, Route route) {
        this.staticRoutes.put(path, route);
    }

    /**
     * Get the enabled status of CORS.
     *
     * @return The enabled status of CORS.
     */
    public boolean getEnableCors() {
        return enableCors;
    }

    /**
     * Set the enabled state of CORS.
     *
     * @param enableCors The enabled state of CORS.
     */
    public void setEnableCors(boolean enableCors) {
        this.enableCors = enableCors;
    }

    /**
     * Get the route for a given path.
     *
     * @param path The path we're responding to.
     * @return The corresponding route.
     */
    public Route resolveRoute(String path) throws IOException {
        Route route = this.staticRoutes.get(path);

        if (route != null) {
            return route;
        }

        Iterator<Map.Entry<String, Route>> iterator = this.dynamicRoutes.entrySet().iterator();
        Map.Entry<String, Route> entry;

        while (iterator.hasNext()) {
            entry = iterator.next();
            Pattern pattern = Pattern.compile(entry.getKey());
            Matcher matcher = pattern.matcher(path);

            if (matcher.matches()) {
                return entry.getValue();
            }
        }

        throw new IOException();
    }

    /**
     * Get the server port.
     *
     * @return The server port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the server port.
     *
     * @param port The server port.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Load preferences from the Android SharedPreferences API.
     *
     * @param preferences The preferences object.
     */
    public void loadPreferences(SharedPreferences preferences) {
        setEnableCors(preferences.getBoolean("pref_key_cors", false));
    }

    /**
     * Create a configuration object from an XML document.
     *
     * @param xml The XML Document object to parse values from.
     * @return A populated configuration object.
     * @throws XPathExpressionException When the configuration file is invalid (e.g. missing
     *                                  required nodes).
     */
    public void loadXml(Document xml) throws XPathExpressionException {
        xml.getDocumentElement().normalize();

        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList portNodes = (NodeList) xPath.evaluate("/governor-config/server/port", xml,
                XPathConstants.NODESET);
        setPort(Integer.parseInt(portNodes.item(0).getTextContent()));

        NodeList routes = (NodeList) xPath.evaluate("/governor-config/routes/*[self::asset or self::method]",
                xml, XPathConstants.NODESET);
        for (int i = 0; i < routes.getLength(); i++) {
            Node routeNode = routes.item(i);
            NamedNodeMap routeNodeAttrs = routeNode.getAttributes();
            String routeNodeName = routeNode.getNodeName();

            String routePath = routeNodeAttrs.getNamedItem("path").getNodeValue();
            String routeController;
            String routeMethod;
            String routeVerb;
            String routePathRegex;

            RouteParser routeParser;

            if (routeNodeName.equals("asset")) {
                addStaticRoute(routePath, new AssetRoute(routeNodeAttrs.getNamedItem("file").getNodeValue(),
                        routeNodeAttrs.getNamedItem("mimetype").getNodeValue()));
            } else if (routeNodeName.equals("method")) {
                routeController = routeNodeAttrs.getNamedItem("controller").getNodeValue();
                routeMethod = routeNodeAttrs.getNamedItem("method").getNodeValue();
                routeVerb = routeNodeAttrs.getNamedItem("verb").getNodeValue();

                routeParser = new RouteParser(routePath);
                if (routeParser.isDynamic()) {
                    routePathRegex = routeParser.getPathRegex();
                    Map<String, Class<?>> parameters = routeParser.getParameters();

                    addDynamicRoute(routePathRegex, new DynamicMethodRoute(routeController,
                            routeMethod,
                            routeVerb,
                            routePathRegex,
                            parameters));
                } else {
                    addStaticRoute(routePath, new MethodRoute(routeController, routeMethod,
                            routeVerb));
                }
            } else {
                throw new InputMismatchException();
            }
        }
    }
}
