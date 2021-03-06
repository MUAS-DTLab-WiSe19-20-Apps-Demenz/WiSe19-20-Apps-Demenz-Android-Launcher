package edu.hm.launcher.config.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import edu.hm.launcher.config.container.ConfigurationTutorialContainer;
import edu.hm.launcher.config.container.TutorialContainer;

public class XmlParserV2 implements IConfigurationParser {

    @Override
    public ConfigurationTutorialContainer parseConfig(InputStream xmlStream) throws IOException, ConfigParseException {

        // Creates new container
        ConfigurationTutorialContainer container = new ConfigurationTutorialContainer();

        try {
            // Build document from stream
            DocumentBuilder xmlBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDocument = xmlBuilder.parse(xmlStream);

            // Then find all Tutorial nodes
            NodeList tutorialList = xmlDocument.getElementsByTagName("AppGroup");

            // Create an tutorials buffer to sort them
            TutorialContainer[] tutorials = new TutorialContainer[tutorialList.getLength()];


            for (int i = 0; i < tutorialList.getLength(); i++) {
                if (tutorialList.item(i).getNodeType() == Node.ELEMENT_NODE) {

                    final Element tutorialElement = (Element) tutorialList.item(i);
                    final String title = tutorialElement.getAttribute("title");
                    final String folder = tutorialElement.getAttribute("folder");
                    final String drawable = tutorialElement.getAttribute("image");

                    final NodeList content = tutorialElement.getElementsByTagName("Tutorial");


                    String[] file = new String[content.getLength()];
                    String[] tutorialTitle = new String[content.getLength()];

                    for (int index = 0; index < content.getLength(); index++)   {
                        final Element contentElement = (Element) content.item(index);
                        file[index] = contentElement.getAttribute("file");
                        tutorialTitle[index] = contentElement.getAttribute("title");
                    }

                    tutorials[i] = new TutorialContainer(title, folder, tutorialTitle, file, drawable);
                }
            }

            // Then construct container from TutorialsBuffer
            for (int i = 0; i < tutorials.length; i++) {
                TutorialContainer tutorial = tutorials[i];
                if(tutorial == null) {
                    throw new ConfigParseException(String.format("Id %d is not contained", i));
                }
                container.add(tutorial);
            }

        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException(e);
        }

        return container;
    }
}
