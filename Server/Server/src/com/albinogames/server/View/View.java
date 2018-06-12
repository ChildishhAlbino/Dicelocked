/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinogames.server.View;

import com.albinogames.server.Commands.ViewCommand;
import com.albinogames.server.Commands.ICommandHandler;
import com.albinogames.server.Commands.ControllerCommand;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.albinogames.server.Commands.ControllerCommand.AskModeForGamesListCommand;
import com.albinogames.server.Commands.ControllerCommand.StartServerCommand;
import com.albinogames.server.IComponent;
import com.albinogames.server.Model.Game;
import com.albinogames.server.Model.Model.*;

/**
 *
 * @author conno
 */
public class View implements ICommandHandler<ViewCommand>, IComponent {

    private ICommandHandler<ControllerCommand> ch;
    private Scanner input;

    @Override
    public void Handle(ViewCommand command) {
        command.execute(this);
    }

    @Override
    public void SetCommandHandler(ICommandHandler ch) {
        this.ch = ch;
    }

    @Override
    public void Start() {
        System.out.println("Welcome to your DiceLocked Server Console!");
        input = new Scanner(System.in);
        ch.Handle(new StartServerCommand());
        while (true) {
            ch.Handle(new AskModeForGamesListCommand());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void PrintGamesList(List<Game> games) {
        if (games != null) {
            if (games.isEmpty()) {
                System.out.println("No games rn!\n");
                return;
            }
            System.out.println(games.size() + " games in session!");
            for (int i = 0; i < games.size(); i++) {
                Game game = games.get(i);
                PrintGame(game);
            }
        }
    }

    public void PrintGame(Game game) {
        if (game != null) {
            String str = "";
            str += game.toString();
            System.out.println(str);
        }
    }

    @Override
    public ICommandHandler GetCommandHandler() {
        return ch;
    }

}
