/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.service;

import hr.algebra.model.Message;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author boric
 */

// nas interface extenda drugi interface, mi extendamo remote interface, ovaj interface implementiramo u networkingu
public interface MessengerService extends Remote{
    
    void sendMessage(Message chatMessage) throws RemoteException;
    
    List<Message> getAllMessages() throws RemoteException;
    
    Message getLastMessage() throws RemoteException;
    
    void clearMessages() throws RemoteException;
    
}
