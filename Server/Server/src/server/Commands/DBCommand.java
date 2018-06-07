/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

import server.Connectivity.SocketHandler;
import server.DB.DB;

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
            System.out.println("SignInCommand");
            if (commandHandler.CheckLoginInfo(ExtractUserName(logonCode),
                    ExtractPassword(logonCode))) {
                // get player code
                // pass to sh
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

        public SignUpCommand(SocketHandler sh, String logonCode) {
            this.sh = sh;
            this.logonCode = logonCode;
        }

        @Override
        public ResultCode execute(DB commandHandler) {
            //Attempt to add to Players Table
            if(commandHandler.AddPlayerToPlayersTable(ExtractScreenName(logonCode))){
                //add username, password and index of player to user_login
                System.out.println("Added player, now adding user");
                commandHandler.CheckSignUp(ExtractUserName(logonCode),
                    ExtractPassword(logonCode), commandHandler.GetPlayerIDByName(ExtractScreenName(logonCode)));
            }
            return ResultCode.Success;
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
