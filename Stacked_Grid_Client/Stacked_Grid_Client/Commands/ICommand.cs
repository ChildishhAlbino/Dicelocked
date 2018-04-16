using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Stacked_Grid_Client.Commands
{
    public enum Result
    {
        success, failure
    }
    public interface ICommand<T>
    {
        Result execution(T commandHandler);
    }
}
