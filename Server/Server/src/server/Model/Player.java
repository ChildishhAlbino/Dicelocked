/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Model;

import server.Commands.GameCommand;
import server.Commands.GameCommand.LeaveGameCommand;
import server.Commands.ICommandHandler;

/**
 *
 * @author conno
 */
public class Player {

    private final String ID;
    private String name;

    private ICommandHandler<GameCommand> ch;

    public Player(String ID) {
        this.ID = ID;
        name = ID.substring(0, ID.indexOf("_"));
    }

    public void SetName(String name) {
        if (this.name == null) {
            this.name = name;
        }
    }

    @Override
    public String toString() {
        String str = "";
        str += "Player ID: " + ID + "\n";
        str += "Name: " + name + "\n";
        return str;
    }

    public void SetCommandHandler(ICommandHandler<GameCommand> ch) {
        this.ch = ch;
    }

    public void LeaveGame() {
        ch.Handle(new LeaveGameCommand(ID));
    }
}
