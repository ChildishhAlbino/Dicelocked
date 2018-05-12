using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace Client.GUI
{
    public interface IView
    {
        void UpdateGUI();

        void UpdateElementContent(ContentControl element, string content);
    }
}
