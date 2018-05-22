/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Controller;

import server.Commands.*;
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
    public void start() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ICommandHandler GetCommandHandler() {
        return ch;
    }
}
