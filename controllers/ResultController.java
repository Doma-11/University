/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import hr.algebra.model.Team;
import hr.algebra.model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author boric
 */
public class ResultController implements Initializable {

    @FXML
    private ProgressIndicator correctProgress;

    @FXML
    private Label lbScore;
    
    @FXML
    private Label lbTotalGameResult;
    
    @FXML
    private Button btnMenuResult;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initObservables();
        
        calculateUserScore();
        
    }    
    
     @FXML
    private void btnMenuResult_OnAction(ActionEvent event) {
        exitGame();
    }
    
     private void exitGame() {
        
        try {
            openMainMenu();
        } catch (IOException ex) {
            Logger.getLogger(ResultsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
      private void openMainMenu() throws IOException {
        Stage multiplayer = (Stage) btnMenuResult.getScene().getWindow();
        multiplayer.close();

        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/views/MainMenu2.fxml"));
        Stage stage = new Stage();
        stage.setTitle("WC 2022");
        stage.setScene(new Scene(root, 673, 500));
        stage.setResizable(false);
        stage.show();
    }

    private void initObservables() {
        
        
        
        lbScore.setText("            "+QuizController.correct + "/10");
        
        float correctf= (float)QuizController.correct/10;
        correctProgress.setProgress(correctf);
        
        
    }

    private void calculateUserScore() {
        double quizGamePoints= (double)QuizController.correct*10;
        double totalWorldCupGamePoints =  quizGamePoints;
        lbTotalGameResult.setText("               Total WC2022 Quiz Points: " + totalWorldCupGamePoints);
        
    }
    
    
}
