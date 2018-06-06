/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

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
}
