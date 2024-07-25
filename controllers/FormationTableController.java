/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import hr.algebra.model.Team;
import hr.algebra.model.User;
import hr.algebra.repository.Repository;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
public class FormationTableController implements Initializable {

    @FXML
    private Label lbDefender1;
    @FXML
    private Label lbDefender2;
    @FXML
    private Label lbDefender3;
    @FXML
    private Label lbDefender4;
    @FXML
    private Label lbMiedfielder1;
    @FXML
    private Label lbMiedfielder2;
    @FXML
    private Label lbMiedfielder3;
    @FXML
    private Label lbAttacker1;
    @FXML
    private Label lbAttacker2;
    @FXML
    private Label lbAttacker3;
    @FXML
    private Label lbGk;
    @FXML
    private Label lbSelectedTeamName;
    @FXML
    private Label lbTotalPoints;
    @FXML
    private ImageView imgGk;
    @FXML
    private ImageView imgDefender1;
    @FXML
    private ImageView imgDefender2;
    @FXML
    private ImageView imgDefender3;
    @FXML
    private ImageView imgDefender4;
    @FXML
    private ImageView imgMiedfielder1;
    @FXML
    private ImageView imgMiedfielder2;
    @FXML
    private ImageView imgAttacker1;
    @FXML
    private ImageView imgMiedfielder3;
    @FXML
    private ImageView imgAttacker2;
    @FXML
    private ImageView imgAttacker3;
    @FXML
    private Button btnQuiz;
    @FXML
    private ImageView imgIcon;
    @FXML
    private TableView<Team> tvTeams;
    @FXML
    private TableColumn<User, String> tcUser;
    @FXML
    private TableColumn<Team, String> tcTeamName;
    @FXML
    private TableColumn<Team, String> tcPoints;

    /**
     * Initializes the controller class.
     */
    
    
    
    private final String IMAGES_DIR = "/hr/algebra/resources/images/";   
    
    List<Label>formationLabels= Arrays.asList(
            lbGk,
            lbDefender1,lbDefender2,
            lbDefender3,lbDefender4,
            lbMiedfielder1,lbMiedfielder2,lbMiedfielder3,
            lbAttacker1,lbAttacker2,lbAttacker3);
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initObservables();
        initTableCells();
       
    }    
    
     @FXML
    private void playQuiz(ActionEvent event) throws IOException {
        
        
        btnQuiz.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    thisStage.close();
                    
                    FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/hr/algebra/views/Quiz.fxml"));
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
            
        });
       
          
    }
    
      public void displayGkName(String name,String surname){
         String fullName=name + " " + surname;
         lbGk.setText(fullName);
    }
      public void displayDf1Name(String name,String surname){
         String fullName=name + " " + surname;
         lbDefender1.setText(fullName);
    }
       public void displayDf2Name(String name,String surname){
         String fullName=name + " " + surname;
         lbDefender2.setText(fullName);
    }
           public void displayDf3Name(String name,String surname){
         String fullName=name + " " + surname;
         lbDefender3.setText(fullName);
    }
       public void displayDf4Name(String name,String surname){
         String fullName=name + " " + surname;
         lbDefender4.setText(fullName);
    }
       
       
       
       public void displayMf1Name(String name,String surname){
         String fullName=name + " " + surname;
         lbMiedfielder1.setText(fullName);
    }
       public void displayMf2Name(String name,String surname){
         String fullName=name + " " + surname;
         lbMiedfielder2.setText(fullName);
    }
           public void displayMf3Name(String name,String surname){
         String fullName=name + " " + surname;
         lbMiedfielder3.setText(fullName);
    }
           
           
        public void displayAf1Name(String name,String surname){
         String fullName=name + " " + surname;
         lbAttacker1.setText(fullName);
    }
        public void displayAf2Name(String name,String surname){
         String fullName=name + " " + surname;
         lbAttacker2.setText(fullName);
    }
           public void displayAf3Name(String name,String surname){
         String fullName=name + " " + surname;
         lbAttacker3.setText(fullName);
    }
         
          public void setGkImage( String path){
          Image image= new Image(IMAGES_DIR + path);
          imgGk.setImage(image);
          imgGk.getStyleClass().add("round-image");
          
      }      
           
           
            
      public void setDf1Image( String path){
          Image image= new Image(IMAGES_DIR + path);
          imgDefender1.setImage(image);
          imgDefender1.getStyleClass().add("round-image");
          
      }           
      
      
      public void setDf2Image( String path){
          Image image= new Image(IMAGES_DIR + path);
          imgDefender2.setImage(image);
          imgDefender2.getStyleClass().add("round-image");
          
      }    
       
      public void setDf3Image( String path){
          Image image= new Image(IMAGES_DIR + path);
          imgDefender3.setImage(image);
          imgDefender3.getStyleClass().add(".round-image");
          
      }  
      public void setDf4Image( String path){
          Image image= new Image(IMAGES_DIR + path);
          imgDefender4.setImage(image);
          imgDefender4.getStyleClass().add(".round-image");
          
      }  
         
      public void setMf1Image( String path){
          Image image= new Image(IMAGES_DIR + path);
          imgMiedfielder1.setImage(image);
          imgMiedfielder1.getStyleClass().add(".round-image");
          
      }  
          
          
      public void setMf2Image( String path){
          Image image= new Image(IMAGES_DIR + path);
          imgMiedfielder2.setImage(image);
          imgMiedfielder2.getStyleClass().add(".round-image");
          
      }  
          
      public void setMf3Image( String path){
          Image image= new Image(IMAGES_DIR + path);
          imgMiedfielder3.setImage(image);
          imgMiedfielder3.getStyleClass().add(".round-image");
          
      }  
         
          
      public void setAf1Image( String path){
          Image image= new Image(IMAGES_DIR + path);
          imgAttacker1.setImage(image);
          imgAttacker1.getStyleClass().add(".round-image");
          
      }  
          
          
      public void setAf2Image( String path){
          Image image= new Image(IMAGES_DIR + path);
          imgAttacker2.setImage(image);
          imgAttacker2.getStyleClass().add(".round-image");
          
      }  
          
      public void setAf3Image( String path){
          Image image= new Image(IMAGES_DIR + path);
          imgAttacker3.setImage(image);
          imgAttacker3.getStyleClass().add(".round-image");
          
      }  
      
      
          
      public void setTeamPointsLabel(double points){
              String teamPoints= Double.toString(points);
              lbTotalPoints.setText(teamPoints);
          }
          
      public void setTeamNameLabel(String teamName){
              lbSelectedTeamName.setText(teamName);
          }

      private void initTableCells() {
              tcTeamName.setCellValueFactory(new PropertyValueFactory<>("name"));
              tcTeamName.setCellFactory(TextFieldTableCell.forTableColumn());
              tcTeamName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Team, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Team, String> event) {                   
                Team team= tvTeams.getSelectionModel().getSelectedItem();
                team.setName(event.getNewValue());
            }
        });
              
              tcPoints.setCellValueFactory(new PropertyValueFactory<>("totalPoints"));
          }

        private void initObservables() {
        tvTeams.setItems(Repository.getTeams());
        Image image= new Image( IMAGES_DIR + "wclogotransp.jpg");
        imgIcon.setImage(image);
        }

    
      
          
         
}
