/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.DB;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Commands.DBCommand;
import server.Commands.ICommand.ResultCode;
import server.Commands.ICommandHandler;

/**
 *
 * @author conno
 */
public class DB implements ICommandHandler<DBCommand> {

    private static DB instance;
    private final String DB_ADDRESS;
    private Connection connect;

    public static ICommandHandler<DBCommand> GetDB() {
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }

    public DB() {
        this.DB_ADDRESS = "jdbc:mysql://localhost:10001/dicelocked";
    }

    @Override
    public void Handle(DBCommand command) {
        if (command.execute(this) != ResultCode.Failure) {
            // log
        }
    }

    private boolean ConnectToDB() {
        if (this.connect == null) {
            try {
                System.out.println("Connecting to DB");
                this.connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/dicelocked", "root", "");
                if (connect == null) {
                    return false;
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                return false;
                // log this
            }
        }
        return true;
    }

    public String Hash(String toBeHashed) {
        if (ConnectToDB()) {
            String sql = "SELECT SHA2(?, 256);";
            try {
                PreparedStatement ps = connect.prepareStatement(sql);
                ps.setString(1, toBeHashed);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    

}
