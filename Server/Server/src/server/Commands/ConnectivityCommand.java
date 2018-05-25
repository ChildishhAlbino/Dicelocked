/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

import server.Commands.ControllerCommand.PassToModelCommand;
import server.Connectivity.*;
import server.Model.Player;

/**
 *
 * @author conno
 */
public abstract class ConnectivityCommand implements ICommand<Connectivity> {

    public static class SendMessageCommand extends ConnectivityCommand {

        private final SocketHandler sh;
        private final String message;

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

    public static class ProcessIncomingMessageCommand extends ConnectivityCommand {

        private final String incoming;

        public ProcessIncomingMessageCommand(String incoming) {
            this.incoming = incoming;
        }

        @Override
        public ResultCode execute(Connectivity commandHandler) {
            commandHandler.Process(incoming);
            return ResultCode.Success;
        }
    }

    public static class PassToControllerCommand extends ConnectivityCommand {

        private final String ID;
        private final SocketHandler sh;

        public PassToControllerCommand(String ID, SocketHandler sh) {
            this.ID = ID;
            this.sh = sh;
        }

        @Override
        public ResultCode execute(Connectivity commandHandler) {
            commandHandler.GetCommandHandler().Handle(new PassToModelCommand(ID, sh));
            return ResultCode.Success;
        }
    }

    public static class AskForInputCommand extends ConnectivityCommand {
        public enum InputType{
            name,
            selection,
            move,
        }
        private final SocketHandler sh;
        private final InputType it;

        public AskForInputCommand(SocketHandler sh, InputType it) {
            this.sh = sh;
            this.it = it;
        }

        @Override
        public ResultCode execute(Connectivity commandHandler) {
            switch(it){
                case name:
                    sh.Send("afi-n/n");
                    break;
                case move:
                    sh.Send("afi-m/n");
                    break;
                case selection:
                    sh.Send("afi-sel/n");
            }
            return ResultCode.Success;
        }
    }
}
