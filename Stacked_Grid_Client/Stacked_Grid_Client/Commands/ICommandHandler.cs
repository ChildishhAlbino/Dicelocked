using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Stacked_Grid_Client.Commands
{
    public interface ICommandHandler<U>        
    {
        void handle(ICommand<U> command);

        
    }
}
