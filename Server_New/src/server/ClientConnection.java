/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import messagetype.*;

/**
 *
 * @author tinhlam
 */
public class ClientConnection extends Thread {

    public Socket socket;

    public ObjectOutputStream os;
    public ObjectInputStream is;

    public static ArrayList<ClientConnection> clients = new ArrayList<>();

    public ClientConnection(Socket socket) {
        try {
            this.socket = socket;
            os = new ObjectOutputStream(this.socket.getOutputStream());
            is = new ObjectInputStream(this.socket.getInputStream());
            clients.add(this);
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                Message message = (Message) is.readObject();
                broadcastMessage(message);
            } catch (IOException | ClassNotFoundException ex) {
                closeConnection();
                return;
            }
        }
    }

    public void broadcastMessage(Message message) {
        for (ClientConnection client : clients) {
            try {
                client.os.writeObject(message);
                client.os.flush();
            } catch (IOException ex) {
                Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void closeConnection() {
        socket = null;
        is = null;
        os = null;
        clients.remove(this);
    }
}
