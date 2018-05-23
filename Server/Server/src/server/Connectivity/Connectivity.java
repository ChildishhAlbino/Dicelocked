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
import server.Commands.*;
import server.Commands.ControllerCommand.*;
import server.Commands.ICommand;
import server.Commands.ICommandHandler;
import server.IComponent;

/**
 *
 * @author conno
 */
public class Connectivity extends Thread implements ICommandHandler<ConnectivityCommand>, IComponent {

    private boolean listening = true; // bool to control while loop
    private List<SocketHandler> socketHandlers; //list of all sockethandlers in use - needs refinement
    private final int PORT_NUMBER = 11000;
    private ICommandHandler<ControllerCommand> ch;

    public Connectivity() {
        super("ConnectivityManager");
        socketHandlers = new ArrayList<>();
    }

    @Override
    public void run() {
        System.out.println("Starting Server!");
        try (ServerSocket socket = new ServerSocket(PORT_NUMBER)) {
            while (listening) {
                SocketHandler sh = null;
                if (socket.accept() != null) {
                    System.out.println("Found a connection");
                    sh = new SocketHandler(socket.accept(), this);
                    sh.start();
                    socketHandlers.add(sh);          
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
        ch.Handle(new ProcessIncommingCommand(incomming));
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
}
