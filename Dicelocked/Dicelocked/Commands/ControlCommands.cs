using Dicelocked.ServerClientConncetivity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace Dicelocked.Commands
{
    public abstract class ControlCommands : ICommand<Controller>
    {
        public virtual Result execution(Controller commandHandler)
        {
            throw new NotImplementedException();
        }
    }

    public class AddIntCommand : ControlCommands
    {
        private Button button;
        public AddIntCommand(Button button)
        {
            this.button = button;
        }

        private static int count = 0;

        public override Result execution(Controller commandHandler)
        {
            if(button != null)
            {
                count++;
                if(count == 4)
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

    public class StartServerCommand : ControlCommands
    {
        public override Result execution(Controller commandHandler)
        {
            commandHandler.StartServer(11000);
            return Result.success;
        }
    }


}
