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
using System.Timers;

namespace Dicelocked
{
    /// <summary>
    /// Interaction logic for GUIView.xaml
    /// </summary>
    public partial class GUIView : Window, IView, ICommandHandler<ViewCommand>
    {
        private ICommandHandler<ControllerCommand> commandHandler;
        private List<ViewCommand> commandBacklog;
        public GUIView()
        {
            Controller controller = new Controller();
            controller.CommandHandler = this;
            this.CommandHandler = controller;
            commandBacklog = new List<ViewCommand>();
            InitializeComponent();
        }

        public ICommandHandler<ControllerCommand> CommandHandler
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

            if (sender is Button)
            {
                Button button = (Button)sender;
                CommandHandler.handle(new AddIntCommand(button));
            }
            else
            {
                Console.WriteLine("shit has hit the fan");
            }
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

        protected override void OnClosing(System.ComponentModel.CancelEventArgs e)
        {
            // do something 
            base.OnClosing(e);
        }


        private void ClearBacklog()
        {
            lock (commandBacklog)
            {
                // do something
                foreach (ViewCommand vc in commandBacklog.ToList())
                {
                    if (vc.execution(this) != Result.failure)
                    {
                        // log
                    }
                    commandBacklog.Remove(vc);
                }
            }
        }

        protected override void OnMouseEnter(MouseEventArgs e)
        {
            base.OnMouseEnter(e);
        }

        protected override void OnMouseMove(MouseEventArgs e)
        {
            //Console.WriteLine(e.GetPosition(this).X.ToString());
            ClearBacklog();
            InvalidateVisual();
            base.OnMouseMove(e);
        }

        public void AddToBacklog(ViewCommand vc)
        {
            lock (commandBacklog)
            {
                commandBacklog.Add(vc);
                //InvalidateVisual();
            }
        }

        public void UpdateGUI()
        {
            throw new NotImplementedException();
        }

        private void ToggleButton_Click(object sender, RoutedEventArgs e)
        {
            if(sender is Button)
            {
                if (SignIn_Grid.IsVisible)
                {
                    SignIn_Grid.Visibility = Visibility.Collapsed;
                    SignUp_Grid.Visibility = Visibility.Visible;
                    ToggleButton.Content = "Sign In instead!";
                }
                else if (SignUp_Grid.IsVisible)
                {
                    SignUp_Grid.Visibility = Visibility.Collapsed;
                    SignIn_Grid.Visibility = Visibility.Visible;
                    ToggleButton.Content = "Sign Up instead!";
                }
            }
            else
            {
                Console.WriteLine("Shit has his the fan");
            }
        }
    }
}
