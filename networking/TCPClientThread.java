/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.networking;

import hr.algebra.repository.Repository;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author boric
 */
public class TCPClientThread  extends Thread {
    public static final String HOST = "192.168.88.1";
    public static final int NICKNAME_PORT = 1989;
   
    public static boolean NICKNAME_FLAG = true;
    
    @Override
    public void run() {
        
        while (NICKNAME_FLAG) {
            try (Socket clientSocket = new Socket(HOST, NICKNAME_PORT)) {
                System.err.println("Client is connecting to: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                sendPrimitiveRequest(clientSocket);

                Thread.sleep(5000);

            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(TCPClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    private static void sendPrimitiveRequest(Socket clientSocket) throws IOException {
        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

        dos.writeUTF(Repository.getThisUser().getNickName());
    }
}
