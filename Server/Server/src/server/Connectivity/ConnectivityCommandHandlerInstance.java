/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Connectivity;

import server.Commands.ConnectivityCommand;
import server.Commands.ICommandHandler;

/**
 *
 * @author conno
 */
public class ConnectivityCommandHandlerInstance {

    private static ICommandHandler<ConnectivityCommand> instance;

    public static ICommandHandler<ConnectivityCommand> GetInstance() {
        if (instance == null) {
            instance = new Connectivity();
        }
        return instance;
    }
}
