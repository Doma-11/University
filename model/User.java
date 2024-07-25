/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author boric
 */
public class User implements Externalizable {
    
    private static final long serialVersionUID = 2L;
      
    private StringProperty nickName;
    private DoubleProperty points;
    private ListProperty<Player> playersinHand;
    
    
    public User(){
        
    }
    
  public User(String nickName,double points,ObservableList<Player> playersinHand) {
        this.nickName = new SimpleStringProperty(nickName);
        this.points = new SimpleDoubleProperty(points);
        this. playersinHand = new SimpleListProperty<>( playersinHand);
        
    }
    public User(String nickName) {
        this.nickName = new SimpleStringProperty(nickName);
        
    }

    public User(String nickName, double points) {
        this.nickName = new SimpleStringProperty(nickName);
        this.points = new SimpleDoubleProperty(points);
    }
    

    public String getNickName() {
        return nickName.get();
    }

    public void setNickName(StringProperty nickName) {
        this.nickName = nickName;
    }

    public double getPoints() {
        return points.get();
    }

    public void setPoints(double points) {
        this.points.set(points);
    }
    
    
    public ObservableList<Player> getPlayersInHand() {
        return playersinHand.get();
    }

    public void setPlayersInHand(ObservableList<Player> playersinHand) {
        this.playersinHand.set(playersinHand);
    }

    

     @Override
    public String toString() {
        return nickName.get();
    }


    @Override
    public void writeExternal(ObjectOutput oos) throws IOException {
        oos.writeUTF(nickName.get());
        oos.writeDouble(points.get());
        oos.writeObject(new ArrayList<>(playersinHand.get()));
        
    }

    @Override
    public void readExternal(ObjectInput ois) throws IOException, ClassNotFoundException {
        nickName = new SimpleStringProperty(ois.readUTF());
        points = new SimpleDoubleProperty(ois.readDouble());
        
        List<Player> playersInHandList = (List<Player>) ois.readObject();
        playersinHand = new SimpleListProperty<>(FXCollections.observableArrayList(playersInHandList));

        
        
    }
    
}
