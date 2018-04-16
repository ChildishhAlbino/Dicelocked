using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Stacked_Grid_Client.GUI;
namespace Stacked_Grid_Client.Commands
{
    public abstract class ViewCommand<T> : ICommand<T>
        where T : IView
    {
        public virtual Result execution(T commandHandler)
        {
            return Result.success;
        }
    }

    public class UpdateViewCommand<T> : ViewCommand<T>
        where T : IView
    {
        public override Result execution(T commandHandler)
        {
            
            return base.execution(commandHandler);
        }
    }
}
