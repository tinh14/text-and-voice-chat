/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package messagetype;

/**
 *
 * @author tinhlam
 */
public class StringMessage extends Message{
    private String content;
    
    public StringMessage(String user, String content){
        super(user);
        this.content = content;
    }
    
    public void setContent(String content){
        this.content = content;
    }
    
    public String getContent(){
        return content;
    }
}
