package Tfarhida.base;

import java.io.File;
import java.sql.*;
import java.sql.DriverManager;
import javax.swing.JFileChooser;


public class MaConnexion {
    
    String filename= null;
    public static String path;
    Connection conn = null;
        
    
    public String url = "jdbc:mysql://localhost:3306/tfarhida?useSSL=false";
    public String user = "root";
    public String pwd = "";
    private Connection cnx;
    public static MaConnexion ct;
    
    public MaConnexion() throws ClassNotFoundException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection(url, user, pwd);
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
    
    public void filen(){
        try{
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("choisir une image");
        chooser.setApproveButtonText("ajouter une image");
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        filename= f.getAbsolutePath();
        this.path=(filename);
        
        }
        catch(Exception e){
            
            System.out.println(e.getMessage());
            
        }
        
    }
    
    public String getP(){
         return path;
    }
    
}
