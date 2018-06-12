/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinogames.server.Model;

/**
 *
 * @author conno
 */
public class Board {

    private Character[] characters;
    private final int id;
    private final int size;
    private Player owner;

    public Board(int size, int id) {
        this.size = size;
        this.id = id;
    }

    public void Init() {
        System.out.println("Init. Board no." + id);
        characters = new Character[size];
    }

    public void SetOwner(Player owner) {
        if (this.owner == null) {
            System.out.println("Owner set!");
            this.owner = owner;
        } else {
            System.out.println("Owner already set.");
        }
    }
}
