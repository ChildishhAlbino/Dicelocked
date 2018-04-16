using Stacked_Grid_Client.ServerClientConncetivity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Stacked_Grid_Client.Commands
{
    public abstract class ControlCommands<T> : ICommand<T>
        where T : IServerClientConnectivity
    {
        public virtual Result execution(T commandHandler)
        {
            throw new NotImplementedException();
        }
    }

    
}
