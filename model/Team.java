/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author boric
 */
public class Team {
    private StringProperty name;
    private ObservableList<Player> players;
    private DoubleProperty totalPoints;
   
     
     
     
    public Team() {
    }


    public Team(String name, List<Player> players, Double totalPoints) {
        this.name=new SimpleStringProperty(name);
        this.players= new SimpleListProperty((ObservableList) players);
        this.totalPoints= new SimpleDoubleProperty(totalPoints);
    }
  
    
    public String getName() {
        return name.get();
    }

    public void setName(String Name) {
        this.name.set(Name);
    }

    public List<Player> getPlayers() {
        return players;
    }

   
    
     public double getTotalPoints() {
        return totalPoints.get();
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints.set(totalPoints);
    }

    @Override
    public String toString() {
        return name.get();
    }
    
    public void addPlayer(Player player){
        players.add(player);
    }
    
}
