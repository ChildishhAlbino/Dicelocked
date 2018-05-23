/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

import server.Connectivity.*;

/**
 *
 * @author conno
 */
public abstract class ConnectivityCommand implements ICommand<Connectivity> {

    public static class SendMessageCommand extends ConnectivityCommand {

        private SocketHandler sh;
        private String message;

        public SendMessageCommand(SocketHandler sh, String message) {
            this.sh = sh;
            this.message = message;
        }

        @Override
        public ResultCode execute(Connectivity commandHandler) {
            sh.Send(message);
            return ResultCode.Success;
        }

    }

}
