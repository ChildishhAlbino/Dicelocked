/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

import server.Commands.ViewCommand.*;
import server.Connectivity.SocketHandler;
import server.Model.Model;
import server.Model.Player;

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

        private final Player player;
        private final SocketHandler sh;

        public FindGameCommand(Player player, SocketHandler sh) {
            this.player = player;
            this.sh = sh;
        }

        @Override
        public ResultCode execute(Model commandHandler) {
            commandHandler.FindGame(player, sh);
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
}
