package com.governorapp.config;

import android.content.SharedPreferences;

import com.governorapp.config.route.AssetRoute;
import com.governorapp.config.route.MethodRoute;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Root configuration node.
 */
public class Configuration {
    /**
     * Request routing table.
     */
    protected Map<String, Route> routingTable;

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
     * Constructor.
     */
    public Configuration() {
        routingTable = new HashMap<String, Route>();
    }

    /**
     * Add a route.
     *
     * @param path  The path to respond to.
     * @param route The route to respond with.
     */
    public void addRoute(String path, Route route) {
        this.routingTable.put(path, route);
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
    public Route getRoute(String path) throws IOException {
        Route route = this.routingTable.get(path);

        if (route == null) {
            throw new IOException();
        }

        return route;
    }

    /**
     * Get the routing table.
     *
     * @return
     */
    public Map<String, Route> getRoutes() {
        return routingTable;
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

            Route route;

            if (routeNodeName.equals("asset")) {
                route = new AssetRoute(routeNodeAttrs.getNamedItem("file").getNodeValue(),
                        routeNodeAttrs.getNamedItem("mimetype").getNodeValue());
            } else if (routeNodeName.equals("method")) {
                route = new MethodRoute(routeNodeAttrs.getNamedItem("controller").getNodeValue(),
                        routeNodeAttrs.getNamedItem("method").getNodeValue(),
                        routeNodeAttrs.getNamedItem("verb").getNodeValue());
            } else {
                throw new InputMismatchException();
            }

            addRoute(routePath, route);
        }
    }
}
