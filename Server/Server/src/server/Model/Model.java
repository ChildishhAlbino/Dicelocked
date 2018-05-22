/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Model;

import server.Commands.ICommandHandler;
import server.Commands.ModelCommand;

/**
 *
 * @author conno
 */
public class Model implements ICommandHandler<ModelCommand> {

    @Override
    public void Handle(ModelCommand command) {
        command.execute(this);
    }
}
