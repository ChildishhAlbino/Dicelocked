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
using System.Windows.Threading;

namespace Dicelocked
{
    /// <summary>
    /// Interaction logic for GUIView.xaml
    /// </summary>
    public partial class GUIView : Window, IView, ICommandHandler<ViewCommand>
    {
        /// <summary>
        /// private variable for commandHandler;
        /// </summary>
        private ICommandHandler<ControllerCommand> commandHandler;
        private bool PanelState = false;
        private double boxSize;
        private enum GridType
        {
            Sign_Up,
            Sign_In,
        }

        private DispatcherTimer dt;
        private List<ViewCommand> commandBacklog;
        public GUIView()
        {
            Controller controller = new Controller();
            controller.CommandHandler = this;
            this.CommandHandler = controller;
            commandBacklog = new List<ViewCommand>();
            SetUpTimer();
            InitializeComponent();
        }

        private double CalculateBoxSize()
        {
            double x = 0;
            x = Height / 6;
            return x;
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

        /// <summary>
        /// handle method taken from ICommandHandler interface
        /// </summary>
        /// <param name="command">The ViewCommand to be executed</param>
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
        /// <summary>
        /// method that intiates the timer than refreshes the window
        /// </summary>
        public void SetUpTimer()
        {
            dt = new DispatcherTimer();
            dt.Interval = TimeSpan.FromMilliseconds(1 / 30);
            dt.Tick += timer_Tick;
            dt.Start();
        }
        /// <summary>
        /// the event handler for timer
        /// </summary>
        /// <param name="sender">the object that called the event</param>
        /// <param name="e">an event args</param>
        private void timer_Tick(object sender, EventArgs e)
        {
            ClearBacklog();
            InvalidateVisual();
        }
        /// <summary>
        /// override of the OnClosing event of the window
        /// </summary>
        /// <param name="e"></param>
        protected override void OnClosing(System.ComponentModel.CancelEventArgs e)
        {
            // do something 
            CommandHandler.handle(new LeaveGameCommand());
            base.OnClosing(e);
        }

        /// <summary>
        /// Method that checks and locks the the ViewCommand backlog
        /// and clears it through a foreach loop
        /// </summary>
        public void ClearBacklog()
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
        /// <summary>
        /// Method to add a ViewCommand to backlog
        /// </summary>
        /// <param name="vc">the viewCommand to be added to the backlog</param>
        public void AddToBacklog(ViewCommand vc)
        {
            lock (commandBacklog)
            {
                commandBacklog.Add(vc);
                //InvalidateVisual();
            }
        }
        /// <summary>
        /// Method that the toggle button calls to switch between the
        /// sign-up and sign-in screens
        /// </summary>
        /// <param name="sender">The toggle button</param>
        /// <param name="e">The event args</param>
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
        /// <summary>
        /// Takes in an enum value and clears the text boxes
        /// on a given panel
        /// </summary>
        /// <param name="g">The panel type</param>
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
        /// <summary>
        /// Method that generates a Sign-In code for the server
        /// </summary>
        /// <returns></returns>
        private string SignInGoButtonPressed()
        {
            if (UsernameSI_Textbox.Text != "" && PasswordSI_Textbox.Text != "")
            {
                string s = GenerateSignInCode(UsernameSI_Textbox.Text, HashPassword(PasswordSI_Textbox.Text));
                ClearTextboxes(GridType.Sign_In);
                return s;
            }
            return null;
        }
        /// <summary>
        /// Method that returns a Sign-Up code for the server
        /// Also clears the text 
        /// </summary>
        /// <returns></returns>
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
        /// <summary>
        /// Appends two string together in a specific format,
        /// Generates a SignInCode
        /// </summary>
        /// <param name="s1">the first argument; in this case the username</param>
        /// <param name="s2">the second argument, in this case the password hash</param>
        /// <returns></returns>
        private string GenerateSignInCode(string s1, string s2)
        {
            return $"{s1}--{s2}";
        }
        /// <summary>
        /// Appends three strings together in a specific format,
        /// Generates a SignUpCode
        /// </summary>
        /// <param name="s1">the first argument; in this case the username</param>
        /// <param name="s2">the second argument, in this case the password hash</param>
        /// <param name="s3">the third argument, in this case the screen name</param>
        /// <returns></returns>
        private string GenerateSignUpCode(string s1, string s2, string s3)
        {
            return $"{s1}--{s2}---{s3}";
        }
        /// <summary>
        /// takes in a string and returns it's SHA256 hash
        /// </summary>
        /// <param name="toBeHashed">the string you want to hash</param>
        /// <returns></returns>
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
        /// <summary>
        /// The event called by the GO! Button on the window
        /// sets an enum type 'l' to a value based on which panel is visible
        /// also calls for a sign-up or sign-in code to be generated
        /// if this is not null, it will begin the process of talking to the server and 
        /// logging in or signing up the user
        /// </summary>
        /// <param name="sender">the object calling the event</param>
        /// <param name="e">the event args</param>
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
            if (userPlusHash != null)
            {
                Console.WriteLine(userPlusHash);
                CommandHandler.handle(new ConnectToServerCommand());
                CommandHandler.handle(new SendLogonInfoCommand(userPlusHash, l));
            }
        }
        /// <summary>
        /// The event called by the LeaveGameButton on the window
        /// sends a LeaveGameCode to the server and returns the user to the signin screen
        /// </summary>
        /// <param name="sender">the object calling the event</param>
        /// <param name="e">the event args</param>
        private void LeaveGameButton_Click(object sender, RoutedEventArgs e)
        {
            CommandHandler.handle(new LeaveGameCommand());
            OpenPreGamePanel();
        }
        /// <summary>
        /// a method that sets the visibility of the waiting game panel to visible 
        /// and collapses the pregame panel
        /// </summary>
        /// <param name="id">the game ID you want to add</param>
        public void OpenWaitingPanel(string id)
        {
            PreGame_Panel.Visibility = Visibility.Collapsed;
            GAMEID_Text.Text = $"GAME ID: {id}";
            Waiting_Panel.Visibility = Visibility.Visible;
        }
        /// <summary>
        /// a method that sets the visibility of the waiting game panel to collapsed
        /// and makes the pregame panel visible
        /// </summary>
        public void OpenPreGamePanel()
        {
            PreGame_Panel.Visibility = Visibility.Visible;
            Waiting_Panel.Visibility = Visibility.Collapsed;
        }
        /// <summary>
        /// The event handler for the toggle button on click event
        /// </summary>
        /// <param name="sender">The object that called this event</param>
        /// <param name="e">The event args</param>
        private void ToggleColoursButton_Click(object sender, RoutedEventArgs e)
        {
            ToggleColours();
        }
        /// <summary>
        /// The method that toggles the background colours
        /// Checks a boolean value and does one of the two statements
        /// Each statement inverts the colours between DarkRed and DarkCyan
        /// The last line inverts the boolean so that will keep toggling.
        /// </summary>
        private void ToggleColours()
        {
            if (PanelState)
            {
                SignUp_Grid.Background = new SolidColorBrush(Colors.DarkRed);
                SignIn_Grid.Background = new SolidColorBrush(Colors.DarkCyan);
            }
            else
            {
                SignUp_Grid.Background = new SolidColorBrush(Colors.DarkCyan);
                SignIn_Grid.Background = new SolidColorBrush(Colors.DarkRed);
            }
            PanelState = !PanelState;
        }
        /// <summary>
        /// Runs through all the smaller parts of drawing the GUI
        /// shows the game grid
        /// Draws both player boards
        /// TODO: draw healthbars
        /// </summary>
        private void DrawGUI()
        {
            boxSize = CalculateBoxSize();
            // set the Canvas to the correct grid
            ShowGameGrid();
            // draw the players board on the left of the screen
            DrawPlayerBoard();
            // draw the opponents board on the right of the screen
        }

        private void DrawPlayerBoard()
        {
            float x = (float)(0.12f * Width);
            float y = (float)(1.5f * boxSize);
            DrawBoard(x, y);
        }

        private void DrawBoard(float x, float y)
        {
            DrawGridSection(x, y, string.Empty);
            // iteratively move accross the screen drawing squares of boxSize 9 times
            // makes a 3x3 grid
        }
        private void DrawGridSection(float x, float y, string s)
        {
            DrawSquare();
            PositionSquare(s, x, y);
        }
        private void DrawSquare()
        {
            Rectangle r = new Rectangle();
            r.Width = boxSize;
            r.Height = boxSize;
            r.Name = "PlayerRectangle1";
            r.Fill = new SolidColorBrush(Colors.Transparent);
            r.Stroke = new SolidColorBrush(Colors.Black);
            r.StrokeThickness = 4;
            Game_Grid.Children.Add(r);
        }
        private void PositionSquare(string s, float x, float y)
        {
            ///find square by name (string s)
            ///move it to the position given
            ///DONE
            //throw new NotImplementedException();
        }
        /// <summary>
        /// Collapses the WaitingGamePanel and makes the GameGrid visibile
        /// </summary>
        private void ShowGameGrid()
        {
            Waiting_Panel.Visibility = Visibility.Collapsed;
            Game_Grid.Visibility = Visibility.Visible;
        }
        /// <summary>
        /// Event handler for the AIGamebutton on click event
        /// Calls DrawGUI
        /// </summary>
        /// <param name="sender">The object who called the event</param>
        /// <param name="e">The event args</param>
        private void AIGameButton_Click(object sender, RoutedEventArgs e)
        {
            DrawGUI();
        }
    }
}
