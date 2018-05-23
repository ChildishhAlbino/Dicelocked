/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Model;

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

    }

    @Override
    public ICommandHandler GetCommandHandler() {
        return ch;
    }

    @Override
    public void SetCommandHandler(ICommandHandler ch) {
        this.ch = ch;
    }
    
    public void NewGame(Player player, SocketHandler sh){
        
    }
    

}
