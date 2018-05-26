/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.View;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Commands.*;
import server.Commands.ControllerCommand.AskModeForGamesListCommand;
import server.Commands.ControllerCommand.StartServerCommand;
import server.IComponent;
import server.Model.Game;
import server.Model.Model.*;

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
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void PrintGamesList(List<Game> games, game_type gt) {
        if (games != null) {
            if (games.isEmpty()) {
                System.out.println("No " + gt.toString() + " games rn!\n");
                return;
            }
            System.out.println(gt.toString().toUpperCase() + ":\n");
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
