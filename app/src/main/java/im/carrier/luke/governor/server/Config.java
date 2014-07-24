package im.carrier.luke.governor.server;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import im.carrier.luke.governor.server.resources.ConfigControllerMethod;

/**
 * Created by luke on 24/07/14.
 */
public class Config {
    protected List<ConfigController> controllers;

    protected int port;

    public static Config fromXml(Document xml) throws XPathExpressionException {
        xml.getDocumentElement().normalize();
        XPathFactory xPathFactory = XPathFactory.newInstance();

        Config config = new Config();

        XPath portXPath = xPathFactory.newXPath();
        XPathExpression portXPathExpr = portXPath.compile("/governor-config/server/port");
        NodeList portNodes = (NodeList) portXPathExpr.evaluate(xml, XPathConstants.NODESET);

        config.setPort(Integer.parseInt(portNodes.item(0).getTextContent()));

        NodeList controllers = xml.getElementsByTagName("controller");
        for (int i = 0; i < controllers.getLength(); i++) {
            Node controllerXml = controllers.item(i);

            if (controllerXml.getNodeName() != "controller") {
                continue;
            }

            NamedNodeMap controllerXmlAttributes = controllerXml.getAttributes();

            ConfigController controller = new ConfigController(
                    controllerXmlAttributes.getNamedItem("name").getTextContent(),
                    controllerXmlAttributes.getNamedItem("path").getTextContent());

            NodeList methodsXml = controllerXml.getChildNodes();
            for (int j = 0; j < methodsXml.getLength(); j++) {
                Node methodXml = methodsXml.item(j);

                if (controllerXml.getNodeName() != "method") {
                    continue;
                }

                NamedNodeMap methodXmlAttributes = methodXml.getAttributes();

                ConfigControllerMethod method = new ConfigControllerMethod(
                        methodXmlAttributes.getNamedItem("name").getTextContent(),
                        methodXmlAttributes.getNamedItem("path").getTextContent());

                controller.addMethod(method);
            }

            config.addController(controller);
        }

        return config;
    }

    public void addController(ConfigController controller) {
        this.controllers.add(controller);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
