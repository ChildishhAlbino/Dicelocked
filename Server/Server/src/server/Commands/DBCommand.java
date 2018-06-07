/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

import server.Connectivity.Connectivity;
import server.Connectivity.SocketHandler;
import server.DB.DB;
import server.Model.Player;

/**
 *
 * @author conno
 */
public abstract class DBCommand implements ICommand<DB> {

    public static class ConnectToDBCommand extends DBCommand {

        @Override
        public ResultCode execute(DB commandHandler) {
            return ResultCode.Success;
        }
    }

    public static class HashPasswordCommand extends DBCommand {

        private final String password;

        public HashPasswordCommand(String password) {
            this.password = password;
        }

        @Override
        public ResultCode execute(DB commandHandler) {
            System.out.println(commandHandler.Hash(password));
            return ResultCode.Success;
        }
    }

    public static class SignInCommand extends DBCommand {

        private final SocketHandler sh;
        private final String logonCode;

        public SignInCommand(SocketHandler sh, String logonCode) {
            this.sh = sh;
            this.logonCode = logonCode;
        }

        @Override
        public ResultCode execute(DB commandHandler) {
            //System.out.println("SignInCommand");
            if (commandHandler.CanSignIn(ExtractUserName(logonCode),
                    ExtractPassword(logonCode))) {
                // get player code
                int i = commandHandler.SignIn(ExtractUserName(logonCode),
                        ExtractPassword(logonCode));
                // pass to game
                String s = commandHandler.GetPlayerNameByID(i);
                if ((s) != null) {
                    Connectivity.GetInstance().Handle(
                            new ConnectivityCommand.PassToControllerCommand(
                                    new Player(i, s), sh));
                } else {
                    return ResultCode.Failure;
                }
                return ResultCode.Success;
            }
            return ResultCode.Failure;
        }

        private String ExtractUserName(String lc) {
            return lc.substring(lc.indexOf("-") + 1, lc.indexOf("--"));
        }

        private String ExtractPassword(String lc) {
            return lc.substring(lc.indexOf("--") + 2);
        }
    }

    public static class SignUpCommand extends DBCommand {

        private final SocketHandler sh;
        private final String logonCode;
        private boolean canProceed = true;

        public SignUpCommand(SocketHandler sh, String logonCode) {
            this.sh = sh;
            this.logonCode = logonCode;
        }

        @Override
        public ResultCode execute(DB commandHandler) {         
            if(commandHandler.UserExists(ExtractUserName(logonCode))){
                System.out.println("User already detected!");
                canProceed = false; // check if username in users db
            }
            if(commandHandler.GetPlayerIDByName(ExtractScreenName(logonCode)) != -1){
                System.out.println("Player already detected");
                canProceed = false; // check if name in players db
            }
            
            if(canProceed){
                // if not, begin signup process
                commandHandler.AddPlayerToPlayersTable(ExtractScreenName(logonCode));
                commandHandler.SignUpUser(ExtractUserName(logonCode), ExtractPassword(logonCode), 
                        commandHandler.GetPlayerIDByName(ExtractScreenName(logonCode)));
                return ResultCode.Success;
            }
            else{
                return ResultCode.Failure;
            } 
        }

        private String ExtractUserName(String lc) {
            return lc.substring(lc.indexOf("-") + 1, lc.indexOf("--"));
        }

        private String ExtractPassword(String lc) {
            return lc.substring(lc.indexOf("--") + 2, lc.indexOf("---"));
        }

        private String ExtractScreenName(String lc) {
            return lc.substring(lc.indexOf("---") + 3);
        }
    }
}
