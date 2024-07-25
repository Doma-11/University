/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import hr.algebra.engine.GameEngine;
import static hr.algebra.engine.GameEngine.getIndexOfCombobox;
import static hr.algebra.engine.GameEngine.getIndexOfPlayer;
import static hr.algebra.engine.GameEngine.getWinnerOfRoundMultiplayer;
import hr.algebra.model.Game;
import hr.algebra.model.Player;
import hr.algebra.model.User;
import hr.algebra.networking.TCPClient;
import hr.algebra.networking.TCPClientThread;
import hr.algebra.networking.UDPClient;
import hr.algebra.networking.UDPClientThread;
import hr.algebra.repository.Repository;
import hr.algebra.service.MessengerService;
import hr.algebra.utils.MessageUtils;
import hr.algebra.utils.SerializationUtils;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author boric
 */
public class TeamMultiplayerController implements Initializable {

    
    @FXML
    private Label lbUserName1;
    @FXML
    private Label lbUserName2;
    @FXML
    private Label lbPointsUser;
     @FXML
    private Label lbPointsUser2;
    @FXML
    private ComboBox<Player> cbGk;
    @FXML
    private ComboBox<Player> cbDefender1;
    @FXML
    private ComboBox<Player> cbDefender2;
    @FXML
    private ComboBox<Player> cbDefender3;
    @FXML
    private ComboBox<Player> cbDefender4;
    @FXML
    private ComboBox<Player> cbMidfielder1;
    @FXML
    private ComboBox<Player> cbMidfielder2;
    @FXML
    private ComboBox<Player> cbMidfielder3;
    @FXML
    private ComboBox<Player> cbAttacker1;
    @FXML
    private ComboBox<Player> cbAttacker2;
    @FXML
    private ComboBox<Player> cbAttacker3;
    @FXML
    private Label lbLastNamePlayerPC;
    @FXML
    private Label lbUserPlayerRating;
    
    @FXML
    private ImageView imgWcLogo;
    
    @FXML
    private Button btnQuit;
    @FXML
    private Button btnFirstPlayerOnTable;
   
    @FXML
    private Label lbFirstNamePlayerUser;
    @FXML
    private Button btnSecondPlayerOnTable;
    @FXML
    private Label lbFirstNamePlayerPC;
    @FXML
    private Label lbPCPlayerRating;
    @FXML
    private Label lbRoundWinner;
    @FXML
    private Label lbRoundWinnerUser2;
    @FXML
    private TextField tfMessage;
    @FXML
    private ScrollPane spMessageContainer;
    @FXML
    private Button btnSendMessage;
    
    private MessengerService messengerService = null;

    private static final int MESSAGE_LENGTH = 30;
    private static final int FONT_SIZE = 15;
    private final String TIME_FORMAT = "HH:mm:ss";
    private static final String MESSAGE_FORMAT = "%s (%s): %s";

    
    private ObservableList<Node> messages;
    @FXML
    private VBox vbMessages;
    
    
    
     
    private final long DELAY = 2000;
    private final int HOLD = 1000;
    private final int pictureWidth = 118;
    private final int pictureHeight = 158;
    private final String IMAGES_DIR = "/hr/algebra/resources/images/";
    private final String FILE_NAME = "multiplayergame.ser";
    
    
    private final boolean isThisPlayerFirstToPlay = Repository.getFirstToPlay().equals(Repository.getThisUser());

    private User lastWinner;
    private Game game;
    int counter = 0;
    double sumUser=0.0;
    double sumUser2=0.0;
    private ObservableList<ComboBox> playersUserCombobox;
    private ObservableList<Button> playersOntableButtons;  
    private ObservableList<Label> playersOnTableLabels;
    
    
    private ObservableList<User> users;
    private ObservableList<Player> myPlayersInHand;
    private ObservableList<Player> opponentPlayersInHand;
    private ObservableList<Player> playersOntable;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //prestani slati svoj nickname
        TCPClientThread.NICKNAME_FLAG = false;
        UDPClientThread.flag = false;
        
        initUDPClientThread();

        initObservables();
        initUsersPlayersInHand();
        initUsers();
        initChat();
        
        if (isThisPlayerFirstToPlay) {

            initGame();
            serializeGame();
        }
    }    

    @FXML
    private void comboBoxSelectAction(ActionEvent event) {
        ComboBox combobox=(ComboBox) event.getTarget();
        int indexcb = getIndexOfCombobox(combobox, playersUserCombobox);
        
         if (isThisPlayerFirstToPlay) {

            handleMove( indexcb, myPlayersInHand, 0);

        } else {

            handleMoveUser2( indexcb, opponentPlayersInHand, 1);
        }
         
         for (Player player : playersOntable) {
             if (player!= null) {
                 System.out.println(player.getPicturePath());
             }
        }
         
         serializeGame();
    }


   
    @FXML
    private void btnQuit_OnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Exit game");

        alert.setContentText("You sure you want to quit?");
        if (alert.showAndWait().get() == ButtonType.OK) {

            Repository.setGameObject(null);
            TCPClient tcpClient = new TCPClient();
            tcpClient.setDaemon(true);
            tcpClient.start();
            exitGame();
        }
    }

   

    private void initObservables() {
        users= Repository.getUsers();
        playersOntableButtons = FXCollections.observableArrayList(btnFirstPlayerOnTable, btnSecondPlayerOnTable);
        playersUserCombobox= FXCollections.observableArrayList(cbGk,cbDefender1,cbDefender2,cbDefender3,cbDefender4,cbMidfielder1,cbMidfielder2,cbMidfielder3,cbAttacker1,cbAttacker2,cbAttacker3);
        playersOnTableLabels=FXCollections.observableArrayList(lbFirstNamePlayerUser,lbFirstNamePlayerPC,lbUserPlayerRating,lbPCPlayerRating,lbPointsUser,lbPointsUser2);
        
        opponentPlayersInHand = FXCollections.observableArrayList();
        myPlayersInHand = FXCollections.observableArrayList();
        playersOntable = FXCollections.observableArrayList(null, null);
        
        // combobox user initialization
        cbGk.setVisibleRowCount(80);
        cbGk.setItems(Repository.getGoalkeepers());
        
        cbDefender1.setVisibleRowCount(80);
        cbDefender1.setItems(Repository.getDefenders());
        
        cbDefender2.setVisibleRowCount(80);
        cbDefender2.setItems(Repository.getDefenders());
        
        cbDefender3.setVisibleRowCount(80);
        cbDefender3.setItems(Repository.getDefenders());
        
        cbDefender4.setVisibleRowCount(80);
        cbDefender4.setItems(Repository.getDefenders());
        
        
        cbMidfielder1.setVisibleRowCount(80);
        cbMidfielder1.setItems( Repository.getMidfielders());
        
        cbMidfielder2.setVisibleRowCount(80);
        cbMidfielder2.setItems( Repository.getMidfielders());
        
        cbMidfielder3.setVisibleRowCount(80);
        cbMidfielder3.setItems(Repository.getMidfielders());
        
        cbAttacker1.setVisibleRowCount(80);
        cbAttacker1.setItems( Repository.getAttackers());
        
        cbAttacker2.setVisibleRowCount(80);
        cbAttacker2.setItems( Repository.getAttackers());
        
        cbAttacker3.setVisibleRowCount(80);
        cbAttacker3.setItems(Repository.getAttackers());
        
        
        Image image= new Image( IMAGES_DIR + "wclogotransp.png");
        imgWcLogo.setImage(image);
    }

   
    private void initGame() {
      initLastWinner();
      disableAllCombobox();
      startGame();
      
      game = new Game(users, lastWinner,playersOntable);
    }
    
     private void initUsers() {
       lbUserName1.setText(users.get(0).getNickName());
       lbUserName2.setText(users.get(1).getNickName());
    }

    private void initUsersPlayersInHand() {
        users.get(0).setPlayersInHand(myPlayersInHand);
        users.get(1).setPlayersInHand(opponentPlayersInHand);

    }

    
     private void initLastWinner() {
        //prvi inicijalizira igru pa ju salje drugome, a drugi je prvi na redu
        lastWinner = users.get(1);
    }

    

    private void startGame() {
        setScore();
        enableUserCombobox();
       
    }

    private void setScore() {
        lbPointsUser.setText(String.valueOf(String.format("%.2f",users.get(0).getPoints())));
        lbPointsUser2.setText(String.valueOf(String.format("%.2f",users.get(1).getPoints())));
    }
    
    private void clearGame(){
        clearUsers();
        clearPlayersInHand();
        clearTable();
        initGame();
    }

    private void clearUsers() {
        for (User user: users) {
            user.setPoints(0);
            user.setPlayersInHand(null);
        }
    }

    private void clearPlayersInHand() {
        myPlayersInHand.clear();
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
       lbRoundWinner.setText("");
       lbRoundWinnerUser2.setText("");
       
    }
      //pocistio sve na ekranu comboboxove i textfieldove
    private void clearObservables() {
       //opponentPlayersInHand.clear();
       //myPlayersInHand.clear();
       lbFirstNamePlayerUser.setText("");
       lbFirstNamePlayerPC.setText("");
       lbUserPlayerRating.setText("");
       lbPCPlayerRating.setText("");
        for (int i = 0; i < playersUserCombobox.size(); i++) {
            playersUserCombobox.get(i).getSelectionModel().clearSelection(); 
        }
        
       enableUserCombobox();
       
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

    
    private void enableUserCombobox() {
        
    for (int i = 0; i < playersUserCombobox.size(); i++) {
           playersUserCombobox.get(i).setDisable(false);
           
        }
    
    }

    private void handleMove(int indexcb, ObservableList<Player> players,int indexOfUser) {
        Player newPlayer= (Player) playersUserCombobox.get(indexcb).getSelectionModel().getSelectedItem();
        // dodao playera u listu svojih odabranih playera
        
        // dodaj na playere na stolu tog playera
        playersOntable.add(indexOfUser,newPlayer);
       
        System.out.println("Ispis trenutno odabranog igraca"+ playersOntable.get(indexOfUser).getFirstName());
        //zamijeni playera novim
        playersOntable.remove((indexOfUser +1));
        System.out.println("Velicina liste playera na stolu:"+playersOntable.size());
        
        
        
        
        /*
        double playerRating= newPlayer.getRating();
        sumUser+= playerRating;
        String pointsString= Double.toString(sumUser);
        System.out.println("Ispis trenutnog broja bodova igraca"+pointsString);
        lbPointsUser.setText(pointsString);
        */
        // stavi sliku i labele odabranog playera
         handlePicture(playersOntableButtons.get(indexOfUser), playersOntable.get(indexOfUser).getPicturePath());
         handlePlayersonTableNameLabel(playersOnTableLabels.get(indexOfUser),playersOntable.get(indexOfUser).getFirstName(),playersOntable.get(indexOfUser).getLastName() );
         handlePlayersonTableRatingLabel(playersOnTableLabels.get(2),playersOntable.get(indexOfUser).getRating());
    }
    
     private void handleMoveUser2(int indexcb, ObservableList<Player> playerz,int indexOfUser) {
        Player newPlayer= (Player) playersUserCombobox.get(indexcb).getSelectionModel().getSelectedItem();
        
        
        // dodaj na playere na stolu tog playera
        playersOntable.add(indexOfUser,newPlayer);
        
        System.out.println("Velicina liste playera na stolu:"+playersOntable.size());
       
        System.out.println("Ispis trenutno odabranog igraca"+ playersOntable.get(indexOfUser).getFirstName());
        //zamijeni playera novim
        playersOntable.remove((indexOfUser +1));
        
        /*
        double playerRating= newPlayer.getRating();
        sumUser2+= playerRating;
        String pointsString= Double.toString(sumUser2);
        System.out.println(pointsString);
        lbPointsUser2.setText(pointsString);
      */
        // stavi sliku i labele odabranog playera
         handlePicture(playersOntableButtons.get(indexOfUser), playersOntable.get(indexOfUser).getPicturePath());
         handlePlayersonTableNameLabel(playersOnTableLabels.get(indexOfUser),playersOntable.get(indexOfUser).getFirstName(),playersOntable.get(indexOfUser).getLastName() );
         handlePlayersonTableRatingLabel(playersOnTableLabels.get(3),playersOntable.get(indexOfUser).getRating());
    }
    
     private void handlePlayersonTableNameLabel(Label label, String firstName, String lastName) {
        label.setText(firstName + " " + lastName);
    }

    private void handlePlayersonTableRatingLabel(Label label, Double rating) {
        label.setText(rating.toString());
    }

    private void handleWinner(){
        //dobijem index pobijednikove karte na stolu
        int indexOfWinnersPlayer = getWinnerOfRoundMultiplayer(users, playersOntable, lastWinner);
        //1 case
        if (indexOfWinnersPlayer == 0 && Repository.getThisUser().getNickName().equals(Repository.getFirstToPlay().getNickName())) {
            //ako udje tu ti si onaj koji je inicijalizirao igru i uzeo partiju
            //ti si sada lastwinner i zapocinjes novu partiju i igras prvi
            System.out.println("USLI U CASE 1: INCIJALIZIRALI I DOBILI PARTIJU");
            lastWinner = users.get(0);
            Repository.addPointsToUser(users.get(0),  GameEngine.getPointFromMyPlayerListPlayer1(playersOntable));
            
            
            
            System.out.println("Pobijedila ana! (User2)");
            lbRoundWinner.setText(lastWinner.getNickName());
            lbRoundWinnerUser2.setText(lastWinner.getNickName());
        }
        //2 case
        if (indexOfWinnersPlayer == 1 && Repository.getThisUser().getNickName().equals(Repository.getFirstToPlay().getNickName())) {
            //ako udje tu ti si onaj koji je inicijalizirao igru i izgubio partiju
            //drugi igrac je sada lstwinner i on igra prvi
            //postavi mu bodove i karte u deck
             System.out.println("USLI U CASE 2: INCIJALIZIRALI I IZGUBILI PARTIJU");
            lastWinner = users.get(1);
            Repository.addPointsToUser(users.get(1), GameEngine.getPointFromMyPlayerListPlayer1(playersOntable));
            
            System.out.println("Pobijedio je Domagoj! (User1)");
            lbRoundWinner.setText(lastWinner.getNickName().toString());
            lbRoundWinnerUser2.setText(lastWinner.getNickName());
        }
        if (indexOfWinnersPlayer == 0 && !Repository.getThisUser().getNickName().equals(Repository.getFirstToPlay().getNickName())) {
            //ti nisi inicijalizirao igru i izgubio si partiju
            //postavi drugog igraca za lastwinnera, dodijeli mu bodove 
            System.out.println("USLI U CASE 3: NISMO INCIJALIZIRALI I IZGUBILI PARTIJU");
            lastWinner = users.get(1);
            Repository.addPointsToUser(users.get(1), GameEngine.getPointFromMyPlayerListPlayer1(playersOntable));
            
            System.out.println("Pobijedio je Domagoj! (User1)");
            lbRoundWinner.setText(lastWinner.getNickName().toString());
            lbRoundWinnerUser2.setText(lastWinner.getNickName());
        }
         if (indexOfWinnersPlayer== 1 && !Repository.getThisUser().getNickName().equals(Repository.getFirstToPlay().getNickName())) {
            //ti nisi inicijalizirao igru i dobio si partiju
            //sada si last winner, dodijeli bodove 
            //postavi mu bodove i karte u deck
            System.out.println("USLI U CASE 4: NISMO INCIJALIZIRALI I DOBILI PARTIJU");
            lastWinner = users.get(0);
            Repository.addPointsToUser(users.get(0),  GameEngine.getPointFromMyPlayerListPlayer2(playersOntable));
            
            System.out.println("Pobijedila ana! (User2)");
            lbRoundWinner.setText(lastWinner.getNickName().toString());
            lbRoundWinnerUser2.setText(lastWinner.getNickName());
        }
        System.out.println(lastWinner.getNickName() + "je pobijedio partiju");
        System.out.println("NA POTEZU JE SADA: " + lastWinner);
        game.setPlayedFirst(lastWinner);
        //pocisti karte na stolu -> gumbe i array
        
        
          //postavi score i podijeljene karte na gumbe
        new Thread() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
                Platform.runLater(() -> {
                    startGame();
                    clearTable();
                    counter++;
                    System.out.println("Stanje countera trenutno na: " + counter);
                    
                   if (counter!=11) {
                       System.out.println("Zapocinjemo novu rundu: Stanje countera trenutno na: " + counter);
                        startNewRound(indexOfWinnersPlayer);
                    } else {
                        serializeGame();
                        System.out.println("Zavrsen game: Stanje countera trenutno na: " + counter);
                        endGame();
                    }
                });
            }

           

          
        }.start();
        
    }
    
    
    private void startNewRound(int indexOfWinnersPlayer) {
     if ((isThisPlayerFirstToPlay && indexOfWinnersPlayer == 0) || (!isThisPlayerFirstToPlay && indexOfWinnersPlayer == 1)) {
            enableUserCombobox();
            // koliko shvacam kad enableam ovo enableam da idem nazad u combobox i slusam klik
        } 
    else{
            serializeGame();
        }
    }
    private boolean counternotEleven() {
        if (counter <= 11) {
               return false;
        }
        return true;
    }
    
    private String getWinnerofGame(ObservableList<User> users){
        if(users.get(0).getPoints()> users.get(1).getPoints()){
             return ("Winner of the game is: " + users.get(0).getNickName()+  " with total of: " + users.get(0).getPoints() + " points");
        }
             return ("Winner of the game is: " + users.get(1).getNickName()+  " with total of: " + users.get(1).getPoints() + " points");
     }
       
       
     private void endGame() {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("THE END");
        System.out.println("Dosli do kraja");
         
        alert.setHeaderText(getWinnerofGame(users));

        alert.setContentText("Go to main menu?");
        if (alert.showAndWait().get() == ButtonType.OK) {

            exitGame();
        }          
    }
     
        
     
       private void initUDPClientThread() {

        UDPClient udpClient = new UDPClient(this);
        udpClient.setDaemon(true);
        udpClient.start();
    }
     
      private void serializeGame() {

        if (game == null) {
            game = new Game(users, lastWinner, playersOntable);
        }
        
        users.get(0).setPlayersInHand(myPlayersInHand);
        System.out.println("Velicina liste igraca u mojoj ruci:"+ myPlayersInHand.size());
        users.get(1).setPlayersInHand(opponentPlayersInHand);
        System.out.println("Velicina liste igraca u protivnickoj ruci: "+ opponentPlayersInHand.size());
        
        
        
        game.setPlayersOntable(playersOntable);
        
        //game = new Game(users,lastWinner,playersOntable);
        
        System.out.println("Velicina liste igraca na stolu: " + playersOntable.size());
        System.out.println("Game za serijalizaciju: " + "useri: "+ users.toString()
                + ", lastwinner" + lastWinner.toString() + ", odabrani playeri na stolu: " + playersOntable.toString() + "broj odabranih igraca: " + playersOntable.size());
        Repository.setGameObject(game);
        

        TCPClient tcpClient = new TCPClient();
        tcpClient.setDaemon(true);
        tcpClient.start();
        /*
        try {
            SerializationUtils.write(game, FILE_NAME);

        } catch (IOException ex) {
            Logger.getLogger(TeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        
        
        

    }

    public void deserializeGame(Game game) {
        
        if (game == null) {
            gameQuit();
        }

        updateGame(game);
        initPlayersOnTable();

        //ako deserijalzirana igra ima dva playera na stolu pronadji pobjednika
        if (playersOntable.get(0) != null && playersOntable.get(1) != null) {
            System.out.println("ulazi u handler winner, obje karte na stolu!!!!!!");
            handleWinner();
        } else {
            System.out.println("Ne ulzai u handle winner, nema playera na stolu");
            startGame();
            /*
            if (isPlayersinTableEmpty() ) {
                endGame();
                // ovdje mi fali jos jedan uvjet if
            }
            */
        }

    }
    
    private void initPlayersOnTable() {
       System.out.println("Deserijalizirani playeri sa stola:");
        for (Player player : playersOntable) {
            if (player!= null) {
                System.out.println(player.getPicturePath());
                
            }
        }
        
        if (playersOntable.get(0)!=null) {
            handlePicture(playersOntableButtons.get(0), playersOntable.get(0).getPicturePath().trim());
            handlePlayersonTableNameLabel(playersOnTableLabels.get(0), playersOntable.get(0).getFirstName(),playersOntable.get(0).getLastName());
            handlePlayersonTableRatingLabel(playersOnTableLabels.get(2),playersOntable.get(0).getRating());
            
        }
        if (playersOntable.get(1)!=null) {
             handlePicture(playersOntableButtons.get(1), playersOntable.get(1).getPicturePath().trim());
              handlePlayersonTableNameLabel(playersOnTableLabels.get(1), playersOntable.get(1).getFirstName(),playersOntable.get(1).getLastName());
              handlePlayersonTableRatingLabel(playersOnTableLabels.get(3),playersOntable.get(1).getRating());
        }
        if(playersOntable.get(0)==null){
            playersOntableButtons.get(0).setGraphic(null);
        }
         if(playersOntable.get(1)==null){
            playersOntableButtons.get(1).setGraphic(null);
        }
        
        
    }
     private void updateGame(Game game) {
        users.get(0).setPoints(game.getUsers().get(1).getPoints());
        users.get(1).setPoints(game.getUsers().get(0).getPoints());
        
        System.out.println("USLI U DRUGOG PLAYERA, deserijalizacija odigranog poteza");
        
        //myPlayersInHand=game.getUsers().get(1).getPlayersInHand();
        //opponentPlayersInHand=game.getUsers().get(0).getPlayersInHand();
        playersOntable = game.getPlayersOntable();
        lastWinner = game.getPlayedFirst();
    }

    private void exitGame()  {
        clearRepo();
        try {
            openMainMenu();
        } catch (IOException ex) {
            Logger.getLogger(TeamMultiplayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
      private void clearRepo() {
        Repository.deleteUsers();
        Repository.deleteFrstToPlay();
        Repository.deleteThisUser();
        Repository.setGameObject(null);
    }

    private void openMainMenu() throws IOException {
        Stage multiplayer = (Stage) btnQuit.getScene().getWindow();
        multiplayer.close();

        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/views/MainMenu2.fxml"));
        Stage stage = new Stage();
        stage.setTitle("WORLD CUP 2022 GAME");
        stage.setScene(new Scene(root, 700, 500));
        stage.setResizable(false);
        stage.show();
    }

    private void gameQuit() {
       Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Player left game");
        alert.setHeaderText("Exit game");

        alert.setContentText("Go to main menu?");
        if (alert.showAndWait().get() == ButtonType.OK) {

            exitGame();
        }
    }

    private boolean isPlayersinTableEmpty() {
       return playersOntable.get(0)== null && playersOntable.get(1)== null;
    }
    
    @FXML
    private void btnSendMessage_OnAction() {
        if (tfMessage.getText().trim().length() > 0) {
            MessageUtils.sendMessage(Repository.getThisUser().getNickName(), tfMessage.getText().trim());
            addMessage(tfMessage.getText().trim(), Repository.getThisUser().getNickName());
            tfMessage.clear();

            try {
                messengerService.getAllMessages().forEach(m -> System.out.println(m.getMessage()));
            } catch (RemoteException ex) {
                Logger.getLogger(TeamMultiplayerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @FXML
    private void tfMessage_OnKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSendMessage_OnAction();
        }
    }
   
   private void initChat() {
        messengerService = MessageUtils.getMessengerService();
        messages = FXCollections.observableArrayList();
        Bindings.bindContentBidirectional(messages, vbMessages.getChildren());
        tfMessage.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() >= MESSAGE_LENGTH) {
                        ((StringProperty) observable).setValue(oldValue);
                    }
                }
        );
        updateChat();
    }
     
      private void updateChat() {
        try {
            messengerService.clearMessages();

            Thread msServiceLoop = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }

                        try {
                            if (messages.size() != messengerService.getAllMessages().size()) {

                                Platform.runLater(() -> {
                                    try {
                                        System.out.println("Uazim u load messages");
                                        loadMessages();
                                    } catch (RemoteException ex) {
                                        Logger.getLogger(TeamMultiplayerController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                });

                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(TeamMultiplayerController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            };

            msServiceLoop.setDaemon(true);
            msServiceLoop.start();

        } catch (RemoteException ex) {
            Logger.getLogger(TeamMultiplayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void loadMessages() throws RemoteException {
        messages.clear();
        messengerService.getAllMessages().forEach(m -> addMessage(m.getMessage(), m.getPlayer()));
        System.out.println("Loadam sve poruke iz messangerService");
        messengerService.getAllMessages().forEach(m -> System.out.println(m));
    }
  
     private void addMessage(String message, String nickName) {

        Label label = new Label();
        label.setFont(new Font(FONT_SIZE));

        System.out.println(message + nickName);
        if (Repository.getThisUser().getNickName().equals(nickName)) {
            label.setTextFill(Color.BLACK);
        } else {
            label.setTextFill(Color.DARKGREEN);
        }

        label.setText(String.format(MESSAGE_FORMAT, LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT)), nickName, message));
        messages.add(label);
        moveScrollPane();
    }
      private void moveScrollPane() {
        spMessageContainer.applyCss();
        spMessageContainer.layout();
        spMessageContainer.setVvalue(1D);
    }

    
}
