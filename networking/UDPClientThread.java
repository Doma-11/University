/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.networking;

import hr.algebra.controllers.EntryMenuMultiplayerController;
import hr.algebra.repository.Repository;
import hr.algebra.utils.ByteUtils;
import java.io.IOException;
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
public class UDPClientThread extends Thread {
    
    // adresa D klase IPV$-> predvidena za multicast promet. SERVER NA GRUPI 230.1 NA PORTU 4446 SALJE SVIMA ZAINTERESIRANIMA PODATKE
    public static final String GROUP = "230.0.0.1";
    public static final int CLIENT_NICKNAME_PORT = 4446;
    
    
    private final EntryMenuMultiplayerController controller;

    public static boolean flag = true;

    public UDPClientThread(EntryMenuMultiplayerController controller) {
        this.controller = controller;
    }
    
    @Override
    public void run(){
        
         try (MulticastSocket client = new MulticastSocket(CLIENT_NICKNAME_PORT)) {

            InetAddress groupAddress = InetAddress.getByName(GROUP);
            //System.err.println(controller.hashCode() + " joining group");
            client.joinGroup(groupAddress);

            while (flag) {
                System.err.println(controller.hashCode() + " listening...");

                // first we read the payload length
                byte[] numberOfNicknameBytes = new byte[8];
                DatagramPacket packet = new DatagramPacket(numberOfNicknameBytes, numberOfNicknameBytes.length);
                client.receive(packet);
                int length = ByteUtils.byteArrayToInt(numberOfNicknameBytes);

                
// we can read payload of that length
                try{
                    byte[] buffer = new byte[length];
                    packet = new DatagramPacket(buffer, buffer.length);
                    client.receive(packet);
                    System.out.println(buffer.length);
                    System.out.println("Array size too large");
                    System.out.println("Max JVM memory" + Runtime.getRuntime().maxMemory());
                }
                catch(OutOfMemoryError oome){
                    
                }
               String receivedMessage = new String(packet.getData(), 0, packet.getLength());
               System.out.println("Client received message: " + receivedMessage);
                
               

                if (!receivedMessage.equals(Repository.getThisUser().getNickName())) {

                    Platform.runLater(() -> {
                       controller.showOpponent(receivedMessage);  
                    });

                }

            }
        } catch (IOException ex) {
            Logger.getLogger(UDPClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
