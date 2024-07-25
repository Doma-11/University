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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author boric
 */
public class Player implements Externalizable{
    
    private static final long serialVersionUID = 1L;
    
    private StringProperty firstName;
    private StringProperty lastName;
    private DoubleProperty rating;    
    private StringProperty picturePath;
    private BooleanProperty isSelected;
    
    public Player(){
        
    }
      public Player(String firstName, String lastName, double rating, String picturePath) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.rating = new SimpleDoubleProperty(rating);
        this.picturePath = new SimpleStringProperty(picturePath);
     
    }
    
    public Player(String firstName, String lastName, double rating, String picturePath,boolean isSelected) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.rating = new SimpleDoubleProperty(rating);
        this.picturePath = new SimpleStringProperty(picturePath);
        this.isSelected = new SimpleBooleanProperty(isSelected);
     
    }
   
    
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }


    public Double getRating() {
        return rating.get();
    }

    public void setRating(Double rating) {
        this.rating.set(rating);
        
    }

    public String getPicturePath() {
        return picturePath.get();
    }

    public void setPicturePath(String picturePath) {
        this.picturePath.set(picturePath);
    }
    public boolean getIsSelected() {
        return isSelected.get();
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
    }


    @Override
    public String toString() {
        return firstName.get() + " " + lastName.get();
    }

    @Override
    public void writeExternal(ObjectOutput oos) throws IOException {
        oos.writeUTF(firstName.get());
        oos.writeUTF(lastName.get());
        oos.writeDouble(rating.get());
        oos.writeUTF(picturePath.get());
        oos.writeBoolean(isSelected.get());
        
    }

    @Override
    public void readExternal(ObjectInput ois) throws IOException, ClassNotFoundException {
       firstName=new SimpleStringProperty(ois.readUTF());
       lastName=new SimpleStringProperty(ois.readUTF());
       rating = new SimpleDoubleProperty(ois.readDouble());
       picturePath=new SimpleStringProperty(ois.readUTF());
       isSelected = new SimpleBooleanProperty(ois.readBoolean());
       
    }

    
    
    
}
