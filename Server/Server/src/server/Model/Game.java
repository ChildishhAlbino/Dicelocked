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
import server.Commands.ICommand.ResultCode;
import server.Connectivity.SocketHandler;

/**
 *
 * @author conno
 */
public class Game implements ICommandHandler<GameCommand> {

    private List<Board> boards;
    private List<Player> players;
    private HashMap<Player, SocketHandler> PlayerToSocket;
    private final int maxPlayers = 2;
    private final int BOARD_SIZE;
    private ICommandHandler<ModelCommand> ch;

    public Game(int BOARD_SIZE) {
        this.BOARD_SIZE = BOARD_SIZE;
    }

    public void Init() {
        boards = new ArrayList<>(); // initializes list of board
        PlayerToSocket = new HashMap<>();
        players = new ArrayList<>();
        for (int i = 0; i < maxPlayers; i++) {
            boards.add(new Board(BOARD_SIZE, i + 1)); // creates a board for each of the players at a given size
        }
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
}
