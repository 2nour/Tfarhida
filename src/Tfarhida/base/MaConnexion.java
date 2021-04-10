package Tfarhida.base;

import java.sql.*;
import java.sql.DriverManager;


public class MaConnexion {
    public String url = "jdbc:mysql://localhost:3306/tfarhida";
    public String user = "root";
    public String pwd = "";
    private Connection cnx;
    public static MaConnexion ct;
    
    private MaConnexion() throws ClassNotFoundException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/tfarhida", user, pwd);
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
