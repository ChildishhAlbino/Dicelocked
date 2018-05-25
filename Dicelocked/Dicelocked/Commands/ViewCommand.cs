
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
        private ViewCommand vc;
        public UpdateViewCommand(ViewCommand vc)
        {
            this.vc = vc;
        }
        public override Result execution(GUIView commandHandler)
        {
            commandHandler.AddToBacklog(vc);
            return Result.success;
        }
    }

    public class UpdateButtonTextCommand : ViewCommand
    {
        string buttonText;
        Button button;
        public UpdateButtonTextCommand(string buttonText, Button button)
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

    public class AskForNameCommand : ViewCommand
    {
        public override Result execution(GUIView commandHandler)
        {
            commandHandler.AskForName();
            return Result.success;
        }
    }
}
