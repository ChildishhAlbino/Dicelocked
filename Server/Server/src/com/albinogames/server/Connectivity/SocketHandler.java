/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinogames.server.Connectivity;

import com.albinogames.server.Commands.ICommandHandler;
import com.albinogames.server.Commands.ConnectivityCommand;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.albinogames.server.Commands.ConnectivityCommand.*;

/**
 *
 * @author conno
 */
public class SocketHandler extends Thread {

    private Socket socket = null;
    private BufferedReader in;
    private PrintWriter out;
    private ICommandHandler<ConnectivityCommand> ch;
    private final int ID;
    private boolean listening = true;

    public SocketHandler(Socket socket, int ID) {
        super("SocketHandler");
        this.socket = socket;
        this.ID = ID;
    }

    @Override
    public void run() {
        System.out.println("Socket handler no." + GetID() + " reporting for duty!");

        try {
            String incomming = null;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            WaitForLogonAttempt();
            while (socket.isConnected() && listening) {
                incomming = in.readLine();
                ch.Handle(new ProcessIncomingMessageCommand(incomming));
                incomming = null;
            }
        } catch (IOException ex) {
            if (socket.isClosed()) {
                ch.Handle(new ProcessIncomingMessageCommand("Error: client shut connection prematurely"));
                Shutdown();
            }
        }
    }

    public void Shutdown() {
        try {
            // still needs to remove player from games
            // also needs to delete games that didn't get filled.
            listening = false;
            socket.close();
            in = null;
            out = null;
        } catch (IOException ex) {
            System.out.println("Lol was an error!");
        }
    }

    public void Send(String message) {
        message += "?";
        System.out.println(message);
        out.println(message);
    }

    public int GetID() {
        return ID;
    }

    public void SetCommandHandler(ICommandHandler ch) {
        this.ch = ch;
    }

    public ICommandHandler GetCommandHandler() {
        return ch;
    }

    private void WaitForLogonAttempt() throws IOException {
        String s = null;
        while (s == null) {
            s = in.readLine();
        }
        //System.out.println(s);
        ch.Handle(new ParseLogonAttempt(this, s));
    }
}
