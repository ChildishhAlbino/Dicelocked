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
    public partial class GUIView : Window, IView, IComponent<GUIView, Controller>
    {
        public GUIView()
        {
            InitializeComponent();
        }

        public ICommandHandler<Controller> CommandHandler { get => throw new NotImplementedException(); set => throw new NotImplementedException(); }

        public void handle(ICommand<GUIView> command)
        {
            throw new NotImplementedException();
        }
    }
}
