package Tfarhida.base;

import java.io.File;
import java.sql.*;
import java.sql.DriverManager;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class MaConnexion {
    public String url = "jdbc:mysql://localhost:3308/tfarhida?useSSL=false";
    public String user = "root";
    public String pwd = "";
    private Connection cnx;
    public static MaConnexion ct;
    
    public MaConnexion() throws ClassNotFoundException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection("jdbc:mysql://localhost:3308/tfarhida?useSSL=false", user, pwd);
            System.out.println("Connexion établie");
        } catch (SQLException ex) {
            System.out.println("Non Connexion");
            System.out.println(ex.getMessage());
        }
    }
    
    public static MaConnexion getinstance() throws ClassNotFoundException{
        if(ct == null)
            ct = new MaConnexion();
        return ct;
    }

    public Connection getCnx() {
        return cnx;
    }
    
   
}
