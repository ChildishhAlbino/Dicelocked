using Dicelocked.Commands;
using Dicelocked.GUI;
using Dicelocked.ServerClientConncetivity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Dicelocked
{
    /// <summary>
    /// Interaction logic for GUIView.xaml
    /// </summary>
    public partial class GUIView : Window, IView, ICommandHandler<ViewCommand>
    {
        private ICommandHandler<ControlCommands> commandHandler;
        public GUIView()
        {
            Controller controller = new Controller();
            controller.CommandHandler = this;
            this.CommandHandler = controller;
            InitializeComponent();
        }

        public ICommandHandler<ControlCommands> CommandHandler
        {
            get
            {
                return commandHandler;
            }

            set
            {
                commandHandler = value;
            }
        }
 
        private void OnButtonClick(object sender, RoutedEventArgs e)
        {
            if(sender is Button)
            {
                Button button = (Button)sender;
                CommandHandler.handle(new AddIntCommand(button));
            }
            else
            {
                Console.WriteLine("shit has hit the fan");
            }
        }

        public void UpdateGUI()
        {
           
        }

        public void handle(ViewCommand command)
        {
            if (!(command is null))
            {
                if (command.execution(this) != Result.success)
                {
                    //error
                    //do something
                }
            }
        }

        public void UpdateButtonContent(Button button, string content)
        {
            button.Content = content;
        }
    }
}
