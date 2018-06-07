/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

import server.Commands.ControllerCommand.PassToModelCommand;
import server.Commands.DBCommand.*;
import server.Connectivity.*;
import server.Connectivity.Connectivity.LoginType;
import server.DB.DB;
import server.Model.Player;

/**
 *
 * @author conno
 */
public abstract class ConnectivityCommand implements ICommand<Connectivity> {

    public static class StartupCommand extends ConnectivityCommand {

        private final ICommandHandler<ControllerCommand> ch;

        public StartupCommand(ICommandHandler<ControllerCommand> ch) {
            this.ch = ch;
        }

        @Override
        public ResultCode execute(Connectivity commandHandler) {
            commandHandler.SetCommandHandler(ch);
            commandHandler.start();
            return ResultCode.Success;
        }
    }

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

        private final Player p;
        private final SocketHandler sh;

        public PassToControllerCommand(Player p, SocketHandler sh) {
            this.sh = sh;
            this.p = p;
        }

        @Override
        public ResultCode execute(Connectivity commandHandler) {
            commandHandler.GetCommandHandler().Handle(new PassToModelCommand(p, sh));
            return ResultCode.Success;
        }
    }

    public static class AskForInputCommand extends ConnectivityCommand {

        public enum InputType {
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
            switch (it) {
                case move:
                    sh.Send("afi-m");
                    break;
                case selection:
                    sh.Send("afi-sel");
            }
            return ResultCode.Success;
        }
    }

    public static class SendGameIDCommand extends ConnectivityCommand {

        private final SocketHandler sh;
        private final String ID;

        public SendGameIDCommand(SocketHandler sh, String ID) {
            this.sh = sh;
            this.ID = ID;
        }

        @Override
        public ResultCode execute(Connectivity commandHandler) {
            String s = "sgi-" + ID;
            System.out.println(s);
            sh.Send(s);
            return ResultCode.Success;
        }
    }

    public static class ParseLogonAttempt extends ConnectivityCommand {

        private final SocketHandler sh;
        private final String logonCode;

        public ParseLogonAttempt(SocketHandler sh, String logonCode) {
            this.logonCode = logonCode;
            this.sh = sh;
        }

        @Override
        public ResultCode execute(Connectivity commandHandler) {
            //System.out.println("LogonAttemptParse");
            LoginType l = commandHandler.ParseLogonCode(logonCode);

            if (l == LoginType.sign_in) {
                DB.GetDB().Handle(new SignInCommand(sh, logonCode));
            } else if (l == LoginType.sign_up) {
                DB.GetDB().Handle(new SignUpCommand(sh, logonCode));
            } else {
                System.out.println("Error: Login type wrong");
                return ResultCode.Failure;
            }
            return ResultCode.Success;
        }
    }
}
