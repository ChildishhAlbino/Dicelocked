/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Model;

import java.util.ArrayList;
import java.util.List;
import server.Commands.*;
import server.Commands.ICommand.ResultCode;
import server.Commands.ModelCommand.*;

/**
 *
 * @author conno
 */
public class Game implements ICommandHandler<GameCommand> {

    private List<Character[]> boards;
    private List<Player> players;
    private int maxPlayers = 2;
    private final int BOARD_SIZE;

    public Game(int BOARD_SIZE) {
        this.BOARD_SIZE = BOARD_SIZE;
    }

    public void Start() {
        boards = new ArrayList<>(); // initializes list of boards
        players = new ArrayList<>();
        for (int i = 0; i < maxPlayers; i++) {
            boards.add(new Character[BOARD_SIZE]); // creates a board for each of the players at a given size
        }
    }

    @Override
    public void Handle(GameCommand command) {
        if (command.execute(this) != ResultCode.Failure) {

        }
    }
}
