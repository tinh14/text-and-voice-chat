/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package client;

import messagetype.AudioMessage;
import messagetype.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import messagetype.*;

/**
 *
 * @author tinhlam
 */
public class Client {

    private static final int PORT = 6366;
    private static final String HOST = "localhost";

    public Socket socket;

    public ObjectOutputStream os;
    public ObjectInputStream is;

    public static BlockingQueue<Message> messageQueue = new LinkedBlockingDeque<>();
    
    public Client() {
        try {
            socket = new Socket(HOST, PORT);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void listenMessage() {
        Thread listenMessageThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Message message = (Message) is.readObject();
                        messageQueue.put(message);
                        System.out.println(messageQueue.size());
                    } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        listenMessageThread.start();
    }
    
    public void sendMessage(Message message) {
        try {
            os.writeObject(message);
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
