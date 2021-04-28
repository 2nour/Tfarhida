package Tfarhida.services;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.time.LocalDate.now;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SpringLayout;
import sun.util.logging.PlatformLogger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hp
 */
public class UserService {

    Connection cnx;
    PreparedStatement ste;

    public UserService() {

        try {
            cnx = MaConnexion.getinstance().getCnx();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ajouterUser(User u) {

        try {
            String sql = "insert into user(email,username,password,confirm_password,roles,Auth)" + "values(?,?,?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setString(1, u.getEmail());
            ste.setString(2, u.getUsername());
            ste.setString(3, u.getPassword());
            ste.setString(4, u.getConfirm_password());
            ste.setString(5, u.getRoles());
            ste.setInt(6, u.getAuth());
            ste.executeUpdate();
            System.out.println("user ajouter");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean ValidateLogin(String user, String password) {

        //chercher l'utilisateur avec email et password
        String LoginVerif = "SELECT count(1) FROM user WHERE email= '" + user + "'AND password ='" + password + "'";
        try {
            Statement stm = cnx.createStatement();
            ResultSet queryResult = stm.executeQuery(LoginVerif);

            // verifier et afficher si l'utilisateur existe
            while (queryResult.next()) {
                if (queryResult.getInt(1) != 1) {
                    return false;
                }
            }

        } catch (Exception e) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, e);
        }
        return true;
    }

    //verifier si l'email existe deja lors de l'inscription 
    public boolean ValidateEmail(String email) {
        String EmailVerif = "SELECT count(1) FROM user WHERE email = '" + email + "' ";
        try {
            Statement stm = cnx.createStatement();
            ResultSet queryResult = stm.executeQuery(EmailVerif);

            // verifier et afficher si l'email existe
            while (queryResult.next()) {
                if (queryResult.getInt(1) != 1) {
                    return true;
                }
            }

        } catch (Exception e) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    public boolean ValidateUsername(String username){
        String UserVerif = "SELECT count(1) FROM user WHERE username = '"+username+"' ";
        try {
            Statement stm = cnx.createStatement();
            ResultSet queryResult = stm.executeQuery(UserVerif);

            // verifier et afficher si username existe
            while (queryResult.next()) {
                if (queryResult.getInt(1) != 1) {
                    return true ;
                }
            }

        } catch(Exception e) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE,null,e);
        }
        return false;
    }

    public List<User> AfficherUser() {
        List<User> User = new ArrayList<>();

        try {

            String query = "SELECT * FROM user";

            Statement stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);

            while (rst.next()) {
                User U = new User();
                U.setEmail(rst.getString("Email"));
                U.setUsername(rst.getString("Username"));
                U.setPassword(rst.getString("Password"));
                U.setConfirm_password(rst.getString("Confirm_password"));
                U.setRoles(rst.getString("Roles"));

                User.add(U);
                System.out.println(query);

            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
        return User;
    }

    public void ModifierUser(User u) throws ClassNotFoundException {

        try {
            String requete = "UPDATE `user` SET `email`=?, `username`=?,`password`=?,`confirm_password`=?,`roles`=?, `Auth`=? WHERE id=?";
            PreparedStatement pst;
          
                pst = MaConnexion.getinstance().getCnx()
                        .prepareStatement(requete);
         

            //
            pst.setString(1, u.getEmail());
            pst.setString(2, u.getUsername());
            pst.setString(3, u.getPassword());
            pst.setString(4, u.getConfirm_password());
            pst.setString(5, u.getRoles());
            pst.setInt(6, u.getAuth());
            pst.setInt(7, u.getId());

            //
            pst.executeUpdate();
            System.out.println("User modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void SupprimerUser(User u) throws ClassNotFoundException {
        try {
            String requete = "DELETE FROM user where id=?";
            PreparedStatement pst = MaConnexion.getinstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, u.getId());
            //
            pst.executeUpdate();
            System.out.println("User supprimé ! ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    // mot  de passe oublié avec mail 
    // generer un code pour l'envoyer 
    public User CodeConf(String email) {

        Random rand = new Random();
        int code = rand.nextInt(999999);

        String query = "SELECT * from user WHERE email='" + email + "'";
        String query2 = "UPDATE user SET Code ='" + code + "' WHERE email ='" + email + "'";

        User u = new User();
        try {
            Statement stm = cnx.createStatement();
            stm.executeUpdate(query2);

            ResultSet rst = stm.executeQuery(query);

            while (rst.next()) {

                u.setUsername(rst.getString("Username"));
                u.setCode(rst.getInt("Code"));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return u;
    }

    public boolean VerifCode(String email, int Code) {
        String query = "SELECT * from user WHERE Email='" + email + "'";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);

            while (rst.next()) {
                if (rst.getInt("Code") == Code) {
                    return true;
                }

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;

    }

    public void NewPw(String email, String password) {

        String query = "update user set password='" + password + "', confirm_password='" + password + "'  where email='" + email + "' ";

        try {

            Statement stm = cnx.createStatement();

            stm.executeUpdate(query);
            System.out.println("Mot de passe changé ");

        } catch (SQLException ex) {
            System.out.println("Echec");
            System.out.println(ex);
        }
    }

    public User AfficherUserDetails(int id) {
        String query = "SELECT * from utilisateur WHERE ID=" + id + "";

        User u = new User();
        try {
            Statement stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);
            while (rst.next()) {
                u.setId(rst.getInt(id));
                u.setUsername(rst.getString("username"));
                u.setEmail(rst.getString("email"));
                u.setPassword(rst.getString("password"));
                u.setConfirm_password(rst.getString("confirm_password"));
                u.setRoles(rst.getString("roles"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }

        return u;

    }

    // details d'une session pour la connexion/session
    public User SessionDetails(String email) {
        String query = "SELECT * from user WHERE email='" + email + "'";

        User u = new User();
        try {
            Statement stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);
            while (rst.next()) {
                u.setId(rst.getInt("id"));
                u.setEmail(rst.getString("email"));
                u.setUsername(rst.getString("username"));
                u.setPassword(rst.getString("Password"));
                u.setConfirm_password(rst.getString("confirm_password"));
                u.setRoles(rst.getString("roles"));
                u.setAuth(rst.getInt("Auth"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }

        return u;

    }

}
