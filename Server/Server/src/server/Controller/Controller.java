/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Controller;

import server.Commands.ControllerCommand;
import server.Commands.ICommandHandler;

/**
 *
 * @author conno
 */
public class Controller implements ICommandHandler<ControllerCommand> {

    @Override
    public void Handle(ControllerCommand command) {
        command.execute(this);
    }

}
