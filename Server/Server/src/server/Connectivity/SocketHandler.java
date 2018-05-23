/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Connectivity;

import java.io.*;
import java.net.*;

/**
 *
 * @author conno
 */
public class SocketHandler extends Thread {

    private Socket socket = null;
    private BufferedReader in;
    private PrintWriter out;
    private Connectivity connect;

    public SocketHandler(Socket socket, Connectivity connect) {
        super("SocketHandler");
        this.socket = socket;
        this.connect = connect;
    }

    public void run() {
        String incomming = null;
        System.out.println("LOL");
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            incomming = in.readLine();
            while (incomming != null && incomming.contains("??")) {
                connect.Process(incomming);
                incomming = null;
            }
        } catch (IOException ex) {
            if (incomming != null) {
                connect.Process(incomming);
                incomming = null;
            }
        }
        
    }

    public void Send(String message) {
        out.println(message);
    }
}
