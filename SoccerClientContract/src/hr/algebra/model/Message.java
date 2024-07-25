/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author boric
 */
public class Message implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String playerNickname;
    private String message;
    private LocalDateTime time;


    public Message(String username, String message, LocalDateTime time) {
        this.playerNickname = username;
        this.message = message;
        this.time = time;
    }

    public String getPlayer() {
        return playerNickname;
    }

    public void setPlayer(String user) {
        this.playerNickname = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" + "playerNickname=" + playerNickname + ", message=" + message + ", time=" + time + '}';
    }
    
}
