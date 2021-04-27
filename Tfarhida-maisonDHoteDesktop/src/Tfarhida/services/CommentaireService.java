
package Tfarhida.services;

import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Commentaire;
import Tfarhida.entities.Maison;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CommentaireService {
    Connection cnx;
    
    PreparedStatement ste;
    Maison maison;
    
    public CommentaireService(){
        try {
            cnx = MaConnexion.getinstance().getCnx();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ajouterCommentaire(Commentaire comment){
        try {
            String sql = "insert into commentaire(id, maison_id, user_id, comment, date)"+"values(?,?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, comment.getId());
            ste.setInt(2, comment.getMaison_id());
            ste.setInt(3, comment.getUser_id());
            ste.setString(4, comment.getComment());
            ste.setDate(5, comment.getDate());
            ste.executeUpdate();
            System.out.println("Commentaire ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void setMaison(Maison maison){
        this.maison = maison;
        afficherComments();
    }
    
    public List<Commentaire> afficherComments(){
        List<Commentaire> comments = new ArrayList<>();
        try {
            String sql = "select * from commentaire";
            ste = cnx.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            while(rs.next()){
                Commentaire m = new Commentaire();
                m.setId(rs.getInt("id"));
                m.setMaison_id(rs.getInt("maison_id"));
                m.setUser_id(rs.getInt("user_id"));
                m.setComment(rs.getString("comment"));
                m.setDate(rs.getDate("date"));
                comments.add(m);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return comments;
    }

   
  
    
}
