/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package messagetype;

/**
 *
 * @author tinhlam
 */
public class AudioMessage extends Message{
    private byte[] buf;
    
    public AudioMessage(String user, byte[] buf){
        super(user);
        this.buf = buf;
    }
    
    public void setAudio(byte[] buf){
        this.buf = buf;
    }
    
    public byte[] getAudio(){
        return buf;
    }
}
