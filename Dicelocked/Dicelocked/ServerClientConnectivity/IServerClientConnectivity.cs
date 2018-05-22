using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dicelocked.ServerClientConncetivity
{
    public interface IServerClientConnectivity 
    {
        void Send(String data);
    }
}
