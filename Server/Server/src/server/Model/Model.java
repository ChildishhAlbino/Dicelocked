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

//    public enum game_type {
//        waiting, full
//    }
    public ICommandHandler<ViewCommand> ch;
    private final int BOARD_SIZE = 3;

//    public List<Game> waiting_games;
//    public List<Game> full_games;
    public List<Game> games;

    //public Map<String,Game> waiting_games_id; ask roland if i should replace with map
    //public Map<String,Game> full_games_id;
    public Model() {
//        waiting_games = new ArrayList<>();
//        full_games = new ArrayList<>();
        games = new ArrayList<>();
    }

    @Override
    public void Handle(ModelCommand command) {
        command.execute(this);
    }

    @Override
    public void Start() {

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
        games.add(game);
        game.Init();
        game.PlayerJoin(ID, sh);
    }

    public void FindGame(String ID, SocketHandler sh) {
        if (games.isEmpty()) {
            System.out.println("There were no games, starting new one!");
            NewGame(ID, sh);
        } else {
            for (Game game : games) {
                if (game.GotSpace()) {
                    System.out.println("Found a game, joining now!");
                    game.PlayerJoin(ID, sh);
                    break;
                }
            }
            System.out.println("Couldn't find a waiting game! Creating new one :D");
            NewGame(ID, sh);
        }
        SortGamesListByWaiting();
    }

    public void SortGamesListByWaiting() {
        games.sort((Game o1, Game o2) -> {
            if (o1.GotSpace() && !o2.GotSpace()) {
                return -1;
            } else if (!o1.GotSpace() && o2.GotSpace()) {
                return 1;
            } else {
                return 0;
            }
        });
    }
}
