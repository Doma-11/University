/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.engine;

import hr.algebra.model.Player;
import hr.algebra.model.User;
import hr.algebra.repository.Repository;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author boric
 */
public final class GameEngine {
    
     public static double getPointsFromMyPlayerList(ObservableList<Player> players) {

        double points = 0.0;
        for (Player player : players) {
            points += player.getRating();
        }
        return points;
    }
      public static double getPointsFromList(ObservableList<Player> players) {

        double points = 0.0;
        for (Player player : players) {
            points += player.getRating();
        }
        return points;
    }
     
     public static double getPointFromMyPlayerListPlayer1(ObservableList<Player> players){
         double points=players.get(0).getRating();
         return points;
     }
      public static double getPointFromMyPlayerListPlayer2(ObservableList<Player> players){
         double points= players.get(1).getRating();
         return points;
     }
    
    public static User getWinnerOfRound(ObservableList<User> users,ObservableList<Player> players, User lastWinner){
        int indexofWinner= getIndexOfUser(lastWinner, users);
        System.out.println("Game started by player:" + lastWinner.toString());
        
        Player firstPlayer= players.get(indexofWinner);
        
        int index= indexofWinner;
        
        for (int i = 0; i < players.size(); i++) {
            if(i != indexofWinner && players.get(i)!= null && players.get(i).getRating()> firstPlayer.getRating()){
                index=i;
            }
        }
        System.out.println("Winner of this round is: " + players.get(index) + " Congrats!");
        return users.get(index);
    }
    
     public static int getWinnerOfRoundMultiplayer(ObservableList<User> users, ObservableList<Player> players, User lastWinner) {

        int indexOfLastWinner=0;
        
        if (!lastWinner.getNickName().equals(Repository.getFirstToPlay().getNickName())) {
            indexOfLastWinner=1;
        }
        
        System.out.println("Igru je zapoceo " + lastWinner.toString());
        Player firstPlayer = players.get(indexOfLastWinner);

        int index = indexOfLastWinner;

        for (int i = 0; i < players.size(); i++) {
            if (i != indexOfLastWinner && players.get(i) != null && players.get(i).getRating()>firstPlayer.getRating()) {
                index = i;

            }
        }
        
        return index;
    }

    public static int getIndexOfUser(User user, ObservableList<User> users) {
        int index = 0;
                
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getNickName().equals(user.getNickName())) {
                index = i;
            }
        }
        return index;
    }
    
    public static int getIndexOfCombobox(ComboBox combobox, ObservableList<ComboBox> comboboxes) {
        int index = 0;

        for (int i = 0; i < comboboxes.size(); i++) {
            if (comboboxes.get(i).getId().equals(combobox.getId())) {
                index = i;
            }

        }
        return index;
    }
    
     public static int getIndexOfPlayer(Player player, ObservableList<Player> players) {
        int index = 0;

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getLastName().equals(player.getLastName())) {
                index = i;
            }

        }
        return index;
    }
     

    
}
