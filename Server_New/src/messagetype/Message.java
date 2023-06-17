/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package messagetype;

import java.io.Serializable;

/**
 *
 * @author tinhlam
 */
public class Message implements Serializable{
    private String user;
    
    public Message(String user){
        this.user = user;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    
    public String getUser(){
        return user;
    }
}
