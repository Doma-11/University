/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import hr.algebra.model.Player;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

/**
 *
 * @author boric
 */
public class RandomPlayerUtils {
    
    
    // vrati mi random playera
    public static final Player getRandomPlayer(ObservableList<Player> players){
        Player newPlayer =  null;
        Random r = new Random();
        
       List<Player> remainingPlayers= players.stream().filter(p-> p.getIsSelected()== false).collect(Collectors.toList());
        
        if(!remainingPlayers.isEmpty()){
            int index = r.nextInt(remainingPlayers.size());
            remainingPlayers.get(index).setIsSelected(true);
            
            newPlayer= remainingPlayers.get(index);
        }
        
       return newPlayer;
    }
    
}
