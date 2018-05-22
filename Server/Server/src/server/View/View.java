/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.View;

import java.util.Scanner;
import server.Commands.*;
import server.Commands.ControllerCommand.StartServerCommand;
import server.IComponent;

/**
 *
 * @author conno
 */
public class View extends Thread implements ICommandHandler<ViewCommand>, IComponent {

    private ICommandHandler<ControllerCommand> ch;
    private Scanner input;
    
    public View(){
        super("View");
    }
    @Override
    public void Handle(ViewCommand command) {
        command.execute(this);
    }

    @Override
    public void SetCommandHandler(ICommandHandler ch) {
        this.ch = ch;
    }

    @Override
    public void run() {
        System.out.println("Welcome to your DiceLocked Server Console!");
        input = new Scanner(System.in);
        ch.Handle(new StartServerCommand());
        while(true){
            System.out.println("listening");
        }
    }

    @Override
    public ICommandHandler GetCommandHandler() {
        return ch;
    }

}
