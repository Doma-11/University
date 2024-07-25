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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author boric
 */
public class GameMove implements Externalizable {
     private static final long serialVersionUID = 5L;
    
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty rating;    
    private StringProperty picturePath;
    private StringProperty chosenComboBoxIndex;
    
    public GameMove(){
        
    }
      public GameMove(String firstName, String lastName, String rating, String picturePath, String chosenComboBoxIndex) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.rating = new SimpleStringProperty(rating);
        this.picturePath = new SimpleStringProperty(picturePath);
         this.chosenComboBoxIndex = new SimpleStringProperty(chosenComboBoxIndex);
     
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


    public String getRating() {
        return rating.get();
    }

    public void setRating(String rating) {
        this.rating.set(rating);
        
    }

    public String getPicturePath() {
        return picturePath.get();
    }

    public void setPicturePath(String picturePath) {
        this.picturePath.set(picturePath);
    }
    
     public String getChosenComboboxIndex() {
        return chosenComboBoxIndex.get();
    }

    public void setChosenComboboxIndex(String chosenComboBoxIndex) {
        this.chosenComboBoxIndex.set(chosenComboBoxIndex);
    }
    
    
    @Override
    public String toString() {
        return firstName.get() + " " + lastName.get();
    }

    @Override
    public void writeExternal(ObjectOutput oos) throws IOException {
        oos.writeUTF(firstName.get());
        oos.writeUTF(lastName.get());
        oos.writeUTF(rating.get());
        oos.writeUTF(picturePath.get());
        oos.writeUTF(chosenComboBoxIndex.get());
    }

    @Override
    public void readExternal(ObjectInput ois) throws IOException, ClassNotFoundException {
       firstName=new SimpleStringProperty(ois.readUTF());
       lastName=new SimpleStringProperty(ois.readUTF());
       rating = new SimpleStringProperty(ois.readUTF());
       picturePath=new SimpleStringProperty(ois.readUTF());
       chosenComboBoxIndex=new SimpleStringProperty(ois.readUTF());
       
       
    }
}
