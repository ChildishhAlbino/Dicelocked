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

    public ICommandHandler<ViewCommand> ch;
    private final int BOARD_SIZE = 3;

    public List<Game> games;

    @Override
    public void Handle(ModelCommand command) {
        command.execute(this);
    }

    @Override
    public void Start() {
        games = new ArrayList<>();
    }

    @Override
    public ICommandHandler GetCommandHandler() {
        return ch;
    }

    @Override
    public void SetCommandHandler(ICommandHandler ch) {
        this.ch = ch;
    }

    public void NewGame(Player player, SocketHandler sh) {
        Game game = new Game(BOARD_SIZE);
        games.add(game);
        game.Init();
        game.PlayerJoin(player, sh);
    }

    public void FindGame(Player player, SocketHandler sh) {
        if(games == null){
            Start();
        }
        if (games.isEmpty()) {
            System.out.println("No games found! Starting new one :D");
            NewGame(player, sh);
        } else {
            for (int i = 0; i < games.size(); i++) {
                Game game = games.get(i);
            
                if (game.GotSpace()) {
                    System.out.println("Found a game!! Joining now!");
                    game.PlayerJoin(player, sh);
                } else if (i >= games.size() - 1) {
                    System.out.println("No games had space for you :( Creating a new one!");
                    NewGame(player, sh);
                    break;
                }
            }
        }
    }

}
