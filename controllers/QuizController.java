/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author boric
 */
public class QuizController implements Initializable {

    @FXML
    private Label lbQuestion;
    @FXML
    private Button opt1;
    @FXML
    private Button opt3;
    @FXML
    private Button opt2;
    @FXML
    private Button opt4;
    @FXML
    private Label lbCorrectAnswer;
    
    
    int counter= 0;
    static int correct= 0;
    static int wrong = 0;

    
    private final String IMAGES_DIR = "/hr/algebra/resources/images/";   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initObservables();
        loadQuestions();
        
    }    

    @FXML
    private void opt1Clicked(ActionEvent event) {
        checkAnswer(opt1.getText().toString());
        if(checkAnswer(opt1.getText().toString())){
            correct= correct +1;
            lbCorrectAnswer.setText(opt1.getText());
            clearForm();
        }
        else{
            wrong = wrong +1;
            lbCorrectAnswer.setText(opt1.getText());
            clearForm();
        }
        if(counter == 9){
              try {
                    Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    thisStage.close();
                    
                    FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/hr/algebra/views/Result2.fxml"));
                    Scene scene= new Scene(fxmlLoader.load());
                    Stage stage= new Stage();
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    scene.setFill(Color.TRANSPARENT);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } 
        else{
            counter ++;
            loadQuestions();
        }
        
    }
    boolean  checkAnswer(String answer) {
        if(counter ==0){
            if(answer.equals("Round of 16")){
                return true;
            }else{
                 return false;
            }
           
        }
         if(counter ==1){
            if(answer.equals("1")){
                return true;
            }else{
                 return false;
            }
           
        }
         if(counter ==2){
            if(answer.equals("172")){
                return true;
            }else{
                 return false;
            }
           
        }
         if(counter ==3){
            if(answer.equals("Kylian Mbappe")){
                return true;
            }else{
                 return false;
            }
           
        }
         if(counter ==4){
            if(answer.equals("Joško Gvardiol")){
                return true;
            }else{
                 return false;
            }
           
        }
         if(counter ==5){
            if(answer.equals("Dominik Livaković")){
                return true;
            }else{
                 return false;
            }
           
        }
         if(counter ==6){
            if(answer.equals("Enner Valencia")){
                return true;
            }else{
                 return false;
            }
           
        }
         if(counter ==7){
            if(answer.equals("Ghana")){
                return true;
            }else{
                 return false;
            }
           
        }
         if(counter ==8){
            if(answer.equals("Barcelona")){
                return true;
            }else{
                 return false;
            }
           
        }
         if(counter ==9){
            if(answer.equals("Al Rihla")){
                return true;
            }else{
                 return false;
            }
           
        }
         return false;
         
    }

    @FXML
    private void opt3Clicked(ActionEvent event) {
        checkAnswer(opt3.getText().toString());
        if(checkAnswer(opt3.getText().toString())){
            correct= correct +1;
            lbCorrectAnswer.setText(opt3.getText().toString());
            clearForm();
        }
        else{
            wrong = wrong +1;
            lbCorrectAnswer.setText("Wrong answer");
            clearForm();
        }
        if( counter == 9){
              try {
                    Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    thisStage.close();
                    
                    FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/hr/algebra/views/Result2.fxml"));
                    Scene scene= new Scene(fxmlLoader.load());
                    Stage stage= new Stage();
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    scene.setFill(Color.TRANSPARENT);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                } 
        } 
        else{
            counter ++;
            loadQuestions();
        }
    }

    @FXML
    private void opt2Clicked(ActionEvent event) {
        checkAnswer(opt2.getText().toString());
        if(checkAnswer(opt2.getText().toString())){
            correct= correct +1;
            lbCorrectAnswer.setText(opt2.getText().toString());
            clearForm();
        }
        else{
            wrong = wrong +1;
            lbCorrectAnswer.setText(opt2.getText().toString());
            clearForm();
        }
        if( counter == 9){
              try {
                    Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    thisStage.close();
                    
                    FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/hr/algebra/views/Result2.fxml"));
                    Scene scene= new Scene(fxmlLoader.load());
                    Stage stage= new Stage();
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    scene.setFill(Color.TRANSPARENT);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } 
        else{
            counter ++;
            loadQuestions();
        }
    }

    @FXML
    private void opt4Clicked(ActionEvent event) {
        checkAnswer(opt4.getText().toString());
        if(checkAnswer(opt4.getText().toString())){
            correct= correct +1;
            lbCorrectAnswer.setText(opt4.getText().toString());
            clearForm();
        }
        else{
            wrong = wrong +1;
            lbCorrectAnswer.setText(opt4.getText().toString());
            clearForm();
        }
        if( counter == 9){
           try {
                    Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    thisStage.close();
                    
                    FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/hr/algebra/views/Result2.fxml"));
                    Scene scene= new Scene(fxmlLoader.load());
                    Stage stage= new Stage();
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    scene.setFill(Color.TRANSPARENT);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } 
        else{
            counter ++;
            loadQuestions();
        }
    }

    private void loadQuestions() {
        if(counter==0){
            lbQuestion.setText("1.In which stage of the tournament were Argentina not given a penalty?");
            opt1.setText("Round of 16");
            opt2.setText("Quarter-final");
            opt3.setText("Semi-final");
            opt4.setText("Final");
        }
        if(counter==1){
            lbQuestion.setText("2.How many goals have Belgium scored in World Cup 2022?");
            opt1.setText("1");
            opt2.setText("2");
            opt3.setText("3");
            opt4.setText("4");
        }
       if(counter==2){
            lbQuestion.setText("3.How many goals were scored in World Cup 2022, surpassing the previous record of 171 goals?");
            opt1.setText("183");
            opt2.setText("181");
            opt3.setText("172");
            opt4.setText("176");            
        }
        if(counter==3){
            lbQuestion.setText("4.Who is the player that completed the most dribbles (25) and the most touches in opposition box(69)?");
            opt1.setText("Antoine Griezmann");
            opt2.setText("Lionel Messi");
            opt3.setText("Bruno Fernandes");
            opt4.setText("Kylian Mbappe");
        }        
        if(counter==4){
            lbQuestion.setText("5.Who is player that had the most clearences than any other play in the World Cup 2022?");
            opt1.setText("Virgil Van Dijk");
            opt2.setText("Nicolas Otamendi");
            opt3.setText("Joško Gvardiol");
            opt4.setText("Raphael Varane");
        }
        if(counter==5){
            lbQuestion.setText("6.Who is the goalkeeper that had the most saves in the World Cup 2022?");
            opt1.setText("Bono");
            opt2.setText("Dominik Livaković");
            opt3.setText("Emiliano Martinez");
            opt4.setText("Hugo Lloris"); 
        }
         if(counter==6){
            lbQuestion.setText("7.Who scored the first goal of the 2022 World Cup?");
            opt1.setText("Akram Afif");
            opt2.setText("Moises Caicedo");
            opt3.setText("Cody Gakpo");
            opt4.setText("Enner Valencia");            
        }
        if(counter==7){
            lbQuestion.setText("8.Which nation had the youngest average squad age?");
            opt1.setText("Spain");
            opt2.setText("Ghana");
            opt3.setText("Ecuador");
            opt4.setText("United States");
        }        
        if(counter==8){
            lbQuestion.setText("9.Which club sent the most players to the World Cup?");
            opt1.setText("PSG");
            opt2.setText("Real Madrid");
            opt3.setText("Barcelona");
            opt4.setText("Manchester City");
        }
        if(counter==9){
            lbQuestion.setText("10.What was the name of Adidas' official ball for the tournament?");
            opt1.setText("Al Sadd");
            opt2.setText("Al Shearer");
            opt3.setText("Al Rihla");
            opt4.setText("Al Hilal"); 
        }
    }

    private void clearForm() {
        lbCorrectAnswer.setText("");
    }

    /*private void initObservables() {
        Image image= new Image( IMAGES_DIR + "wclogotransp.png");
        imgWcLogo.setImage(image);
    }

    */
}
