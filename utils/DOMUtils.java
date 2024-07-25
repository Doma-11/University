/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;


import hr.algebra.model.User;
import hr.algebra.model.Player;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author boric
 */
public class DOMUtils {

    private static final String FILENAME_PLAYERS = "users.xml";
    
    

    public static void saveUsers(ObservableList<User> users) throws TransformerException {

        try {
            Document document = createDocument("users");
            users.forEach(u -> document.getDocumentElement().appendChild(createUserElement(u, document)));
            saveDocument(document, FILENAME_PLAYERS);
            
            
            Transformer transformer= TransformerFactory.newInstance().newTransformer();
            Source xmlSource= new DOMSource(document);
            Result xmlResult= new StreamResult(new File("users.xml"));
            transformer.transform(xmlSource,xmlResult);
            
           
            
        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Document createDocument(String root) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation domImplementation = builder.getDOMImplementation();
        return domImplementation.createDocument(null, root, null);
    }

    private static Node createUserElement(User user, Document document) {
        
        Element element = document.createElement("user");
        element.appendChild(createElement(document, "nickName", user.getNickName()));
        element.appendChild(createElement(document, "points", String.valueOf(user.getPoints())));
        element.appendChild(createListElement(user.getPlayersInHand(), document, "playersinHand", "playerinHand"));
        
        return element;
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

    private static Node createListElement(ObservableList<Player> playersList, Document document, String listName, String itemName) {
        Element element = document.createElement(listName);
        System.out.println("++++++ SIZE PLAYER LIST: "+ playersList.size());
        if (playersList != null && !playersList.isEmpty()) {
            
            for (Player player : playersList) {
                if (player!=null) {
                    element.appendChild(createPlayerElement(player, document, itemName));
                }
            }
                   
        }
        

        return element;
    }

    private static Node createPlayerElement(Player player, Document document, String name) {
        Element element = document.createElement(name);
        element.appendChild(createElement(document, "firstName", player.getFirstName().toString()));
        element.appendChild(createElement(document, "lastName", player.getLastName().toString()));
        element.appendChild(createElement(document, "rating", player.getRating().toString()));
        element.appendChild(createElement(document, "picturePath", player.getPicturePath()));
        
        return element;
    }

    public static ObservableList<User> loadUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            Document document = createDocument(new File(FILENAME_PLAYERS));
            
            NodeList nodes = document.getElementsByTagName("user");
            
            for (int i = 0; i < nodes.getLength(); i++) {
                users.add(processUserNode((Element) nodes.item(i)));
                
            }
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        return users;
    }

    private static User processUserNode(Element element) {
         
        return new User(
                element.getElementsByTagName("nickName").item(0).getTextContent(),
                Double.valueOf(element.getElementsByTagName("points").item(0).getTextContent()),
                processListElement("playerinHand", element)
                       
        );
        
    }
    
    private static ObservableList<Player> processListElement(String itemName,Element element) {
        
        System.out.println("++++++ USAO U PROCES LIST ELEMENT: +++++++++");
        ObservableList<Player> players = FXCollections.observableArrayList();
        
        NodeList nodes = element.getElementsByTagName(itemName);
        
       System.out.println("++++++ VELICINA NODES:" +nodes.getLength()); 
       
       
        for (int i = 0; i < nodes.getLength(); i++) {
             players.add(processPlayerNode((Element) nodes.item(i)));
            }
        System.out.println("++++++ USAO U PROCES LIST ELEMENT velicina liste :" + players.size());
        return players;
        
        
    }
    
    private static Player processPlayerNode(Element element) {
        return new Player(
                element.getElementsByTagName("firstName").item(0).getTextContent(),
                element.getElementsByTagName("lastName").item(0).getTextContent(),
                Double.valueOf(element.getElementsByTagName("rating").item(0).getTextContent()),
                element.getElementsByTagName("picturePath").item(0).getTextContent()
                         
        );
    }
     
    private static Document createDocument(File file) throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        return document;
    }
}
