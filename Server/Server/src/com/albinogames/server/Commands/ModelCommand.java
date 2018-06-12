/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinogames.server.Commands;

import com.albinogames.server.Commands.ViewCommand.*;
import com.albinogames.server.Connectivity.SocketHandler;
import com.albinogames.server.Model.Game;
import com.albinogames.server.Model.Model;
import com.albinogames.server.Model.Player;

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
        
        private final Player p;
        private final SocketHandler sh;
        
        public FindGameCommand(Player ID, SocketHandler sh) {
            this.p = ID;
            this.sh = sh;
        }
        
        @Override
        public ResultCode execute(Model commandHandler) {
            commandHandler.FindGame(p, sh);
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
    
    public static class PassGameCommandToGame extends ModelCommand {
        
        private final GameCommand gc;
        private final String gameID;
        
        public PassGameCommandToGame(GameCommand gc, String gameID) {
            this.gc = gc;
            this.gameID = gameID;
        }
        
        @Override
        public ResultCode execute(Model commandHandler) {
            commandHandler.PassGameCommandToGame(gameID, gc);
            return ResultCode.Success;
        }
    }
    
    public static class RemoveGameCommand extends ModelCommand {

        private final Game g;
        
        public RemoveGameCommand(Game g) {
            this.g = g;
        }
        
        @Override
        public ResultCode execute(Model commandHandler) {
            commandHandler.RemoveGame(g);
            return ResultCode.Success;
        }
    }
}
