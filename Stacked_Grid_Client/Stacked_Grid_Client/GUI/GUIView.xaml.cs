using Stacked_Grid_Client.Commands;
using Stacked_Grid_Client.GUI;
using Stacked_Grid_Client.ServerClientConncetivity;
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

namespace Stacked_Grid_Client
{
    /// <summary>
    /// Interaction logic for GUIView.xaml
    /// </summary>
    public partial class GUIView : Window, IView, ICommandHandler<GUIView>
    {
        private ICommandHandler<Controller> commandHandler;
        public GUIView()
        {
            Controller controller = new Controller();
            controller.CommandHandler = this;
            this.CommandHandler = controller;
            InitializeComponent();
        }
        

        public ICommandHandler<Controller> CommandHandler
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

        public void handle(ICommand<GUIView> command)
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

        private void OnButtonClick(object sender, RoutedEventArgs e)
        {
            commandHandler.handle(new AddIntCommand((ContentControl)sender));
        }

        public void UpdateGUI()
        {
           
        }

        public void UpdateElementContent(ContentControl element, string content)
        {
            throw new NotImplementedException();
        }
    }
}
