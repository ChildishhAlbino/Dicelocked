using Dicelocked.ServerClientConncetivity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace Dicelocked.Commands
{
    public abstract class ControllerCommand : ICommand<Controller>
    {
        public virtual Result execution(Controller commandHandler)
        {
            throw new NotImplementedException();
        }
    }

    public class AddIntCommand : ControllerCommand
    {
        private Button button;
        public AddIntCommand(Button button)
        {
            this.button = button;
        }

        private static int count = 0;

        public override Result execution(Controller commandHandler)
        {
            if (button != null)
            {
                count++;
                if (count == 4)
                {
                    commandHandler.handle(new StartServerCommand());
                }
                if (count >= 5)
                {
                    commandHandler.Send(count.ToString());
                }
                commandHandler.CommandHandler.handle(new UpdateButtonTextCommand($"Button has been pressed {count} time(s)", button));
            }
            return Result.success;
        }
    }

    public class StartServerCommand : ControllerCommand
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
            switch (received)
            {
                case "afi-n?":
                    commandHandler.CommandHandler.handle(new UpdateViewCommand(new AskForNameCommand()));
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

}
