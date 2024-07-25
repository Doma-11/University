/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.networking;

import hr.algebra.model.Game;
import hr.algebra.repository.Repository;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author boric
 */
public class TCPClient extends Thread {
    
    public static final String HOST = "192.168.88.1";
    public static final int GAME_OBJECT_PORT = 12345;

    @Override
    public void run() {
        try (Socket clientSocket = new Socket(HOST, GAME_OBJECT_PORT)) {
            System.err.println("Client is connecting to: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

            System.err.println("Client salje za game");
            sendSerializableRequest(clientSocket);
            
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void sendSerializableRequest(Socket clientSocket) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
        Game game = Repository.getGameObject();
        oos.writeObject(game);
       

    }
    
    
    
}
