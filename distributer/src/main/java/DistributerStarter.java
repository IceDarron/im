import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import server.DistributerServer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;


public class DistributerStarter {
    private static final Logger logger = LoggerFactory.getLogger(DistributerStarter.class);
    private static File cfg = null;
    private static File log = null;
    private static int gateId;

    public static void main(String[] args) throws Exception {

        configureAndStart(args);

    }

    static void configureAndStart(String[] args) throws ParseException {
        parseArgs(args);

        try {
            //parse xml File and apply it
            DocumentBuilder builder = DocumentBuilderFactory
                    .newInstance().newDocumentBuilder();
            Document doc = builder.parse(cfg);
            Element rootElement = doc.getDocumentElement();

            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression xPathExpression = null;
            NodeList nodeList = null;
            Element element = null;

            xPathExpression = xPath.compile("/gate/id");
            nodeList = (NodeList) xPathExpression.evaluate(rootElement, XPathConstants.NODESET);
            element = (Element) nodeList.item(0);
            gateId = Integer.parseInt(element.getAttribute("value"));
            logger.info("gate id " + gateId);

            xPathExpression = xPath.compile("/gate/gateserver");
            nodeList = (NodeList) xPathExpression.evaluate(rootElement, XPathConstants.NODESET);
            element = (Element) nodeList.item(0);
            int gateListenPort = Integer.parseInt(element.getAttribute("port"));
            logger.info("gateserver gateListenPort " + gateListenPort);

            xPathExpression = xPath.compile("/gate/auth");
            nodeList = (NodeList) xPathExpression.evaluate(rootElement, XPathConstants.NODESET);
            element = (Element) nodeList.item(0);
            String authIP = element.getAttribute("ip");
            int authPort = Integer.parseInt(element.getAttribute("port"));
            logger.info("AuthConnection auth ip: {}  auth port: {}", authIP, authPort);

            xPathExpression = xPath.compile("/gate/logic");
            nodeList = (NodeList) xPathExpression.evaluate(rootElement, XPathConstants.NODESET);
            element = (Element) nodeList.item(0);
            String logicIP = element.getAttribute("ip");
            int logicPort = Integer.parseInt(element.getAttribute("port"));
            logger.info("LogicConnection logic ip: {}  logic port: {}", logicIP, logicPort);

            //TODO init log congfigres

            //Now Start Servers
            new Thread(() -> DistributerServer.start(gateListenPort)).start();

            new Thread(() -> GateAuthConnection.startGateAuthConnection(authIP, authPort)).start();

            new Thread(() -> GateLogicConnection.startGateLogicConnection(logicIP, logicPort)).start();

        } catch (Exception e) {
            logger.error("init cfg error");
            e.printStackTrace();
        }
    }

    static void parseArgs(String[] args) throws ParseException {
        // Create a Parser
        CommandLineParser parser = new BasicParser();
        Options options = new Options();
        options.addOption("h", "help", false, "Print this usage information");
        options.addOption("c", "cfg", true, "config Absolute Path");
        options.addOption("l", "log", true, "log configuration");

        // Parse the program arguments
        CommandLine commandLine = parser.parse(options, args);
        // Set the appropriate variables based on supplied options

        if (commandLine.hasOption('h')) {
            printHelpMessage();
            System.exit(0);
        }
        if (commandLine.hasOption('c')) {
            cfg = new File(commandLine.getOptionValue('c'));
        } else {
            printHelpMessage();
            System.exit(0);
        }
        if (commandLine.hasOption('l')) {
            log = new File(commandLine.getOptionValue('l'));
        } else {
            printHelpMessage();
            System.exit(0);
        }
    }

    static void printHelpMessage() {
        System.out.println("Change the xml File and Log.XML Path to right Absolute Path base on your project Location in your computor");
        System.out.println("Usage example: ");
        System.out.println("java -cfg D:\\MyProject\\face2face\\gate\\src\\main\\resources\\auth.xml  -log D:\\MyProject\\face2face\\gate\\src\\main\\resources\\log.xml");
        System.exit(0);
    }

    public static int getGateId() {
        return gateId;
    }
}
