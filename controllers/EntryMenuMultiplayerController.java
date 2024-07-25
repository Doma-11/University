/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import hr.algebra.model.Player;
import hr.algebra.model.User;
import hr.algebra.networking.TCPClientThread;
import hr.algebra.networking.UDPClientThread;
import hr.algebra.repository.Repository;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author boric
 */
public class EntryMenuMultiplayerController implements Initializable {

    @FXML
    private Label lbPlayersNickname;
    @FXML
    private Button btnPlay;
    @FXML
    private Label lbOpponentsNickname;
    @FXML
    private Label lbMessage;

    private Timeline timeline;
    
    /**
     * Initializes the controller class.
     */
    
    private final UDPClientThread udpClientThread = new UDPClientThread(this);
    private final TCPClientThread tcpClientThread = new TCPClientThread();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       initUsersName();
       handleButton();
       initAnimation();
       initClientThread();
    }    

    @FXML
    private void btnPlay_onClick(ActionEvent event) throws IOException {
        addUsersToGame();
        
        Stage entryMenu = (Stage) btnPlay.getScene().getWindow();
        entryMenu.close();

        
        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/views/TeamMultiplayer2.fxml"));
        Stage stage = new Stage();
        stage.setTitle("WC2022 game");
        stage.setScene(new Scene(root, 1029, 693));
        stage.setResizable(false);
        stage.show();
        
        
        
    }
    
    
    
     private void initUsersName() {
       lbPlayersNickname.setText(Repository.getThisUser().getNickName());
       
    }

    private void handleButton() {
        btnPlay.setDisable(lbOpponentsNickname.getText().isEmpty());
    }

    private void initAnimation() {
        timeline= new Timeline(
                new KeyFrame(Duration.seconds(0.75), e -> lbMessage.setVisible(false)),
                new KeyFrame(Duration.seconds(1.5), e -> lbMessage.setVisible(true))
        );
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void initClientThread() {
        
        tcpClientThread.setDaemon(true);
        tcpClientThread.start();
        
        udpClientThread.setDaemon(true);
        udpClientThread.start();
    }

    public void showOpponent(String nickname){
        stopShowingMessage();
       
        lbOpponentsNickname.setText(nickname);
        
        //zaustavi klijentsku dretvu koja ti salje igrace
        //UDPClientThread.flag = false;
        
        //nakon 6 sekundi od pojavljivanja imena zaustavi
        enablePlayButton();
       
    }

    private void addUsersToGame() {
        
        //prvo sebe dodajemo
        Repository.addUser(Repository.getThisUser());
        
        //onda kreiramo drugog playera preko nickname
        Repository.addUser(new User(lbOpponentsNickname.getText(), 0.0,  null));
    }


    private void stopShowingMessage() {
        timeline.stop();
        lbMessage.setVisible(false);
    }

    private void enablePlayButton() {
        
        new Thread() {
            @Override
            public void run() {

                try {
                    Thread.sleep(8000);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
                Platform.runLater(() -> {
                    //TCPClientThread.flag = false;
                     btnPlay.setDisable(false);
                });
            }
        }.start();
    }

}
