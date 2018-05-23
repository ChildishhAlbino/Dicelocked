/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

import server.Commands.ViewCommand.*;
import server.Model.Model;

/**
 *
 * @author conno
 */
public abstract class ModelCommand implements ICommand<Model> {

    public static class TestModelCommand extends ModelCommand {

        @Override
        public ResultCode execute(Model commandHandler) {
            return ResultCode.Failure;
        }
    }

    public static class ParseInputCommand extends ModelCommand {

        public String input;

        public ParseInputCommand(String input) {
            this.input = input;
        }

        @Override
        public ResultCode execute(Model commandHandler) {
            commandHandler.GetCommandHandler().Handle(new PrintToViewCommand(input));
            return ResultCode.Success;
        }
    }
}
