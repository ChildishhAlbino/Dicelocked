
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;
using Dicelocked.GUI;
using Dicelocked.ServerClientConncetivity;

namespace Dicelocked.Commands
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
            commandHandler.UpdateGUI();
            return Result.success;
        }
    }

    public class UpdateButtonText : ViewCommand
    {
        string buttonText;
        Button button;
        public UpdateButtonText(string buttonText, Button button)
        {
            this.buttonText = buttonText;
            this.button = button;
        }

        public override Result execution(GUIView commandHandler)
        {
            commandHandler.UpdateButtonContent(button, buttonText);
            return Result.success;
        }
    }
}
