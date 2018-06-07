/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

import server.Commands.ViewCommand.*;
import server.Connectivity.SocketHandler;
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

    public static class FindGameCommand extends ModelCommand {

        private final String ID;
        private final SocketHandler sh;

        public FindGameCommand(String ID, SocketHandler sh) {
            this.ID = ID;
            this.sh = sh;
        }

        @Override
        public ResultCode execute(Model commandHandler) {
            commandHandler.FindGame(ID, sh);
            return ResultCode.Success;
        }
    }

    public static class StartModelCommand extends ModelCommand {

        @Override
        public ResultCode execute(Model commandHandler) {
            commandHandler.Start();
            return ResultCode.Success;
        }
    }

    public static class PassGamesListToViewCommand extends ModelCommand {

        @Override
        public ResultCode execute(Model commandHandler) {
            commandHandler.ch.Handle(new PrintGamesListCommand(commandHandler.games));
            return ResultCode.Success;
        }
    }
}
