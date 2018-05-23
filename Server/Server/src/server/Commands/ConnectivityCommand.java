/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

import server.Connectivity.Connectivity;

/**
 *
 * @author conno
 */
  public abstract class ConnectivityCommand implements ICommand<Connectivity> {

      public static class SendMessageCommand extends ConnectivityCommand{

        @Override
        public ResultCode execute(Connectivity commandHandler) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
          
      }

    }
