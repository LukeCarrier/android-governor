package im.carrier.luke.governor.config;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import im.carrier.luke.governor.config.route.AssetRoute;
import im.carrier.luke.governor.config.route.MethodRoute;

/**
 * Root configuration node.
 */
public class Configuration {
    /**
     * Request routing table.
     */
    protected HashMap<String, Route> routingTable;

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
     * Create a configuration object from an XML document.
     *
     * @param xml The XML Document object to parse values from.
     * @return A populated configuration object.
     * @throws XPathExpressionException When the configuration file is invalid (e.g. missing
     *                                  required nodes).
     */
    public static Configuration fromXml(Document xml) throws XPathExpressionException {
        xml.getDocumentElement().normalize();

        XPath xPath = XPathFactory.newInstance().newXPath();
        Configuration config = new Configuration();

        NodeList portNodes = (NodeList) xPath.evaluate("/governor-config/server/port", xml,
                XPathConstants.NODESET);
        config.setPort(Integer.parseInt(portNodes.item(0).getTextContent()));

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

            config.addRoute(routePath, route);
        }

        return config;
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
     * Get the server port.
     *
     * @return The server port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the server port.
     */
    public void setPort(int port) {
        this.port = port;
    }
}
