/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinogames.server.Controller;

import com.albinogames.server.Connectivity.Connectivity;
import com.albinogames.server.Commands.ICommandHandler;
import com.albinogames.server.Commands.ModelCommand;
import com.albinogames.server.Commands.ControllerCommand;
import com.albinogames.server.Commands.ConnectivityCommand.*;
import com.albinogames.server.DB.DB;
import com.albinogames.server.IComponent;

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
        DB.SetCommandHandler(this);
        Connectivity.GetInstance().Handle(new StartupCommand(this));
    }

    @Override
    public ICommandHandler GetCommandHandler() {
        return ch;
    }
}
