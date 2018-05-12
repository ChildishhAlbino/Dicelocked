
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;
using Stacked_Grid_Client.GUI;
using Stacked_Grid_Client.ServerClientConncetivity;

namespace Stacked_Grid_Client.Commands
{
    public abstract class ViewCommand : ICommand<GUIView>
    {
        public virtual Result execution(GUIView commandHandler)
        {
            throw new NotImplementedException();
        }
    }

    public class UpdateViewCommand : ViewCommand        
    {
        public override Result execution(GUIView commandHandler)
        {
            return Result.success;
        }
    }

    public class UpdateButtonText : ViewCommand
    {
        string buttonText;
        ContentControl element;
        public UpdateButtonText(string buttonText, ContentControl element)
        {
            this.buttonText = buttonText;
            this.element = element;
        }

        public override Result execution(GUIView commandHandler)
        {
            element.Content = buttonText;
            return Result.success;
        }
    }
}
