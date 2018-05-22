/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Commands;

import server.Model.Model;

/**
 *
 * @author conno
 */
public abstract class ModelCommand implements ICommand<Model> {

    public abstract class GameCommand extends ModelCommand {

    }

    public static class TestModelCommand extends ModelCommand {

        @Override
        public ResultCode execute(Model commandHandler) {
            return ResultCode.Failure;
        }

    }
}
