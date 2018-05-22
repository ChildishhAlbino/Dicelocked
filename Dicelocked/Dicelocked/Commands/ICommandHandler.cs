using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dicelocked.Commands
{
    public interface ICommandHandler<U>
    {
        void handle(U command);

        
    }
}
