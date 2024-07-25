/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import hr.algebra.model.User;
import hr.algebra.repository.Repository;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author boric
 */
public class GameMenuSinglePlayerController implements Initializable {

    @FXML
    private TextField tfNickname;
    @FXML
    private Button btnPlay;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handlePlayButton();
    }    

    @FXML
    private void handlePlayButton() {
         btnPlay.setDisable(
        tfNickname.getText().trim().isEmpty()
        || tfNickname.getText().trim().equals("PC")
        );
    }

    @FXML
    private void btnPlay_onClick() throws IOException {
        // dodavamo user1
        Repository.addUser(new User(tfNickname.getText().trim(),0.0,null));
        
        // dodavamo kompjuter
        Repository.addUser(new User("PC", 0.0, null));
        
        Stage gameMenu = (Stage) btnPlay.getScene().getWindow();
        gameMenu.close();
        
        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/views/Teams.fxml"));
        Stage stage = new Stage();
        stage.setTitle("WORLD CUP 2022 - game");
        stage.setScene(new Scene(root, 1029, 693));
        stage.setResizable(false);
        stage.show();
        
    }
    @FXML
    private void handleKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                btnPlay_onClick();
            } catch (IOException ex) {
                Logger.getLogger(GameMenuSinglePlayerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
