using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Dicelocked.Commands;
using Dicelocked.GUI;

namespace Dicelocked.ServerClientConncetivity
{
    public class Controller : ICommandHandler<ControllerCommand>
    {
        private ICommandHandler<ViewCommand> commandHandler;
        public ClientConnectivity connection;
        private string ID = null;

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

        public void handle(ControllerCommand command)
        {
            if (!(command is null))
            {
                if (command.execution(this) == Result.success)
                {
                    // log command
                }
            }
        }

        public void StartConnecting(int portNum)
        {
            connection = new ClientConnectivity();
            connection.CommandHandler = this;
        }

        public void Send(string message)
        {
            message += "\n";
            connection.Send((message));
        }

        public void SetID(string ID)
        {
            if(this.ID == null)
            {
                this.ID = ID;
            }
            else
            {
                Console.WriteLine(ID);
                Console.WriteLine("ID already set, thanks!");
            }
        }

        public void ClearID()
        {
            ID = null;
        }
    }
}
