/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Connectivity;

import java.io.*;
import java.net.*;
import server.Commands.*;
import server.Commands.ConnectivityCommand.*;

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
            ch.Handle(new PassToControllerCommand(ListenForPlayerName(), this));
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

    private String ListenForPlayerName() throws IOException {
        String name = null;
        ch.Handle(new AskForInputCommand(this, AskForInputCommand.InputType.name));
        System.out.println("Sent command!");
        while (name == null) {
            name = in.readLine();
        }

        name = Identification.ID.GenerateID_Name(3, name);
        return name;
    }
}
