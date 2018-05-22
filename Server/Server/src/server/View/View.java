/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.View;

import server.Commands.ICommandHandler;
import server.Commands.ViewCommand;

/**
 *
 * @author conno
 */
public class View implements ICommandHandler<ViewCommand>{

    @Override
    public void Handle(ViewCommand command) {
        command.execute(this);
    }

}
