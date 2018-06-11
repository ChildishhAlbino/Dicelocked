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

    public void NewGame(Player p, SocketHandler sh) {
        Game game = new Game(BOARD_SIZE, this);
        games.add(game);
        game.Init();
        game.PlayerJoin(p, sh);
    }

    public void FindGame(Player p, SocketHandler sh) {
        if (games.isEmpty()) {
            System.out.println("There were no games, starting new one!");
            NewGame(p, sh);
        } else {
            for (Game game : games) {
                if (game.GotSpace()) {
                    System.out.println("Found a game, joining now!");
                    game.PlayerJoin(p, sh);
                    break;
                } else {
                    System.out.println("Couldn't find a waiting game! Creating new one :D");
                    NewGame(p, sh);
                    break;
                }
            }
        }
        SortGamesListByWaiting();
    }
    
    public void PassGameCommandToGame(String ID, GameCommand gc){
        Game g = GetGameByID(ID);
        System.out.println(ID);
        if(g != null){
            g.Handle(gc);
        }
        else{
            System.out.println("Couldn't find Game by ID");
        }
    }
    
    private Game GetGameByID(String ID){
        Game g = null;
        for(Game game : games){
            if(game.ID.equals(ID)){
                g = game;
            }
        }
        return g;
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
    
    public void RemoveGame(Game g){
        games.remove(g);
    }
}
