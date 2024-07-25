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
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author boric
 */
public class Game implements Externalizable {
    
    
    private static final long serialVersionUID = 3L;
    
    private ListProperty<User> users;
    private ObjectProperty<User> playedFirst;
    private ListProperty<Player> playersonTable; // odabrani playeri-provjeriti jel mi treba za multiplayer
    
    /**
     *
     */
    public Game(){
        
    }

    public Game(ObservableList<User> users, User playedFirst, ObservableList<Player> playersonTable) {
        this.users =  new SimpleListProperty<>(users);
        this.playedFirst = new SimpleObjectProperty(playedFirst);
        this.playersonTable =new SimpleListProperty<>(playersonTable);
    }

    public ObservableList<User> getUsers() {
        return users.get();
    }

    public void setUsers(ObservableList<User> users) {
        this.users.set(users);
    }

    public User getPlayedFirst() {
        return playedFirst.get();
    }

    public void setPlayedFirst(User playedFirst) {
        this.playedFirst.set(playedFirst);
    }

    public ObservableList<Player> getPlayersOntable() {
        return playersonTable.get();
    }

    public void setPlayersOntable(ObservableList<Player> playersonTable ) {
        this.playersonTable.set(playersonTable);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(new ArrayList<>(users.get()));
        out.writeObject(playedFirst.get());
        out.writeObject(new ArrayList<>(playersonTable.get()));
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        List<User> usersList = (List<User>) in.readObject();
        users = new SimpleListProperty<>(FXCollections.observableArrayList(usersList));
        
        playedFirst = new SimpleObjectProperty<>((User) in.readObject());
        
        List<Player> playersonTableList = (List<Player>) in.readObject();
        playersonTable = new SimpleListProperty<>(FXCollections.observableArrayList(playersonTableList));
        
    }
    
    
}
