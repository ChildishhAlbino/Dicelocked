/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import server.Controller.Controller;
import server.Model.Model;
import server.View.View;

public class Server {

    private static View view;
    private static Model model;
    private static Controller controller;

    public static void main(String[] args) {

        // create components
        view = new View();
        controller = new Controller();
        model = new Model();
        // link them
        view.SetCommandHandler(controller);
        controller.SetCommandHandler(model);
        model.SetCommandHandler(view);
        // start the view
        view.start();
    }
}
