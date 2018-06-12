/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinogames.server.Connectivity;

import com.albinogames.server.Commands.ConnectivityCommand;
import com.albinogames.server.Commands.ControllerCommand;
import java.io.IOException;
import java.net.*;
import java.rmi.UnknownHostException;
import java.util.HashMap;
import com.albinogames.server.Commands.ControllerCommand.*;
import com.albinogames.server.Commands.ICommand;
import com.albinogames.server.Commands.ICommandHandler;
import com.albinogames.server.IComponent;

/**
 *
 * @author conno
 */
public class Connectivity extends Thread implements ICommandHandler<ConnectivityCommand>, IComponent {

    public enum LoginType {
        error,
        sign_up,
        sign_in,

    }
    private boolean listening = true; // bool to control while loop
    //private List<SocketHandler> socketHandlers; //list of all sockethandlers in use - needs refinement

    private static ICommandHandler<ConnectivityCommand> instance;
    private HashMap<Integer, SocketHandler> socketHandlers;
    private final int PORT_NUMBER = 11000;
    private ICommandHandler<ControllerCommand> ch;

    public Connectivity() {
        super("ConnectivityManager");
        socketHandlers = new HashMap<>();
    }

    public static ICommandHandler<ConnectivityCommand> GetInstance() {
        if (instance == null) {
            instance = new Connectivity();
        }
        return instance;
    }

    @Override
    public void run() {
        System.out.println("Starting Server!");
        try (ServerSocket socket = new ServerSocket(PORT_NUMBER)) {
            while (listening) {
                SocketHandler sh = null;
                Socket clientSocket = socket.accept();
                if (clientSocket != null) {
                    System.out.println("Found a connection");
                    sh = new SocketHandler(clientSocket, com.albinogames.server.identification.IDGenerator.GenerateID_Int(2));
                    sh.SetCommandHandler(this);
                    socketHandlers.put(sh.GetID(), sh);
                    sh.start();
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

    public void Process(String incomming) {
        ch.Handle(new ProcessIncomingCommand(incomming));
    }

    @Override
    public void Handle(ConnectivityCommand command) {
        if (command.execute(this) != ICommand.ResultCode.Failure) {
            // record command in log
        }
    }

    @Override
    public void SetCommandHandler(ICommandHandler ch) {
        this.ch = ch;
    }

    @Override
    public ICommandHandler GetCommandHandler() {
        return ch;
    }

    @Override
    public void Start() {

    }

    public SocketHandler GetSocketHandlerByID(String ID) {
        SocketHandler sh = socketHandlers.get(ID);
        return sh;
    }

    public LoginType ParseLogonCode(String logonCode) {
        LoginType l = LoginType.error;
        switch (logonCode.substring(0, 4)) {
            case "sii-":
                //System.out.println("SignIn!");
                return LoginType.sign_in;
            case "sui-":
                //System.out.println("SignUp");
                return LoginType.sign_up;
        }
        return l;
    }
}
