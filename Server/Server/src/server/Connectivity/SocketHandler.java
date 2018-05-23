/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Connectivity;

import java.io.*;
import java.net.*;
import server.Commands.*;
import server.Model.Player;

/**
 *
 * @author conno
 */
public class SocketHandler extends Thread {

    private Socket socket = null;
    private BufferedReader in;
    private PrintWriter out;
    private ICommandHandler<ConnectivityCommand> ch;
    private final Connectivity connect;
    private final int ID;

    public SocketHandler(Socket socket, Connectivity connect, int ID) {
        super("SocketHandler");
        this.socket = socket;
        this.connect = connect;
        this.ID = ID;
    }

    @Override
    public void run() {    
        System.out.println("Socket handler no." + GetID() + " reporting for duty!");
        Player player;
        try {
            String incomming = null;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (socket.isConnected()) {
                incomming = in.readLine();
                connect.Process(incomming);
                incomming = null;
            }
        } catch (IOException ex) {
            if (socket.isClosed()) {
                connect.Process("Error: client shut connection prematurely");
                Shutdown();
            }
        }
    }

    public void Shutdown() {
        try {
            socket.close();
            in = null;
            out = null;
        } catch (IOException ex) {
            System.out.println("Lol was an error!");
        }
    }

    public void Send(String message) {
        out.println(message);
    }

    public int GetID() {
        return ID;
    }
    
    public void SetCommandHandler(ICommandHandler ch){
        this.ch = ch;
    }
    
    public ICommandHandler GetCommandHandler(){
        return ch;
    }
}
