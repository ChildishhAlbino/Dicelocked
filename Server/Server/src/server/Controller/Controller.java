/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Controller;

import server.Commands.*;
import server.Commands.ConnectivityCommand.*;
import server.Connectivity.*;
import server.DB.DB;
import server.IComponent;

/**
 *
 * @author conno
 */
public class Controller implements ICommandHandler<ControllerCommand>, IComponent {

    private ICommandHandler<ModelCommand> ch;

    @Override
    public void Handle(ControllerCommand command) {
        command.execute(this);
    }

    @Override
    public void SetCommandHandler(ICommandHandler ch) {
        this.ch = ch;
    }

    @Override
    public void Start() {
        DB.GetDB().Handle(new DBCommand.HashPasswordCommand("lol"));
        ConnectivityCommandHandlerInstance.GetInstance().Handle(new StartupCommand(this));
    }

    @Override
    public ICommandHandler GetCommandHandler() {
        return ch;
    }
}
