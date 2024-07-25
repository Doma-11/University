/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;



import hr.algebra.model.GameMove;

import hr.algebra.model.Player;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author boric
 */
public class DOMUtilsGame {

    private static final String FILENAME_GAMES = "gamemoves.xml";

    public static void saveGameMoveToXml(GameMove player) {

        List<GameMove> playerList = readGameMovesFromXmlFile();
        playerList.add(player);

            try {
                Document document = createDocument("gameMoves");

                for(GameMove gameMoveXmlNode : playerList) {

                    Element gameMoveElement = document.createElement("gameMove");
                    document.getDocumentElement().appendChild(gameMoveElement);

                    gameMoveElement.appendChild(createElement(document, "firstName", gameMoveXmlNode.getFirstName()));
                    gameMoveElement.appendChild(createElement(document, "lastName", gameMoveXmlNode.getLastName()));
                    gameMoveElement.appendChild(createElement(document, "rating", gameMoveXmlNode.getRating().toString()));
                    gameMoveElement.appendChild(createElement(document, "picturePath", gameMoveXmlNode.getPicturePath()));
                    gameMoveElement.appendChild(createElement(document, "chosenComboBoxIndex", gameMoveXmlNode.getChosenComboboxIndex()));
                    
                }

                saveDocument(document, FILENAME_GAMES);
            } catch (ParserConfigurationException | TransformerException ex) {
                ex.printStackTrace();
            }
    }

  private static Document createDocument(String root) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation domImplementation = builder.getDOMImplementation();
        return domImplementation.createDocument(null, root, null);
    }
  
  
    private static Node createElement(Document document, String tagName, String data) {
        Element element = document.createElement(tagName);
        Text text = document.createTextNode(data);
        element.appendChild(text);
        return element;
    }

    private static void saveDocument(Document document, String fileName) throws TransformerException {
       TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        //transformer.transform(new DOMSource(document), new StreamResult(System.out));
        transformer.transform(new DOMSource(document), new StreamResult(new File(fileName)));
    }

    public static List<GameMove> readGameMovesFromXmlFile() {

        List<GameMove> playerList = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(FILENAME_GAMES));
            Node node = document.getDocumentElement();

            NodeList childNodes = node.getChildNodes();

            int numberOfNodes = childNodes.getLength();

            for(int n = 0; n < numberOfNodes; n++) {

                Node parentNode = childNodes.item(n);

                if (parentNode.getNodeType() == Node.ELEMENT_NODE) {

                    NodeList playerNodes = parentNode.getChildNodes();

                    String firstName="";
                    String lastName="";
                    String rating="";
                    String picturePath="";                    
                    String chosenComboBoxIndex="";
                    
                    for (int i = 0; i < playerNodes.getLength(); i++) {

                        Node playerNode = playerNodes.item(i);

                        if (playerNode.getNodeType() == Node.ELEMENT_NODE) {

                            switch (playerNode.getNodeType()) {
                                case Node.ELEMENT_NODE:
                                    Element nodeElement = (Element) playerNode;
                                    String nodeName = nodeElement.getNodeName();
                                    if (nodeName.equals("firstName")) {
                                        firstName= nodeElement.getTextContent();
                                    }
                                    else if(nodeName.equals("lastName")) {
                                       lastName = nodeElement.getTextContent();
                                    }
                                    else if(nodeName.equals("rating")) {
                                        rating = nodeElement.getTextContent();
                                    }
                                    else if(nodeName.equals("picturePath")) {
                                        picturePath= nodeElement.getTextContent();
                                    }
                                    else if(nodeName.equals("chosenComboBoxIndex")) {
                                        chosenComboBoxIndex= nodeElement.getTextContent();
                                    }
                                    break;
                                case Node.TEXT_NODE:
                                    break;
                                case Node.CDATA_SECTION_NODE:
                                    break;
                            }
                        }
                    }

                    GameMove player = new GameMove(firstName,lastName,rating,picturePath,chosenComboBoxIndex);
                    
                    playerList.add(player);
                }
            }
        }
        catch(ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }

        return playerList;
    }
}
