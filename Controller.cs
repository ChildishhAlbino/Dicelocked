using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Stacked_Grid_Client.Commands;
using Stacked_Grid_Client.GUI;

namespace Stacked_Grid_Client.ServerClientConncetivity
{
    public class Controller : ICommandHandler<ControlCommands>, IServerClientConnectivity
    {
        private ICommandHandler<ViewCommand> commandHandler;
        public ICommandHandler<ViewCommand> CommandHandler
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

        public ICommandHandler<Controller> GetCommandHandler()
        {
            throw new NotImplementedException();
        }

        public void handle(ControlCommands command)
        {
            if (!(command is null))
            {
                if (command.execution(this) == Result.success)
                {
                    CommandHandler.handle(new UpdateViewCommand());
                }
            }
        }
    }
}
