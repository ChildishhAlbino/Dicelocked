using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Dicelocked.Commands;
using Dicelocked.GUI;

namespace Dicelocked.ServerClientConncetivity
{
    public class Controller : ICommandHandler<ControlCommands>
    {
        private ICommandHandler<ViewCommand> commandHandler;
        public ClientConnectivity connection;

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

        public void StartServer(int portNum)
        {
            connection = new ClientConnectivity();
        }

    }
}
