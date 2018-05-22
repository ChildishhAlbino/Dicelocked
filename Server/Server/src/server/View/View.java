/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.View;

import server.Commands.*;
import server.IComponent;

/**
 *
 * @author conno
 */
public class View implements ICommandHandler<ViewCommand>, IComponent {

    private ICommandHandler<ControllerCommand> ch;

    @Override
    public void Handle(ViewCommand command) {
        command.execute(this);
    }

    @Override
    public void SetCommandHandler(ICommandHandler ch) {
        this.ch = ch;
    }

    @Override
    public void start() {
        System.out.println("Welcome to your DiceLocked Server Console!");
    }

    @Override
    public ICommandHandler GetCommandHandler() {
        return ch;
    }

}
