package Tfarhida.services;

import Tfarhida.base.MaConnexion;
import java.sql.Connection;
import Tfarhida.entities.Organisation;
import com.sun.istack.internal.logging.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrganisationService {

    Connection cnx;
    PreparedStatement ste;

    public OrganisationService() {
        try {
            cnx = MaConnexion.getinstance().getCnx();
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OrganisationService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Organisation> afficherOrganisation() throws SQLException {
        List<Organisation> lo = new ArrayList<Organisation>();
        Statement st = cnx.createStatement();
        String req = "SELECT * FROM ORGANISATION";
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {

            //  System.out.println (rs.getInt(1)));
          //  System.out.println(rs.getInt(2));
           // System.out.println(rs.getString(3));
            //System.out.println(rs.getInt(4));
            //System.out.println(rs.getString(5));
            //System.out.println(rs.getDate(6));
            //System.out.println(rs.getString(7));
            //System.out.println(rs.getString(8));

            //java.Date date= rs.getDate(6);
            //   Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);  
            Organisation o = new Organisation();
            o.setId(rs.getInt("id"));
            o.setActivite(rs.getString("Activite"));
            o.setCommentaire(rs.getString("commentaire"));
            o.setEtat(rs.getString("Etat"));
            o.setLieu(rs.getString("lieu"));
            o.setNbrjours(rs.getInt("nbrjours"));
            o.setNbrpersonne(rs.getString("nbrpersonne"));
            o.setDate(rs.getDate("date"));
            o.setNote(rs.getInt("note"));
            //(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getDate(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
            lo.add(o);
        }
        return lo;

    }

    public void ajouterOrganisation(Organisation o) {

        try {
            String sql = "insert into organisation (nbrjours, nbrpersonne,date, activite,commentaire,Lieu, etat )"
                    + "values(?,?,?,?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setInt(1, o.getNbrjours());
            ste.setString(2, o.getNbrpersonne());

            Date date = o.getDate();
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strdate = dateformat.format(date);
            ste.setString(3, strdate);
            ste.setString(4, o.getActivite());
            ste.setString(6, o.getLieu());
            ste.setString(5, o.getCommentaire());
            ste.setString(7, "en attente");

            ste.executeUpdate();
            System.out.println("Organisation Ajoutée ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void modifierOrganisation(Organisation o) {

        try {
           // System.out.println(o);
            String sql2 = "UPDATE organisation SET nbrjours=?,nbrpersonne=?,date=?,activite=?,commentaire=?, Lieu=? WHERE id="+o.getId();

            ste = cnx.prepareStatement(sql2);
            ste.setInt(1, o.getNbrjours());
            ste.setString(2, o.getNbrpersonne());

            Date date = o.getDate();
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strdate = dateformat.format(date);
            ste.setString(3, strdate);
            ste.setString(4, o.getActivite());
            ste.setString(6, o.getLieu());
            ste.setString(5, o.getCommentaire());
            //  ste.setString(7, "en attente"); 

            ste.executeUpdate();

            ste.close();
            System.out.println("Organisation Modifiée");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        
    }

    public void supprimerOrganisation(int id) {

        try {
            String sql3 = "delete from organisation where id=?";

            PreparedStatement ps = cnx.prepareStatement(sql3);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Organisation Supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    public void ApprouverAdmin (int id) throws SQLException{
   
         
            String sql4 = "UPDATE organisation SET etat='Accepter' WHERE id="+id;

            ste = cnx.prepareStatement(sql4);
         
            ste.executeUpdate();
              System.out.println("org accepter");
    
    }
    
    
        public void RefuserAdmin (int id) throws SQLException{
   
         
            String sql4 = "UPDATE organisation SET etat='Refuser' WHERE id="+id;

            ste = cnx.prepareStatement(sql4);
         
            ste.executeUpdate();
              System.out.println("org refusée");
    
    }
        public void mettreEvaluation (int id_organisation , int etoile) throws SQLException {
            String sql= "update organisation set note=? where id= '"+id_organisation+"'" ;
            PreparedStatement ste =  cnx.prepareStatement(sql);
             ste.setInt(1, etoile);
             ste.executeUpdate();
        }
        
          public List<Organisation> afficherLesMeilleursOrganisation() throws SQLException {
        List<Organisation> lo = new ArrayList<Organisation>();
        Statement st = cnx.createStatement();
        String req = "SELECT * FROM ORGANISATION where note>3";
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {

          
            //java.Date date= rs.getDate(6);
            //   Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);  
            Organisation o = new Organisation();
            o.setId(rs.getInt("id"));
            o.setActivite(rs.getString("Activite"));
            o.setCommentaire(rs.getString("commentaire"));
            o.setEtat(rs.getString("Etat"));
            o.setLieu(rs.getString("lieu"));
            o.setNbrjours(rs.getInt("nbrjours"));
            o.setNbrpersonne(rs.getString("nbrpersonne"));
            o.setDate(rs.getDate("date"));
            o.setNote(rs.getInt("note"));
            //(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getDate(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
            lo.add(o);
        }
        return lo;

    }
      
          
    public int getNoteOrganisationById(int id) throws SQLException {
        Statement st = cnx.createStatement();
        String req = "SELECT note FROM ORGANISATION where id="+id;
        ResultSet rs = st.executeQuery(req);

        if (rs.next()) {
              return(rs.getInt("note"));
        }
        return -1;

    }

        
}
