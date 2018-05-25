/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Model;

import java.util.ArrayList;
import java.util.List;
import server.Commands.*;
import server.Connectivity.SocketHandler;
import server.IComponent;

/**
 *
 * @author conno
 */
public class Model implements ICommandHandler<ModelCommand>, IComponent {

    public enum game_type {
        waiting, full
    }
    public ICommandHandler<ViewCommand> ch;
    private final int BOARD_SIZE = 3;

    public List<Game> waiting_games;
    public List<Game> full_games;

    public Model() {
        Start();
    }

    @Override
    public void Handle(ModelCommand command) {
        command.execute(this);
    }

    @Override
    public void Start() {
        waiting_games = new ArrayList<>();
        full_games = new ArrayList<>();
    }

    @Override
    public ICommandHandler GetCommandHandler() {
        return ch;
    }

    @Override
    public void SetCommandHandler(ICommandHandler ch) {
        this.ch = ch;
    }

    public void NewGame(String ID, SocketHandler sh) {
        Game game = new Game(BOARD_SIZE);
        waiting_games.add(game);
        game.Init();
        game.PlayerJoin(ID, sh);
    }

    public void FindGame(String ID, SocketHandler sh) {
        if (waiting_games == null || full_games == null) {
            Start();
        }
        if (waiting_games.isEmpty()) {
            System.out.println("No games found! Starting new one :D");
            NewGame(ID, sh);
        } else {
            for (int i = 0; i < waiting_games.size(); i++) {
                Game game = waiting_games.get(i);
                if (game.GotSpace()) {
                    System.out.println("Found a game!! Joining now!");
                    game.PlayerJoin(ID, sh);
                    if (!(game.GotSpace())) {
                        full_games.add(game);
                        waiting_games.remove(game);
                    }
                } else if (i >= waiting_games.size() - 1) {
                    System.out.println("No games had space for you :( Creating a new one!");
                    NewGame(ID, sh);
                    break;
                }
            }
        }
    }

}
