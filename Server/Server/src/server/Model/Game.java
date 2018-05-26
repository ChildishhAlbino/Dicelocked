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
import server.Connectivity.*;

/**
 *
 * @author conno
 */
public class Game implements ICommandHandler<GameCommand> {
    
    private List<Board> boards;
    private List<String> ids;
    private HashMap<String, Player> players;
    //private List<Player> players;
    private HashMap<Player, SocketHandler> PlayerToSocket;
    private final int MAX_PLAYERS = 2;
    private final int BOARD_SIZE;
    private ICommandHandler<ModelCommand> ch;
    private boolean full = false;
    
    public final String ID;
    
    public Game(int BOARD_SIZE) {
        this.BOARD_SIZE = BOARD_SIZE;
        this.ID = Identification.ID.GenerateID_Number(6);
    }
    
    public void Init() {
        boards = new ArrayList<>(); // initializes list of board
        PlayerToSocket = new HashMap<>();
        ids = new ArrayList<>();
        players = new HashMap<>();
        for (int i = 0; i < MAX_PLAYERS; i++) {
            boards.add(new Board(BOARD_SIZE, i + 1)); // creates a board for each of the players at a given size
        }
    }
    
    public void ShutdownGame() {
        Identification.ID.RemoveID(ID);
        players.clear();
        PlayerToSocket.clear();
        ids.clear();
        // shutdown socket handlers
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
    
    public void PlayerJoin(String ID, SocketHandler sh) {
        
        Player player = new Player(ID);
        PlayerToSocket.put(player, sh);
        ids.add(ID);
        players.put(ID, player);
        player.SetCommandHandler(this);
        if (players.size() == MAX_PLAYERS) {
            full = true;
        }
        SendGameID(player);
    }
    
    public void PlayerLeave(String ID) {
        PlayerToSocket.remove(players.get(ID));
        players.remove(ID);
        ids.remove(ID);
        if (players.size() < MAX_PLAYERS) {
            full = false;
        }
    }
    
    @Override
    public String toString() {
        String str = "";
        str += "Game: " + ID + "\n";
        str += ("Players: " + players.size() + "\n");
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(ids.get(i));
            str += player.toString();
        }
        return str;
    }
    
    public boolean GotSpace() {
        return !full;
    }
    
    private void SendGameID(Player player) {
        ConnectivityCommandHandlerInstance.GetInstance().Handle(new SendGameIDCommand(PlayerToSocket.get(player), ID));
    }
}
