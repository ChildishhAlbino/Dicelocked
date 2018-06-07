/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.DB;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Commands.ControllerCommand;
import server.Commands.DBCommand;
import server.Commands.ICommand.ResultCode;
import server.Commands.ICommandHandler;
import server.Model.Player;

/**
 *
 * @author conno
 */
public class DB implements ICommandHandler<DBCommand> {

    private static DB instance;
    private final String DB_ADDRESS;
    private Connection connect;
    private static ICommandHandler<ControllerCommand> ch;

    public static ICommandHandler<DBCommand> GetDB() {
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }

    public DB() {
        this.DB_ADDRESS = "jdbc:mysql://localhost:3306/dicelocked";
    }

    public static void SetCommandHandler(ICommandHandler<ControllerCommand> ch) {
        DB.ch = ch;
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
                this.connect = DriverManager.getConnection(DB_ADDRESS, "root", "");
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

    public boolean CheckLoginInfo(String username, String hash) {
        //System.out.println("checking information...");
        if (ConnectToDB()) {
            try {
                String sql = "SELECT * FROM User_Login WHERE Username = ? && PasswordHash = ?";
                PreparedStatement ps = connect.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, hash);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                if(rs.next()){
                    System.out.println("Login Successful");
                    return true;
                }
                System.out.println("Wrong password!");
            } catch (SQLException ex) {
                //Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.toString());
            }
        }
        return false;
    }
    
    public int SignIn(String username, String hash){
        // returns PlayerID from username
        return 0;
    }  

    public void CheckSignUp(String username, String hash, int ID) {
        if (ConnectToDB()) {
            // Check Login info doesn't already exist
            if (!CheckLoginInfo(username, hash)) {
                // if so; signup
                System.out.println("User not found so we'll sign you up!");
                SignUpUser(username, hash, ID);
            } else {
                // else, explain they already have signup details
            }
        }
    }

    public void SignUpUser(String username, String hash, int ID) {
        if (ConnectToDB()) {
            String sql = "INSERT INTO user_login(Username, PasswordHash, User_Since, Player_ID) VALUES (?, ?, NOW(), ?)";
            try {
                PreparedStatement ps = connect.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, hash);
                ps.setInt(3, ID);
                ps.execute();
            } catch (SQLException ex) {
                if(ex.toString().contains("Duplicate entry")){
                    System.out.println("Username already in use!");
                    // let client know
                }
            }
        }
    }

    public boolean AddPlayerToPlayersTable(String playerName) {
        if (ConnectToDB()) {
            try {
                String sql = "INSERT INTO players(PlayerName) VALUES (?)";
                PreparedStatement ps = connect.prepareStatement(sql);
                ps.setString(1, playerName);
                ps.execute();
                return true;
            } catch (SQLException ex) {
                //Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                if(ex.toString().contains("Duplicate entry")){
                    System.out.println("Player Name already in use!");
                    // let client know
                }
            }
        }
        return false;
    }

    public int GetPlayerIDByName(String name) {
        int i = -1;
        if (ConnectToDB()) {
            try {
                String sql = "SELECT RecordID FROM players WHERE PlayerName = ?";
                PreparedStatement ps = connect.prepareStatement(sql);
                ps.setString(1, name);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                if (rs.next()) {
                    i = rs.getInt("RecordID");
                }
                else{
                    // couldn't find player by name
                    System.out.println("Problems");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return i;
    }
}
