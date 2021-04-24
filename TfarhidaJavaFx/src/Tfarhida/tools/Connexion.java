
package Tfarhida.tools;
import java.sql.*;
import java.util.logging.Logger;
        
public class Connexion {
   public  String url="jdbc:mysql://localhost:3306/tfarhida" ;
   public String user="root";
   public String pwd="";
   private Connection cnx;
   private static Connexion ct;
   
   public Connexion(){
       try
       {
       cnx=DriverManager.getConnection(url,user,pwd);
       System.out.println("Connexion etablie");
       }
       catch (SQLException ex)
       {
         System.out.println(ex.getMessage());
       }
       
   }
   public static  Connexion getinstance(){
       if (ct==null)
           ct=new Connexion();
       return ct;
   }

    public Connection getCnx() {
        return cnx;
    }
   
   
}
