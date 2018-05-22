/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Connectivity;

import java.io.IOException;
import java.net.*;
import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author conno
 */
public class Connectivity extends Thread{

    private boolean listening = true; // bool to control while loop
    private List<SocketHandler> socketHandlers; //list of all sockethandlers in use - needs refinement
    private final int PORT_NUMBER = 11000;
    public Connectivity() {
        super("ConnectivityManager");
        socketHandlers = new ArrayList<>();
    }

    public void StartServer() {
        System.out.println("Starting Server!");
        try (ServerSocket socket = new ServerSocket(PORT_NUMBER)) {
            while (listening) {
                SocketHandler sh;
                if (socket.accept() != null) {
                    System.out.println("Found a connection");
                    sh = new SocketHandler(socket.accept(), this);
                    socketHandlers.add(sh);
                    sh.run();
                }
            }
        } catch (UnknownHostException ex) {
            listening = false;
            System.out.println(ex.toString());
        } catch (IOException ex) {
            listening = false;
            System.out.println(ex.toString());
        }
    }
    
    public void Process(String incomming){
        
    }
}
