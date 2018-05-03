using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Stacked_Grid_Client.Commands;
using Stacked_Grid_Client.GUI;

namespace Stacked_Grid_Client.ServerClientConncetivity
{
    public class Controller : IComponent<Controller>, IServerClientConnectivity
    {
        private ICommandHandler<GUIView> commandHandler;
        public ICommandHandler<GUIView> CommandHandler
        {
            get
            {
                return commandHandler;
            }

            set
            {
                commandHandler = value;
            }
        }

        public void handle(ICommand<Controller> command)
        {
            if(!(command is null))
            {
                if (command.execution(this) == Result.success)
                {
                    CommandHandler.handle(new UpdateViewCommand());
                }             
            } 
        }
    }
}
