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
        private string id = null;
        private string playerName = null;

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

        public string PlayerName
        {
            get => playerName;
        }

        public string ID
        {
            get => id;
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
            if(connection != null)
            {
                message += "\n";
                connection.Send((message));
            }    
        }

        public void SetID(string ID)
        {
            if (this.id == null)
            {
                this.id = ID;
                CommandHandler.handle(new UpdateViewCommand(new BeginWaitingCommand(id)));
            }
            else
            {
                Console.WriteLine(ID);
                Console.WriteLine("ID already set, thanks!");
            }
        }

        public void ClearID()
        {
            id = null;
            playerName = null;
        }

        public void SetPlayerName(string name)
        {
            if (this.playerName == null)
            {
                this.playerName = name;
            }
            else
            {
                Console.WriteLine(name);
                Console.WriteLine("PlayerName already set, thanks!");
            }
        }
        public void DisconnectFromServer()
        {
            if(connection != null)
            {
                connection.Disconnect();
                connection = null;
            } 
        }
    }
}
