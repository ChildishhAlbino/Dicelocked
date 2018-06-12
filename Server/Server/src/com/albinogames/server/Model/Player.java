/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinogames.server.Model;

import com.albinogames.server.Commands.GameCommand;
import com.albinogames.server.Commands.ICommandHandler;

/**
 *
 * @author conno
 */
public class Player {

    private final int ID;
    private final String name;

    private ICommandHandler<GameCommand> ch;

    public Player(int ID, String name) {
        this.ID = ID;
        this.name = name;
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
        //ch.Handle(new LeaveGameCommand(ID));
    }

    public int GetID() {
        return ID;
    }
    
    public String GetName(){
        return name;
    }
}
