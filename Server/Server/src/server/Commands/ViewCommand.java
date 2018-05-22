/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

import server.View.View;

/**
 *
 * @author conno
 */
public abstract class ViewCommand implements ICommand<View> {

    public static class TestViewCommand extends ViewCommand {

        @Override
        public ResultCode execute(View commandHandler) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
}
