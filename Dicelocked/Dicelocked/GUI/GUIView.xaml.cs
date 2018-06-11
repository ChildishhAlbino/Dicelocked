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
using System.Security.Cryptography;

namespace Dicelocked
{
    /// <summary>
    /// Interaction logic for GUIView.xaml
    /// </summary>
    public partial class GUIView : Window, IView, ICommandHandler<ViewCommand>
    {
        private ICommandHandler<ControllerCommand> commandHandler;
        private enum GridType
        {
            Sign_Up,
            Sign_In,
        }
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
            if (sender is Button)
            {
                if (SignIn_Grid.IsVisible)
                {
                    SignIn_Grid.Visibility = Visibility.Collapsed;
                    SignUp_Grid.Visibility = Visibility.Visible;
                    ClearTextboxes(GridType.Sign_In);
                    ToggleButton.Content = "Sign In instead!";
                }
                else if (SignUp_Grid.IsVisible)
                {
                    SignUp_Grid.Visibility = Visibility.Collapsed;
                    SignIn_Grid.Visibility = Visibility.Visible;
                    ClearTextboxes(GridType.Sign_Up);
                    ToggleButton.Content = "Sign Up instead!";
                }
            }
            else
            {
                Console.WriteLine("Shit has his the fan");
            }
        }
        private void ClearTextboxes(GridType g)
        {
            switch (g)
            {
                case GridType.Sign_In:
                    UsernameSI_Textbox.Clear();
                    PasswordSI_Textbox.Clear();
                    break;
                case GridType.Sign_Up:
                    UsernameSU_Textbox.Clear();
                    PasswordSU_Textbox.Clear();
                    ScreenNameSU_Textbox.Clear();
                    break;
                default:
                    break;
            }
        }
        private string SignInGoButtonPressed()
        {
            if (UsernameSI_Textbox.Text != "" && PasswordSI_Textbox.Text != "")
            {
                string s = GeneratePlayerPlusHash(UsernameSI_Textbox.Text, HashPassword(PasswordSI_Textbox.Text));
                ClearTextboxes(GridType.Sign_In);
                return s;
            }
            return null;
        }

        private string SignUpGoButtonPressed()
        {
            if (UsernameSU_Textbox.Text != "" && PasswordSU_Textbox.Text != "" && ScreenNameSU_Textbox.Text != "")
            {
                string s = GenerateSignUpCode(UsernameSU_Textbox.Text, HashPassword(PasswordSU_Textbox.Text), ScreenNameSU_Textbox.Text);
                ClearTextboxes(GridType.Sign_Up);
                return s;
            }
            return null;
        }

        private string GeneratePlayerPlusHash(string s1, string s2)
        {
            return $"{s1}--{s2}";
        }

        private string GenerateSignUpCode(string s1, string s2, string s3)
        {
            return $"{s1}--{s2}---{s3}";
        }

        private string HashPassword(string toBeHashed)
        {
            byte[] bytes = Encoding.ASCII.GetBytes(toBeHashed);
            SHA256Managed sha = new SHA256Managed();
            byte[] hashBytes = sha.ComputeHash(bytes);
            StringBuilder sb = new StringBuilder();
            foreach (byte b in hashBytes)
            {
                sb.AppendFormat("{0:x2}", b);
            }
            return sb.ToString();
        }

        private void GOButton_Click(object sender, RoutedEventArgs e)
        {
            string userPlusHash = string.Empty;
            LoginType l = default(LoginType);
            if (SignUp_Grid.IsVisible)
            {
                userPlusHash = SignUpGoButtonPressed();
                l = LoginType.Sign_Up;
            }
            else if (SignIn_Grid.IsVisible)
            {
                userPlusHash = SignInGoButtonPressed();
                l = LoginType.Sign_In;
            }
            if(userPlusHash != null)
            {
                Console.WriteLine(userPlusHash);
                CommandHandler.handle(new ConnectToServerCommand());
                CommandHandler.handle(new SendLogonInfoCommand(userPlusHash, l));
            }
        }

        private void LeaveGameButton_Click(object sender, RoutedEventArgs e)
        {
            CommandHandler.handle(new LeaveGameCommand());
        }

        public void OpenWaitingPanel(string ID)
        {
            PreGame_Panel.Visibility = Visibility.Collapsed;
            GAMEID_Text.Text += ID;
            Waiting_Panel.Visibility = Visibility.Visible;
        }
    }
}
