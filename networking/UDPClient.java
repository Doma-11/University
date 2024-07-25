/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.networking;

import hr.algebra.controllers.TeamMultiplayerController;
import hr.algebra.model.Game;
import hr.algebra.repository.Repository;
import hr.algebra.utils.ByteUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author boric
 */
public class UDPClient extends Thread {
    
    public static final int CLIENT_GAME_PORT = 4447;
    public static final String GROUP = "230.0.0.2";
    
    private final TeamMultiplayerController multiplayerController;
    
    public static boolean flag = true;
    
     public UDPClient(TeamMultiplayerController controller) {
        this.multiplayerController = controller;
    }
     
     
      @Override
    public void run() {
        // we use new Socket for each client call
        
        
        try (MulticastSocket client = new MulticastSocket(CLIENT_GAME_PORT)) {

            
            InetAddress groupAddress = InetAddress.getByName(GROUP);
            //System.err.println(controller.hashCode() + " joining group");
            client.joinGroup(groupAddress);
            
            while (true) {
                System.err.println(multiplayerController.hashCode() + " listening...");
                System.out.println("uso u udp clienta");
                
                // first we read the payload length
                byte[] numberOfGameBytes = new byte[64];
                DatagramPacket packet = new DatagramPacket(numberOfGameBytes, numberOfGameBytes.length);
                client.receive(packet);
                int length = ByteUtils.byteArrayToInt(numberOfGameBytes);
                System.out.println("checkpoint 1");
                 // we can read payload of that length
                byte[] buffer = new byte[length];
                packet = new DatagramPacket(buffer, buffer.length);
                client.receive(packet);
                System.out.println("checkpoint 2");
                try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                        ObjectInputStream ois = new ObjectInputStream(bais)) {
                    Game game = (Game) ois.readObject();
                    // send to Event Queue to be processed by the application thread
                    System.out.println("checkpoint 3. tu smo kod uvjeta za igru i deserijalizaciju");
                    
                    if (game != null && !game.getUsers().get(0).getNickName().equals(Repository.getThisUser().getNickName())) {
                        Platform.runLater(() -> {
                        multiplayerController.deserializeGame(game);
                            System.out.println("uso u platform run later, u udp clientu inicijalizirao deserijalizaciju" + Repository.getThisUser());
                    });
                       
                    }
                    // ovo smo dodali da vidimo jel radi ovaj objekt za player1(Domagoja) u konzoli da ispisuje Uso u platformu run later Domagoj
                    /*
                     if(game != null && !game.getUsers().get(1).getNickName().equals(Repository.getThisUser().getNickName())){
                        Platform.runLater(() -> {
                        multiplayerController.deserializeGame(game);
                            System.out.println("uso u platform run later-> " + Repository.getThisUser());
                    });
                    }
                     */
                    
                    if (game == null ) {
                        Platform.runLater(() -> {
                        multiplayerController.deserializeGame(game);
                            System.out.println("GAME JE NULL-> " + Repository.getThisUser());
                    });
                       
                    }
                    
                   
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
                }

               }
            } catch (IOException ex) {
            Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);}
    }
     
    
}
