using Stacked_Grid_Client.ServerClientConncetivity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace Stacked_Grid_Client.Commands
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
        private ContentControl element;
        public AddIntCommand(ContentControl element)
        {
            this.element = element;
        }

        private static int count = 0;

        public override Result execution(Controller commandHandler)
        {
            if(element != null)
            {
                count++;
                commandHandler.CommandHandler.handle(new UpdateButtonText($"Button has been pressed {count} time(s)", element));
            }
            return Result.success;
        }  
    }


}
