/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import server.Commands.*;
import server.Commands.ConnectivityCommand.*;
import server.Commands.ICommand.ResultCode;
import server.Commands.ModelCommand.*;
import server.Connectivity.*;

/**
 *
 * @author conno
 */
public class Game implements ICommandHandler<GameCommand> {

    private List<Board> boards;
    private HashMap<Integer, Player> players;
    private HashMap<Player, SocketHandler> playerToSocket;
    private final int MAX_PLAYERS = 2;
    private final int BOARD_SIZE;
    private ICommandHandler<ModelCommand> ch;
    private boolean full = false;

    public final String ID;

    public Game(int BOARD_SIZE, ICommandHandler<ModelCommand> ch) {
        this.BOARD_SIZE = BOARD_SIZE;
        this.ID = Identification.ID.GenerateID_Number(6);
        this.ch = ch;
    }

    public void Init() {
        boards = new ArrayList<>(); // initializes list of board
        playerToSocket = new HashMap<>();
        players = new HashMap<>();
        for (int i = 0; i < MAX_PLAYERS; i++) {
            boards.add(new Board(BOARD_SIZE, i + 1)); // creates a board for each of the players at a given size
        }
    }

    public void ShutdownGame() {
        Identification.ID.RemoveID(ID);
        ch.Handle(new RemoveGameCommand(this));
        playerToSocket.clear();
        players.clear();
    }

    @Override
    public void Handle(GameCommand command) {
        if (command.execute(this) != ResultCode.Failure) {
            // log command
        }
    }

    public void SetCommandHandler(ICommandHandler ch) {
        this.ch = ch;
    }

    public ICommandHandler GetCommandHandler() {
        return ch;
    }

    public void PlayerJoin(Player p, SocketHandler sh) {

        playerToSocket.put(p, sh);
        players.put(p.GetID(), p);
        p.SetCommandHandler(this);
        if (players.size() == MAX_PLAYERS) {
            full = true;
        }
        SendGameID(p);
    }

    public void PlayerLeave(String name) {
        Player p = GetPlayerByName(name);
        playerToSocket.get(p).Shutdown();
        playerToSocket.remove(p);
        players.remove(p.GetID());
        if (players.isEmpty()) {
            ShutdownGame();
        }
        else if (players.size() < MAX_PLAYERS) {
            // ask the other player if they'd like to requeue for another game
            // reset the game and make it waiting
            full = false;
        }
    }
    
    public Player GetPlayerByName(String name){
        Player p = null;
        for(Player player : players.values()){
            if(player.GetName().equals(name)){
                p = player;
            }
        }
        return p;
    }

    @Override
    public String toString() {
        String str = "";
        str += "Game: " + ID + "\n";
        str += ("Players: " + players.size() + "\n");
//        for (int i = 0; i < players.size(); i++) {
//            Player player = players.get(ids.get(i));
//            str += player.toString();
//        }
        for (Player p : players.values()) {
            str += p.toString();
        }
        return str;
    }

    public boolean GotSpace() {
        return !full;
    }

    private void SendGameID(Player player) {
        Connectivity.GetInstance().Handle(new SendGameIDCommand(playerToSocket.get(player), ID));
    }
}
