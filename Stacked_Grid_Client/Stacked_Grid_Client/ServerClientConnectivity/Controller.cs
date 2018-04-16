using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Stacked_Grid_Client.Commands;
using Stacked_Grid_Client.GUI;

namespace Stacked_Grid_Client.ServerClientConncetivity
{
    public class Controller : IComponent<Controller, GUIView>, IServerClientConnectivity
    {
        public ICommandHandler<GUIView> CommandHandler
        {
            get => throw new NotImplementedException();
            set => throw new NotImplementedException();
        }

        public void handle(ICommand<Controller> command)
        {
            throw new NotImplementedException();
        }
    }
}
