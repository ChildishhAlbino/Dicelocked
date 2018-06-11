/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

import server.Commands.GameCommand.*;
import server.Commands.ModelCommand.*;
import server.Connectivity.SocketHandler;
import server.Controller.Controller;
import server.Model.Player;

/**
 *
 * @author conno
 */
public abstract class ControllerCommand implements ICommand<Controller> {

    public static class StartServerCommand extends ControllerCommand {

        @Override
        public ResultCode execute(Controller commandHandler) {
            commandHandler.Start();
            return ResultCode.Success;
        }
    }

    public static class ProcessIncomingCommand extends ControllerCommand {

        private final String incoming;

        public ProcessIncomingCommand(String incoming) {
            this.incoming = incoming;
        }

        @Override
        public ResultCode execute(Controller commandHandler) {
            
            switch(incoming.substring(0, 4)){
                case "lgc-":
                    GameCommand g = new LeaveGameCommand(ExtractPlayerName(incoming));
                    commandHandler.GetCommandHandler().Handle(new PassGameCommandToGame(
                            g, ExtractGameID(incoming)));
                    break;
                default:
                    System.out.println("Dunno how to process this command code mate!");
                    break;
            }
            
            return ResultCode.Success;
        }
        
        private String ExtractGameID(String s){
            return s.substring(s.indexOf("-") + 1, s.indexOf("--"));
        }
        
        private String ExtractPlayerName(String s){
            return s.substring(s.indexOf("--") + 2);
        }
    }

    public static class PassToModelCommand extends ControllerCommand {

        private final Player p;
        private final SocketHandler sh;

        public PassToModelCommand(Player ID, SocketHandler sh) {
            this.p = ID;
            this.sh = sh;
        }

        @Override
        public ResultCode execute(Controller commandHandler) {
            commandHandler.GetCommandHandler().Handle(new FindGameCommand(p, sh));
            return ResultCode.Success;
        }
    }

    public static class AskModeForGamesListCommand extends ControllerCommand {

        @Override
        public ResultCode execute(Controller commandHandler) {
            commandHandler.GetCommandHandler().Handle(new PassGamesListToViewCommand());
            return ResultCode.Success;
        }
    }
}
