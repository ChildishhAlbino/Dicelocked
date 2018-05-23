/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.View;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Commands.*;
import server.Commands.ControllerCommand.StartServerCommand;
import server.IComponent;

/**
 *
 * @author conno
 */
public class View implements ICommandHandler<ViewCommand>, IComponent {

    private ICommandHandler<ControllerCommand> ch;
    private Scanner input;

    @Override
    public void Handle(ViewCommand command) {
        command.execute(this);
    }

    @Override
    public void SetCommandHandler(ICommandHandler ch) {
        this.ch = ch;
    }

    @Override
    public void Start() {
        System.out.println("Welcome to your DiceLocked Server Console!");
        input = new Scanner(System.in);
        ch.Handle(new StartServerCommand());
        while(true){
            System.out.println("listening");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ICommandHandler GetCommandHandler() {
        return ch;
    }

}
