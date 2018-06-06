using Dicelocked.ServerClientConncetivity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace Dicelocked.Commands
{
    public enum LoginType
    {
        Sign_Up,
        Sign_In,
    }

    public abstract class ControllerCommand : ICommand<Controller>
    {
        public virtual Result execution(Controller commandHandler)
        {
            throw new NotImplementedException();
        }
    }

    public class ConnectToServerCommand : ControllerCommand
    {
        public override Result execution(Controller commandHandler)
        {
            commandHandler.StartConnecting(11000);
            return Result.success;
        }
    }

    public class ParseRecievedCommand : ControllerCommand
    {
        private string received;
        public ParseRecievedCommand(String received)
        {
            this.received = received;
            Cleanup();
        }
        public override Result execution(Controller commandHandler)
        {
            Console.WriteLine("Command executing");
            switch (received.Substring(0, 4))
            {
                case "afi-":
                    switch (received.Substring(received.IndexOf("-") + 1))
                    {
                        case "n?":
                            commandHandler.CommandHandler.handle(new UpdateViewCommand(new AskForNameCommand()));
                            break;
                    }
                    break;
                case "sgi-":
                    string s;
                    s = received.Substring(received.IndexOf("-") + 1, 6);
                    Console.WriteLine(s);
                    commandHandler.handle(new SetGameIDCommand(s));
                    break;

            }
            return Result.success;
        }
        private void Cleanup()
        {
            if (received.Contains("\r\n"))
            {
                received = received.Substring(0, received.IndexOf("\r\n"));
            }
        }
    }

    public class SetGameIDCommand : ControllerCommand
    {
        private string ID;

        public SetGameIDCommand(string ID)
        {
            this.ID = ID;
        }

        public override Result execution(Controller commandHandler)
        {
            commandHandler.SetID(ID);
            return Result.success;
        }
    }

    public class SendMessageCommand : ControllerCommand
    {
        private string message;
        public SendMessageCommand(String message)
        {
            this.message = message;
        }
        public override Result execution(Controller commandHandler)
        {
            commandHandler.Send(message);
            return Result.success;
        }
    }

    public class LeaveGameCommand : ControllerCommand
    {
        public override Result execution(Controller commandHandler)
        {
            commandHandler.Send("lgc-");
            commandHandler.ClearID();
            return Result.success;
        }
    }

    public class SendLogonInfoCommand : ControllerCommand
    {
        private string s;
       
        private LoginType l;
        public SendLogonInfoCommand(string s, LoginType l)
        {
            this.s = s;
            this.l = l;
        }
        public override Result execution(Controller commandHandler)
        {
            string send = null;
            switch (l)
            {
                case LoginType.Sign_In:
                    send = $"sii-{s}";
                    break;

                case LoginType.Sign_Up:
                    send = $"sui-{s}";
                    break;
                default:
                    //error - log this
                    break;
            }
            if(send != null)
            {
                commandHandler.Send(send);
                return Result.success;
            }
            return Result.failure;
        }
    }
}
