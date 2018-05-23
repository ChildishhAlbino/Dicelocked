/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import server.Commands.ICommandHandler;

/**
 *
 * @author conno
 */
public interface IComponent {
    // not perfect at all - can cause major problems later on
    void SetCommandHandler(ICommandHandler ch);
    // needs fix
    ICommandHandler GetCommandHandler();

    void Start();
}
