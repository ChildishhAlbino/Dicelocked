/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinogames.server.Commands;

import java.util.List;
import com.albinogames.server.Model.Game;
import com.albinogames.server.Model.Model.*;
import com.albinogames.server.View.View;

/**
 *
 * @author conno
 */
public abstract class ViewCommand implements ICommand<View> {

    public static class PrintToViewCommand extends ViewCommand {

        private String toBePrinted;

        public PrintToViewCommand(String toBePrinted) {
            this.toBePrinted = toBePrinted;
        }

        @Override
        public ResultCode execute(View commandHandler) {
            System.out.println(toBePrinted);
            return ResultCode.Success;
        }
    }

    public static class PrintGamesListCommand extends ViewCommand {

        private final List<Game> games;

        public PrintGamesListCommand(List<Game> games) {
            this.games = games;
        }

        @Override
        public ResultCode execute(View commandHandler) {
            commandHandler.PrintGamesList(games);
            return ResultCode.Success;
        }

    }
}
