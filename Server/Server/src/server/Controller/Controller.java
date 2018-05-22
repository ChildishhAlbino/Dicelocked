/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Controller;

import server.Commands.*;
import server.Connectivity.Connectivity;
import server.IComponent;

/**
 *
 * @author conno
 */
public class Controller implements ICommandHandler<ControllerCommand>, IComponent {

    private ICommandHandler<ModelCommand> ch;
    private Connectivity connection;

    @Override
    public void Handle(ControllerCommand command) {
        command.execute(this);
    }

    @Override
    public void SetCommandHandler(ICommandHandler ch) {
        this.ch = ch;
    }

    @Override
    public void run() {
        connection = new Connectivity();
        connection.StartServer();
    }

    @Override
    public ICommandHandler GetCommandHandler() {
        return ch;
    }
}
