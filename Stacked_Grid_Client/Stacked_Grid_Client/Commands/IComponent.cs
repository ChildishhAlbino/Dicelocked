using Stacked_Grid_Client.ServerClientConncetivity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Stacked_Grid_Client.Commands
{
    public interface IComponent<U> : ICommandHandler<U>   
    {

    }
}
