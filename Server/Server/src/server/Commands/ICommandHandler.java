/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

/**
 *
 * @author conno
 * @param <T>
 */
public interface ICommandHandler<T extends ICommand<?>> {

    void Handle(T command);
}
