/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

import server.Model.Game;

/**
 *
 * @author conno
 */
public abstract class GameCommand implements ICommand<Game> {
    
    public static class LeaveGameCommand extends GameCommand {
        
        private final String ID;
        
        public LeaveGameCommand(String ID) {
            this.ID = ID;
        }
        
        @Override
        public ResultCode execute(Game commandHandler) {
            commandHandler.PlayerLeave(ID);
            return ResultCode.Success;
        }
    }
}
