/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

public interface ICommand<T> {

    public enum ResultCode {
        Success,
        Failure,
    }

    ResultCode execute(T commandHandler);
}
