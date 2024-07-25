/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import hr.algebra.model.Message;
import hr.algebra.service.MessengerService;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author boric
 */
public class MessageUtils {
    
    static Registry registry = null;
    private static MessengerService messengerService = null;
    
    public static void showInfoMessage(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    
    public static void sendMessage(String nickname, String message) {

        
        try {
            registry = LocateRegistry.getRegistry();
            messengerService = (MessengerService) registry.lookup("MessengerService");
            Message newMessage = new Message(nickname, message, LocalDateTime.now());
            messengerService.sendMessage(newMessage);

        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(MessageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static MessengerService getMessengerService() {

        try {
            registry = LocateRegistry.getRegistry();
            messengerService = (MessengerService) registry.lookup("MessengerService");
            return messengerService;

        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(MessageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
     public static List<Message> getAllMessages() {
        
        try {
            registry = LocateRegistry.getRegistry();
            messengerService = (MessengerService) registry.lookup("MessengerService");
            return messengerService.getAllMessages();
            

        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(MessageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
