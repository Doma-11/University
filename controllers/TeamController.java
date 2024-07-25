/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import hr.algebra.engine.GameEngine;
import static hr.algebra.engine.GameEngine.getIndexOfCombobox;
import static hr.algebra.engine.GameEngine.getIndexOfPlayer;
import static hr.algebra.engine.GameEngine.getWinnerOfRound;
import static hr.algebra.engine.GameEngine.getIndexOfUser;
import hr.algebra.model.Game;
import hr.algebra.model.GameMove;

import hr.algebra.model.Player;
import hr.algebra.model.User;
import hr.algebra.repository.Repository;

import static hr.algebra.utils.DOMUtils.saveUsers;
import hr.algebra.utils.DOMUtilsGame;

import hr.algebra.utils.HtmlUtils;
import hr.algebra.utils.RandomPlayerUtils;
import hr.algebra.utils.SerializationUtils;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.xml.transform.TransformerException;

/**
 * FXML Controller class
 *
 * @author boric
 */
public class TeamController implements Initializable {

    @FXML
    private ComboBox<Player> cbGk;
    @FXML
    private ComboBox<Player> cbDefender1;
    @FXML
    private ComboBox<Player> cbDefender3;
    @FXML
    private ComboBox<Player> cbDefender2;
    @FXML
    private ComboBox<Player> cbDefender4;
    @FXML
    private ComboBox<Player> cbMidfielder2;
    @FXML
    private ComboBox<Player> cbMidfielder3;
    @FXML
    private ComboBox<Player> cbMidfielder1;
    @FXML
    private ComboBox<Player> cbAttacker2;
    @FXML
    private ComboBox<Player> cbAttacker1;
    @FXML
    private ComboBox<Player> cbAttacker3;
   
    @FXML
    private ImageView imgWcLogo;
    @FXML
    private Label lbUserName;
    @FXML
    private Label lbPointsUser;
    @FXML
    private Label lbUserPlayerRating;
    @FXML
    private Label lbPcPlayerRating;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnQuit;
    @FXML
    private Button btnReplay;
    @FXML
    private Label lbPCPlayerRating;
    @FXML
    private TextField tfGkPC;
    @FXML
    private TextField tfDefender1PC;
    @FXML
    private TextField tfDefender2PC;
    @FXML
    private TextField tfDefender3PC;
    @FXML
    private TextField tfDefender4PC;
    @FXML
    private TextField tfMidfielder1PC;
    @FXML
    private TextField tfMidfielder2PC;
    @FXML
    private TextField tfMidfielder3PC;
    @FXML
    private TextField tfAttacker1PC;
    @FXML
    private TextField tfAttacker2PC;
    @FXML
    private TextField tfAttacker3PC;
    @FXML
    private Label lbUserName1;
    @FXML
    private Label lbUserNamePC;
    @FXML
    private Label lbPointsPC;
    @FXML
    private Label lbFirstNamePlayerUser;
    @FXML
    private Label lbRoundWinner;
    @FXML
    private Label lbFirstNamePlayerPC;
    @FXML
    private Label lbLastNamePlayerPC;
    @FXML
    private Button btnFirstPlayerOnTable;
    @FXML
    private Button btnSecondPlayerOnTable;

    /**
     * Initializes the controller class.
     */
    
    private User lastWinner;
    private Game game;
  
    private GameMove gameMoveUser;
    private boolean isRadUTijeku= false;
    
    private boolean isReplayMoveClicked;
    
    int counter= 0;
    static double points;
    double sumUser= 0;
    double sumPC=0;
    
    
    private final long DELAY = 2000;
    private final int HOLD = 1000;
    
    private final String IMAGES_DIR = "/hr/algebra/resources/images/"; 
    private final int pictureWidth = 118;
    private final int pictureHeight = 158;
    private final String START_MESSAGE = "";
    private final String FILE_NAME = "game.ser";
    private final String DOCUMENTATION_FILE_NAME = "documentation.html";
    
  
    
    private ObservableList<TextField> playersPCTextfield;
    private ObservableList<ComboBox> playersUserCombobox;
    private ObservableList<Button> playersOntableButtons;  
    private ObservableList<Label> playersOnTableLabels;
    
    
    private ObservableList<User> users;
    private ObservableList<Player> playersinHand;
    private ObservableList<Player> opponentPlayersInHand;
    
    //****replay liste****
    private ObservableList<Player> playersInHandReplay;
    private ObservableList<Player> opponentPlayersInHandReplay;
    
    private ObservableList<Player> playersOntable;
    private ObservableList<ObservableList<Player>> ListofListPlayers;
    
    
    
    
    private boolean isGameActive = true;
    
    private Player replayPlayer=new Player();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initObservables();
        initUsersPlayersInHand();
        initGame();
        
        serializeGame();
    }    
    
    
   
     

    @FXML
    private void btnQuit_OnAction()  {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Exit game");

        users.get(0).setPlayersInHand(playersinHand);
        users.get(1).setPlayersInHand(opponentPlayersInHand);
        playersinHand = game.getUsers().get(0).getPlayersInHand();
        opponentPlayersInHand = game.getUsers().get(1).getPlayersInHand();
        System.out.println("Velicina nase liste koju saljemo: " + playersinHand.size());
        System.out.println("Velicina PC liste koju saljemo: " + opponentPlayersInHand.size());
        
        saveDOM();
        
        try(FileWriter htmlWriter = new FileWriter(DOCUMENTATION_FILE_NAME)) {
        htmlWriter.write(HtmlUtils.createDocumentation());
        //pokazi zadnji ekran sa rezultatima
        openResults();
        } catch (IOException ex) {
        Logger.getLogger(TeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void saveDOM() {

        try {
            Repository.getUsers().forEach(u -> System.out.println(u.getNickName()));
            System.out.println("Velicina PC liste savedom: " + game.getUsers().get(1).getPlayersInHand().size());
            Repository.addPlayerstoUserTeam(game.getUsers().get(0), playersinHand);
            saveUsers(Repository.getUsers());
            
        } catch (TransformerException ex) {
            Logger.getLogger(TeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // SINKRONIZACIJA
    
    
    //INICIJALIZACIJA SVEGA
    
    private void initObservables() {
        
        users= Repository.getUsers();
        playersOntableButtons = FXCollections.observableArrayList(btnFirstPlayerOnTable, btnSecondPlayerOnTable);
        playersUserCombobox= FXCollections.observableArrayList(cbGk,cbDefender1,cbDefender2,cbDefender3,cbDefender4,cbMidfielder1,cbMidfielder2,cbMidfielder3,cbAttacker1,cbAttacker2,cbAttacker3);
        playersPCTextfield=FXCollections.observableArrayList(tfGkPC,tfDefender1PC,tfDefender2PC,tfDefender3PC,tfDefender4PC,tfMidfielder1PC,tfMidfielder2PC,tfMidfielder3PC,tfAttacker1PC,tfAttacker2PC,tfAttacker3PC);
        playersOnTableLabels=FXCollections.observableArrayList(lbFirstNamePlayerUser,lbFirstNamePlayerPC,lbUserPlayerRating,lbPCPlayerRating,lbPointsUser,lbPointsPC);
        opponentPlayersInHand = FXCollections.observableArrayList();
        playersinHand = FXCollections.observableArrayList();
        opponentPlayersInHandReplay = FXCollections.observableArrayList();
        playersInHandReplay = FXCollections.observableArrayList();
        playersOntable = FXCollections.observableArrayList(null, null);
        ListofListPlayers= FXCollections.observableArrayList( Repository.getGoalkeepers(),
                Repository.getDefenders(),
                Repository.getDefenders(),
                Repository.getDefenders(),
                Repository.getDefenders(),
                Repository.getMidfielders(),
                Repository.getMidfielders(),
                Repository.getMidfielders(),
                Repository.getAttackers(),
                Repository.getAttackers(),
                Repository.getAttackers());
        
        
        // combobox user initialization
        cbGk.setVisibleRowCount(80);
        cbGk.setItems(ListofListPlayers.get(0));
        
        cbDefender1.setVisibleRowCount(80);
        cbDefender1.setItems(ListofListPlayers.get(1));
        
        cbDefender2.setVisibleRowCount(80);
        cbDefender2.setItems(ListofListPlayers.get(2));
        
        cbDefender3.setVisibleRowCount(80);
        cbDefender3.setItems(ListofListPlayers.get(3));
        
        cbDefender4.setVisibleRowCount(80);
        cbDefender4.setItems(ListofListPlayers.get(4));
        
        
        cbMidfielder1.setVisibleRowCount(80);
        cbMidfielder1.setItems(ListofListPlayers.get(5));
        
        cbMidfielder2.setVisibleRowCount(80);
        cbMidfielder2.setItems(ListofListPlayers.get(6));
        
        cbMidfielder3.setVisibleRowCount(80);
        cbMidfielder3.setItems(ListofListPlayers.get(7));
        
        cbAttacker1.setVisibleRowCount(80);
        cbAttacker1.setItems(ListofListPlayers.get(8));
        
        cbAttacker2.setVisibleRowCount(80);
        cbAttacker2.setItems(ListofListPlayers.get(9));
        
        cbAttacker3.setVisibleRowCount(80);
        cbAttacker3.setItems(ListofListPlayers.get(10));
        
        // combobox PC initialization
                 
        Image image= new Image( IMAGES_DIR + "wclogotransp.png");
        imgWcLogo.setImage(image);
    }
    
     @FXML
    private void btnReset_OnAction(ActionEvent event) {
        
        lbPointsUser.setText("");
        lbPointsPC.setText("");
        lbRoundWinner.setText("");
        clearUsers();
        clearTable();
        clearObservables();
        

        
        List<GameMove> gameMoveList = DOMUtilsGame.readGameMovesFromXmlFile();

        AtomicInteger counter = new AtomicInteger(0);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            GameMove newGameMove = gameMoveList.get(counter.get());

            String firstNameGameMove= newGameMove.getFirstName();
            String lastNameGameMove= newGameMove.getLastName();
            String ratingGameMove = newGameMove.getRating();
            String picPathGameMove= newGameMove.getPicturePath();
            String chosencbGameMove = newGameMove.getChosenComboboxIndex();
            
            System.out.println("dohvacen REPLAY GAME MOVE!!!!");
            
            double playerRating= Double.parseDouble(ratingGameMove);
            //-------------------------------------//
            // Initialize your Player object
           
            System.out.println("ISPIS REPLAY GAME MOVE!!!!!");
            System.out.println(firstNameGameMove);
            System.out.println(lastNameGameMove);
            System.out.println(ratingGameMove);
            
            
           Player player = new Player(firstNameGameMove,lastNameGameMove,playerRating,picPathGameMove);
           System.out.println("ISPIS REPLAY IGRACA!!!!!");
           System.out.println(player.getFirstName());
           System.out.println(player.getLastName());
           System.out.println(player.getRating());
               
            switch(chosencbGameMove){
                   case "cbGk":
                // getamo prvi combobox , znaci 0 je index, stavljamo playera na 0ti index
                   cbGk.setValue(player);
                   handleUser(player);
                   break;            
                   case "cbDefender1":
                   cbDefender1.setValue(player);
                   handleUser(player);
                   break;
                   case "cbDefender2":
                   cbDefender2.setValue(player);
                   handleUser(player);
                   break;
                   case "cbDefender3":
                   cbDefender3.setValue(player); 
                   handleUser(player);
                   break;
                   case "cbDefender4":
                   cbDefender4.setValue(player);
                   handleUser(player);
                   break;
                   case "cbMidfielder1":
                   cbMidfielder1.setValue(player);
                   handleUser(player);
                   break;
                   case "cbMidfielder2":
                   cbMidfielder2.setValue(player);
                   handleUser(player);
                   break;
                   case "cbMidfielder3":
                   cbMidfielder3.setValue(player);
                   handleUser(player);
                   break;
                   case "cbAttacker1":
                   cbAttacker1.setValue(player);
                   handleUser(player);
                   break;
                   case "cbAttacker2":
                   cbAttacker2.setValue(player);
                   handleUser(player);
                   break;
                   case "cbAttacker3":
                   cbAttacker3.setValue(player);
                   handleUser(player);
                   break;
                        
            }
            counter.set(counter.get() + 1);
           
            
        }));
        
        timeline.setCycleCount(gameMoveList.size());
        timeline.playFromStart();
        
    }
    
      private synchronized void handleUser(Player player) {
          
       isRadUTijeku=false;
       int indexOfUser=0;
       playersOntable.add(indexOfUser,player);
       //////////////////////////////
       playersOntable.remove((indexOfUser +1));
       users.get(0).setPlayersInHand(playersinHand);
       
        // dodaj na playere na stolu tog playera
        playersOntable.add(indexOfUser,player);
        System.out.println("ISPIS U REPLAY MODE"+ playersOntable.get(indexOfUser).getFirstName());
       
        //zamijeni playera novim
        //playersOntable.remove((indexOfUser +1));
        System.out.println("Velicina liste playera na stolu REPLAY MODE: "+playersOntable.size());
        
        // stavi sliku i labele odabranog playera
         handlePicture(playersOntableButtons.get(indexOfUser), playersOntable.get(indexOfUser).getPicturePath());
         handlePlayersonTableNameLabel(playersOnTableLabels.get(indexOfUser),playersOntable.get(indexOfUser).getFirstName(),playersOntable.get(indexOfUser).getLastName() );
         handlePlayersonTableRatingLabel(playersOnTableLabels.get(2),playersOntable.get(indexOfUser).getRating());       
         handleTextField(playersPCTextfield.get(0),playersOntable.get(indexOfUser).getFirstName(),playersOntable.get(indexOfUser).getLastName());
         
         isRadUTijeku=true;
         notifyAll();
    }
    
    
     @FXML
    private void btnReplayMove_onAction(ActionEvent event) {

        isReplayMoveClicked = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                
        alert.setTitle("XML");
        alert.setHeaderText("Make XML:");
        
        saveDOM();
        alert.setContentText("You sure you want to make XML?");
        alert.setContentText("DOCUMENTATION CREATED!");
        if (alert.showAndWait().get() == ButtonType.OK) {
            
            Stage stage = (Stage) btnQuit.getScene().getWindow();
            stage.close();
            
            // spremanje dokumentacije
            try (FileWriter htmlWriter = new FileWriter(DOCUMENTATION_FILE_NAME)) {
            htmlWriter.write(HtmlUtils.createDocumentation());
            } catch (IOException ex) {
            Logger.getLogger(TeamController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
        
         
    }
    //INIT GAME
    private void initGame(){
        initUsers();
        initUsersPlayersInHand();
        disableAllCombobox();
        startGame();
        
        
        game = new Game(users, lastWinner,playersOntable);
        
        

               
                
    }
    private void initUsers() {
        lbUserName.setText(users.get(0).getNickName());
        lbUserNamePC.setText(users.get(1).getNickName());
        
        lastWinner= users.get(0);
    }
    private void initUsersPlayersInHand() {
        users.get(0).setPlayersInHand(playersinHand);
        users.get(1).setPlayersInHand(opponentPlayersInHand);

    }

    @FXML
    private void comboBoxSelectAction(ActionEvent event) throws InterruptedException {
        
        disableAllCombobox();
       
        ComboBox combobox=(ComboBox) event.getTarget();
        int indexcb = getIndexOfCombobox(combobox, playersUserCombobox);
        
        int indexList=indexcb;
        
        handlePlayerGoesFirstMoveThread( indexcb, playersinHand, 0);
          
    }
    
    
    
    
    
    private void disableAllCombobox() {
       cbGk.setDisable(true);
       cbDefender1.setDisable(true);
       cbDefender2.setDisable(true);
       cbDefender3.setDisable(true);
       cbDefender4.setDisable(true);
       cbMidfielder1.setDisable(true);
       cbMidfielder2.setDisable(true);
       cbMidfielder3.setDisable(true); 
       cbAttacker1.setDisable(true);
       cbAttacker2.setDisable(true);
       cbAttacker3.setDisable(true); 
               
    }
    
       private void enableAllCombobox() {
       cbGk.setDisable(false);
       cbDefender1.setDisable(false);
       cbDefender2.setDisable(false);
       cbDefender3.setDisable(false);
       cbDefender4.setDisable(false);
       cbMidfielder1.setDisable(false);
       cbMidfielder2.setDisable(false);
       cbMidfielder3.setDisable(false); 
       cbAttacker1.setDisable(false);
       cbAttacker2.setDisable(false);
       cbAttacker3.setDisable(false); 
               
    }
       
     


    private void startGame() {
        //resetScore();
        enableUserCombobox();
        btnReplay.setDisable(false);
        btnReset.setDisable(false);
        isReplayMoveClicked = false;
    }

    private void resetScore() {
        lbPointsUser.setText(String.valueOf(users.get(0).getPoints()));
        lbPointsPC.setText(String.valueOf(users.get(1).getPoints()));
    }
    
    private void enableUserCombobox() {
        
        for (int i = 0; i < playersUserCombobox.size(); i++) {
           playersUserCombobox.get(i).setDisable(false);
           
        }
    }
    
   
    private void clearGame(){
        clearUsers();
        clearPlayersInHand();
        clearTable();
        clearObservables();
        disableAllCombobox();
        initGame();
    }
   

    private void clearUsers() {
        // mozemo napraviti dvije nove liste koje sluze za replay, a ove dvije izbrisati koje imamo
        for (User user: users) {
            user.setPoints(0);
           
            user.setPlayersInHand(null);
           

        }
    }

    private void clearPlayersInHand() {
        playersinHand.clear();
        opponentPlayersInHand.clear();
    }

     private void clearButton(Button btn) {
        btn.setGraphic(null);
    }
     
    
     private void clearTable() {
       playersOntable.clear();
       playersOntable.add(null);
       playersOntable.add(null);
       playersOntableButtons.forEach(btn -> btn.setGraphic(null));
       lbFirstNamePlayerUser.setText("");
       lbFirstNamePlayerPC.setText("");
       lbUserPlayerRating.setText("");
       lbPCPlayerRating.setText("");
       
    }
    
    private void clearObservables() {
        
        cbGk.setValue(null);
        cbDefender1.setValue(null);
        cbDefender2.setValue(null);
        cbDefender3.setValue(null);
        cbDefender4.setValue(null);
        cbMidfielder1.setValue(null);
        cbMidfielder2.setValue(null);
        cbMidfielder3.setValue(null);
        cbAttacker1.setValue(null);
        cbAttacker2.setValue(null);
        cbAttacker3.setValue(null);
        
         //////////////////////////////////////////////////////////////
         
          playersPCTextfield.get(0).setText("");
          playersPCTextfield.get(1).setText("");
          playersPCTextfield.get(2).setText("");
          playersPCTextfield.get(3).setText("");
          playersPCTextfield.get(4).setText("");
          playersPCTextfield.get(5).setText("");
          playersPCTextfield.get(6).setText("");
          playersPCTextfield.get(7).setText("");
          playersPCTextfield.get(8).setText("");
          playersPCTextfield.get(9).setText("");
          playersPCTextfield.get(10).setText("");
          playersPCTextfield.get(0).setText("");
        
    }

    
   

    private void handlePlayerGoesFirstMoveThread( int index, ObservableList<Player> players, int i) {
       new Thread() {
            @Override
            public void run() {
                // Exit the thread if the game has ended
                
          
                Platform.runLater(() -> {
                    handleMove(index, playersinHand, i);
                });
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }

                if(playersOntable.get(0)!= null){
                Platform.runLater(() -> {

                    if (!isReplayMoveClicked) {
                        btnReplay.setDisable(true);
                        try {
                            handleCompMove(index, opponentPlayersInHand, i);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TeamController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                
                if (!isReplayMoveClicked) {

                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                Platform.runLater(() -> {

                    if (!isReplayMoveClicked) {
                        btnReplay.setDisable(true);
                        handleWinner();
                    }
                });
                }
                else{
                    isGameActive=false;
                    enableUserCombobox();
                    counter=0;
                }
                    if (!isGameActive) {
                           return;
                    }
            }
            
            
        
        }.start();
       
       
    }

    
   
    private void handleMoveReplay(int index, Player selectedPlayer, ObservableList<Player> playersinHand, int i) {
           playersinHand.add(selectedPlayer);
           playersOntable.add(i,selectedPlayer);
           playersOntable.remove((i +1));
           handlePicture(playersOntableButtons.get(i), playersOntable.get(i).getPicturePath());
           handlePlayersonTableNameLabel(playersOnTableLabels.get(1),playersOntable.get(i).getFirstName(),playersOntable.get(i).getLastName() );
           handlePlayersonTableRatingLabel(playersOnTableLabels.get(3),playersOntable.get(i).getRating());
           handleTextField(playersPCTextfield.get(i),playersOntable.get(i).getFirstName(),playersOntable.get(i).getLastName());
            
     }
    
     private void handleCompMoveReplay(int index, ObservableList<Player> opponentPlayersInHand, int i) {
           ObservableList<Player> innerList= ListofListPlayers.get(index);
           // izvlacimo random playera iz liste
           Player randomPlayer= RandomPlayerUtils.getRandomPlayer(innerList);
           
           // izvlacimo index tog playera
           int indexofPlayer= getIndexOfPlayer(randomPlayer, innerList);
           opponentPlayersInHand.add(randomPlayer);
           
           i=1;
           
           playersOntable.add(i,randomPlayer);
           System.out.println("Ispis trenutno odabranog igraca"+ playersOntable.get(i).getFirstName());
           
           playersOntable.remove((i +1));
           
           handlePicture(playersOntableButtons.get(i), playersOntable.get(i).getPicturePath());
           handlePlayersonTableNameLabel(playersOnTableLabels.get(1),playersOntable.get(i).getFirstName(),playersOntable.get(i).getLastName() );
           handlePlayersonTableRatingLabel(playersOnTableLabels.get(3),playersOntable.get(i).getRating());
           handleTextField(playersPCTextfield.get(i),playersOntable.get(i).getFirstName(),playersOntable.get(i).getLastName());
           
    }


    
    private void handleMove(int indexcb, ObservableList<Player> playersInHand,int indexOfUser){
        
        Player newPlayer= (Player) playersUserCombobox.get(indexcb).getSelectionModel().getSelectedItem();
        newPlayer.setIsSelected(true);
        
        
        //------------------------------------//*
        String name= newPlayer.getFirstName();
        String lastName= newPlayer.getLastName();
        Double ratingPlayer= newPlayer.getRating();
        String localRating= ratingPlayer.toString();
        String picPath= newPlayer.getPicturePath();
        
        
        String chosenComboboxIndex= playersUserCombobox.get(indexcb).getId();
        
        GameMove gameMove= new GameMove(name,lastName,localRating,picPath,chosenComboboxIndex);
        
        DOMUtilsGame.saveGameMoveToXml(gameMove);
        
       
        //----------------------------------------//
        
         // dodao playera u listu svojih odabranih playera
        System.out.println("Dodajem playera u myPlayersInHand listu: " + newPlayer.getFirstName() + newPlayer.getLastName());
        playersInHand.add(newPlayer);
        users.get(0).setPlayersInHand(playersInHand);
        playersInHandReplay= users.get(0).getPlayersInHand();
        System.out.println("Velicina mojeREPLAY liste: " +playersInHandReplay.size());
        
        // dodaj na playere na stolu tog playera
        playersOntable.add(indexOfUser,newPlayer);
        System.out.println("Ispis trenutno odabranog igraca"+ playersOntable.get(indexOfUser).getFirstName());
        
        //zamijeni playera novim
        playersOntable.remove((indexOfUser +1));
        System.out.println("Velicina liste playera na stolu:"+playersOntable.size());
        
        // stavi sliku i labele odabranog playera
         handlePicture(playersOntableButtons.get(indexOfUser), playersOntable.get(indexOfUser).getPicturePath());
         handlePlayersonTableNameLabel(playersOnTableLabels.get(indexOfUser),playersOntable.get(indexOfUser).getFirstName(),playersOntable.get(indexOfUser).getLastName() );
         handlePlayersonTableRatingLabel(playersOnTableLabels.get(2),playersOntable.get(indexOfUser).getRating()); 
            
        
       
    }

     private synchronized void handleCompMove(int indexofList,ObservableList<Player> opponentPlayersInHand,int indexOfUser) throws InterruptedException{
           ObservableList<Player> innerList= ListofListPlayers.get(indexofList);
           Player randomPlayer= RandomPlayerUtils.getRandomPlayer(innerList);
           
           while(isRadUTijeku=false){
               wait();
           }
           
           int indexofPlayer= getIndexOfPlayer(randomPlayer, innerList);
           opponentPlayersInHand.add(randomPlayer);
           users.get(1).setPlayersInHand(opponentPlayersInHand);
            opponentPlayersInHandReplay= users.get(1).getPlayersInHand();
            System.out.println("velicina PCREPLAY liste" + opponentPlayersInHandReplay );
           // postavljamo index usera =1
           indexOfUser=1;
           // dodajemo playera na index usera
           playersOntable.add(indexOfUser,randomPlayer);
           System.out.println("Ispis trenutno odabranog igraca"+ playersOntable.get(indexOfUser).getFirstName());
           
           playersOntable.remove((indexOfUser +1));
           
           System.out.println("Velicina liste playera na stolu:"+playersOntable.size());
           
           handlePicture(playersOntableButtons.get(indexOfUser), playersOntable.get(indexOfUser).getPicturePath());
           handlePlayersonTableNameLabel(playersOnTableLabels.get(1),playersOntable.get(indexOfUser).getFirstName(),playersOntable.get(indexOfUser).getLastName() );
           handlePlayersonTableRatingLabel(playersOnTableLabels.get(3),playersOntable.get(indexOfUser).getRating());
           handleTextField(playersPCTextfield.get(indexofList),playersOntable.get(indexOfUser).getFirstName(),playersOntable.get(indexOfUser).getLastName());
           
           System.out.println("PROSAO KROZ SVE METODE");
           
           
           notifyAll();
       }

    private void handlePlayersonTableNameLabel(Label label, String firstName, String lastName) {
        label.setText(firstName + " " + lastName);
    }

    private void handlePlayersonTableRatingLabel(Label label, Double rating) {
        label.setText(rating.toString());
    }

    private void handleTextField(TextField textfield, String firstName, String lastName) {
        textfield.setText(firstName + " " + lastName);
    }
     private void handlePicture(Button btn, String cardPicturePath) {

        try {

            Image img = new Image(IMAGES_DIR + cardPicturePath);
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(pictureHeight);
            imgView.setFitWidth(pictureWidth);
            btn.setGraphic(imgView);

        } catch (Exception e) {
            throw new RuntimeException("Something went wrong with images");
        }
        

    }


    private void handleWinner() {
               
        User winner = getWinnerOfRound(users, playersOntable, lastWinner);
        lastWinner = winner;
        lbRoundWinner.setText(lastWinner.getNickName().toString());
        // trebamo userima setirati bodove
        
        if( winner == Repository.getUsers().get(0)){
            Repository.addPointsToUser(winner, GameEngine.getPointFromMyPlayerListPlayer1(playersOntable));
            
            double pointsUser= Repository.getUsers().get(0).getPoints();
            lbPointsUser.setText(String.valueOf(String.format("%.2f",pointsUser)));
         
        }else{
            Repository.addPointsToUser(winner, GameEngine.getPointFromMyPlayerListPlayer2(playersOntable));
            double pointsPC= Repository.getUsers().get(1).getPoints();
            lbPointsPC.setText(String.valueOf(String.format("%.2f",pointsPC)));
            
           
        }
        
        
        clearTable();
        startGame();
        System.out.println("stigli do petlje winnera");
        game.setPlayedFirst(lastWinner);
        
        counter++;
        System.out.println("1.STANJE COUNTERA: " + counter);
        serializeGame();
        if (counter!=11) {
            startNewRound(0);
        }
        else{
             isGameActive = false;
            endGame();
        }
    
    }

    private void endGame(){
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("THE END");
        
        
        
        if(Repository.getUsers().get(0).getPoints()>Repository.getUsers().get(1).getPoints()){
            alert.setHeaderText("Winner of WC2022 GAME:" + users.get(0).getNickName()+ ": " + String.format("%.2f",Repository.getUsers().get(0).getPoints()) +" > "+ users.get(1).getNickName() + ": " + String.format("%.2f", Repository.getUsers().get(1).getPoints()));
        }
        else{
            alert.setHeaderText("Winner of WC2022 GAME:" + users.get(1).getNickName()+ ": " + String.format("%.2f",Repository.getUsers().get(1).getPoints()) +" > "+ users.get(0).getNickName() + ": " + String.format("%.2f",Repository.getUsers().get(0).getPoints()));
        }
        
         saveDOM();
        
            try (FileWriter htmlWriter = new FileWriter(DOCUMENTATION_FILE_NAME)) {
            htmlWriter.write(HtmlUtils.createDocumentation());
            } catch (IOException ex) {
            Logger.getLogger(TeamController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
      
        // TREBAM NAPRAVITI WINNER IGRE
        alert.setContentText("Do you want to play new game?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            lbPointsUser.setText("");
            lbPointsPC.setText("");
            lbRoundWinner.setText("");
            clearUsers();
            clearTable();
            clearObservables();
            
             saveDOM();
        
            try (FileWriter htmlWriter = new FileWriter(DOCUMENTATION_FILE_NAME)) {
            htmlWriter.write(HtmlUtils.createDocumentation());
            } catch (IOException ex) {
            Logger.getLogger(TeamController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            
            clearGame();
            counter =0;
             System.out.println("2 STANJE COUNTERA-ZAVRSILI GAME: " + counter);
        }  else {
            btnQuit_OnAction();
        }
    }
    
    
      private void serializeGame() {

        try {
            SerializationUtils.write(game, FILE_NAME);

        } catch (IOException ex) {
            Logger.getLogger(TeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
       private void deserializeGame() {
        try {
            game = (Game) SerializationUtils.read(FILE_NAME);
            
            users = game.getUsers();
            playersinHand = game.getUsers().get(0).getPlayersInHand();
            opponentPlayersInHand = game.getUsers().get(1).getPlayersInHand();

        } catch (Exception ex) {
            Logger.getLogger(TeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startNewRound(int indexOfWinner) {
        if (indexOfWinner==0) {
            enableAllCombobox();
            System.out.println("ZAPOCINJEMO NOVU RUNDU!");
        }
        else{
            //handleCompMove(index, opponentPlayersInHand, i);
            enableAllCombobox();
        }
    }

    private void openResults() throws IOException {
        Stage singleplayer = (Stage) btnQuit.getScene().getWindow();
        singleplayer.close();

        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/views/Results.fxml"));
        Stage stage = new Stage();
        stage.setTitle("WC2022-RESULTS");
        stage.setScene(new Scene(root, 700, 500));
        stage.setResizable(false);
        stage.show();
    }

   

   

   

}
