/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author boric
 */
public class MainMenuController implements Initializable {

    @FXML
    private Button btnMultiplayer;
    @FXML
    private Button btnSinglePlayer;

    @FXML
    private Button btnQuiz;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnMultiplayerOnAction(ActionEvent event) throws IOException{
        Stage mainMenu = (Stage) btnMultiplayer.getScene().getWindow();
        mainMenu.close();

        
        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/views/GameMenuMultiplayer.fxml"));
        Stage stage = new Stage();
        stage.setTitle("WORLD CUP 2022 - enter nickname");
        stage.setScene(new Scene(root, 673, 500));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void btnSinglePlayerOnAction(ActionEvent event) throws IOException {
        Stage mainMenu = (Stage) btnSinglePlayer.getScene().getWindow();
        mainMenu.close();

        
        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/views/GameMenuSinglePlayer.fxml"));
        Stage stage = new Stage();
        stage.setTitle("WORLD CUP 2022 - enter nickname");
        stage.setScene(new Scene(root, 700, 469));
        stage.setResizable(false);
        stage.show();
        
    }
    @FXML
    private void btnQuizOnAction(ActionEvent event) throws IOException {
        Stage mainMenu = (Stage) btnQuiz.getScene().getWindow();
        mainMenu.close();

        
        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/views/Quiz.fxml"));
        Stage stage = new Stage();
        stage.setTitle("WORLD CUP 2022 - QUIZ");
        stage.setScene(new Scene(root, 900, 700));
        stage.setResizable(false);
        stage.show();
        
    }
    
}
