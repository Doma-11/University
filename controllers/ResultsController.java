/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import hr.algebra.model.Player;
import hr.algebra.model.User;
import hr.algebra.repository.Repository;
import static hr.algebra.utils.DOMUtils.loadUsers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author boric
 */
public class ResultsController implements Initializable {

    @FXML
    private Label lbMyNickname;
    @FXML
    private Label lbOpponentsNickname;
    @FXML
    private Label lbMyPoints;
    @FXML
    private Label lbOpponentPoints;
    @FXML
    private VBox vbMyPlayers;
    @FXML
    private VBox vbOpponentPlayers;
    @FXML
    private Button btnMenu;

    /**
     * Initializes the controller class.
     */
    
    
    
    private ObservableList<User> list;
    private ObservableList<Node> playersInHand = FXCollections.observableArrayList();;
    private ObservableList<Node> opponentsPlayersInHand = FXCollections.observableArrayList();;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       loadDOM();
       initLabels();
       
       
       Bindings.bindContentBidirectional(playersInHand, vbMyPlayers.getChildren());
       Bindings.bindContentBidirectional(opponentsPlayersInHand, vbOpponentPlayers.getChildren());
       
       initPlayers();
    }    

    @FXML
    private void btnMenu_OnAction(ActionEvent event) {
        exitGame();
    }
    
     private void exitGame() {
        clearRepo();
        try {
            openMainMenu();
        } catch (IOException ex) {
            Logger.getLogger(ResultsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
      private void clearRepo() {
        Repository.deleteUsers();
        Repository.setGameObject(null);
        Repository.deleteFrstToPlay();
        Repository.deleteThisUser();
        
    } 
     
      private void openMainMenu() throws IOException {
        Stage multiplayer = (Stage) btnMenu.getScene().getWindow();
        multiplayer.close();

        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/views/MainMenu2.fxml"));
        Stage stage = new Stage();
        stage.setTitle("WC 2022");
        stage.setScene(new Scene(root, 673, 500));
        stage.setResizable(false);
        stage.show();
    }
     
      
     private void loadDOM() {
        list = FXCollections.observableArrayList();
        list = loadUsers();
        System.out.println("************(LOADDOM) Useri u listi- 1. user: " + list.get(0).getNickName()+ " drugi user: " + list.get(1).getNickName());
        System.out.println("************(LOADDOM) velicina liste- 1. user: " + list.get(0).getPlayersInHand().size()+ " playeri u listi dva: " + list.get(1).getPlayersInHand());
        list.forEach(u -> print(u));
        
    } 
     
     
      private void print(User u) {
        System.out.println(u.getNickName());
        System.out.println(u.getPoints());
        u.getPlayersInHand().forEach(p -> System.out.println(p.getPicturePath()));
        
    }
      
      private void initLabels() {
        lbMyNickname.setText(list.get(0).getNickName().toString());
        lbOpponentsNickname.setText(list.get(1).getNickName());
        lbMyPoints.setText(String.format("%.2f",list.get(0).getPoints()));
        lbOpponentPoints.setText(String.format("%.2f",list.get(1).getPoints()));
    }
    
      
       private void initPlayers() {

        list.get(0).getPlayersInHand().forEach(p -> addPlayer(p, playersInHand));
       
        list.get(1).getPlayersInHand().forEach(p -> addPlayer(p, opponentsPlayersInHand));
    }
       
       
        private void addPlayer(Player p, ObservableList<Node> list) {
        
        Label label = new Label();
        label.setText( p.getFirstName() + " " + p.getLastName() + ": "+ p.getRating());
        list.add(label);
        
    }
}
