/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import Tfarhida.entities.Randonnee;
import Tfarhida.entities.Reservation;
import static Tfarhida.gui.AfficheReservationController.list;
import Tfarhida.services.RandonneService;
import Tfarhida.services.ReservationService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXComboBox;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AfficheReservationClientController implements Initializable {

      
    
    @FXML
    private TextField tfid;
    
    public static ObservableList<Reservation> list;
    
    

    @FXML
    private TextField tfnumres;

    @FXML
    private DatePicker datepickeresr;

    @FXML
    private TextField tfobs;

    @FXML
    private TextField tfmont;

    @FXML
    private TextField tfnbperrsonne;

    @FXML
    private JFXComboBox<Reservation> cbetat;
    
     @FXML
    private ComboBox<Reservation> cbr;

    @FXML
    private TableView<Reservation> tbreservation;

    @FXML
    private TableColumn<Reservation, Integer> colid;

    @FXML
    private TableColumn<Reservation, Integer> colnumres;

    @FXML
    private TableColumn<Reservation, String> coldateres;

    @FXML
    private TableColumn<Reservation, String> colobs;

    @FXML
    private TableColumn<Reservation, Float> colmontant;

    @FXML
    private TableColumn<Reservation, Integer> colnbpersonne;

    @FXML
    private TableColumn<Reservation, String> coletat;
    
    @FXML
    private TextField txtchercher;
       @FXML
    private AnchorPane anchropane;
    
    
    @FXML
    private Button ajoutreservation;
    @FXML
    private Button modifierreservation;
    @FXML
    private Button supprimerreservation;
    @FXML
    private Button DetailsReservation;
    
      @FXML
    private Button button_imprime;
    
    
     @FXML
   public  void handleButtonAction(ActionEvent event) {
    if(event.getSource() == ajoutreservation){
       AddReservation();
    }else if (event.getSource() == modifierreservation){
        UpdateReservation();
    }else if(event.getSource() == supprimerreservation){
        DeleteReservation();   
        }else if(event.getSource() == DetailsReservation ){
            DetailsReservation();
        
        
    }
   }
    
    @Override
   public void initialize(URL url, ResourceBundle rb) {
        try {
            showReservation();
               FilteredList<Reservation> filteredData = new FilteredList<>(list, b -> true);
                // filterField.textProperty().addListener((observable,old));
        txtchercher.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(f -> {
      // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                int s = f.getObservation().toLowerCase().indexOf(lowerCaseFilter);
                int s1 = f.getEtat().toLowerCase().indexOf(lowerCaseFilter);
                
                
                if (s != -1) {
                    return true; // Filter matches first name.
                } else if (s1 != -1) {
                    return true; // Filter matches last name.
                } else {
                    return false; // Does not match.
                }
            });
        });
        
        SortedList<Reservation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbreservation.comparatorProperty());
        tbreservation.setItems(sortedData);
        
               } catch (SQLException ex) {
            Logger.getLogger(AfficheReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
      public Connection getConnection() throws SQLException{
       Connection conn;
       try{
           conn= DriverManager.getConnection("jdbc:mysql://localhost:3308/tfarhida", "root","");
           return conn;
       }catch(Exception ex){
           System.out.println("Errors:" +ex.getMessage());
           return null;
           
       }
    }
    public ObservableList<Reservation> getReservationList() throws SQLException{
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM reservation";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
             RandonneService randonneService=null;
            Reservation reservation;
            randonneService=new RandonneService();
            while(rs.next()){
                    Randonnee   rand=randonneService.findById(rs.getInt("id_randonnee"));

                reservation = new Reservation(rs.getInt("id"),rs.getInt("numreservation"),rs.getString("datereservation"),rs.getString("observation"),rs.getFloat("montant"),rand,rs.getInt("nombrepersonne"), rs.getString("etat"));
                reservationList.add(reservation);
            }
        }catch(Exception ex){
        ex.printStackTrace();
    }
        return reservationList;
    
    }
    public void showReservation() throws SQLException{
         list = getReservationList();
        colid.setCellValueFactory(new PropertyValueFactory<Reservation,Integer>("id"));
        colnumres.setCellValueFactory(new PropertyValueFactory<Reservation,Integer>("numreservation"));
        coldateres.setCellValueFactory(new PropertyValueFactory<Reservation,String>("datereservation"));
        colobs.setCellValueFactory(new PropertyValueFactory<Reservation,String>("observation"));
        colmontant.setCellValueFactory(new PropertyValueFactory<Reservation,Float>("montant"));
        colnbpersonne.setCellValueFactory(new PropertyValueFactory<Reservation,Integer>("nombrepersonne"));
        coletat.setCellValueFactory(new PropertyValueFactory<Reservation,String>("etat"));
        
        tbreservation.setItems(list);
    }
    
     public void AddReservation() {

     try {
                      Randonnee rand=null;
         RandonneService randonneService=new RandonneService();
            ReservationService reservationService=new ReservationService();
            rand=randonneService.findById(36);
            Reservation r=new Reservation(Integer.parseInt(tfid.getText()),Integer.parseInt(tfnumres.getText()),datepickeresr.getValue().toString(),tfobs.getText(),Double.parseDouble(tfmont.getText()),rand,Integer.parseInt(tfnbperrsonne.getText()),"en cours");
              reservationService.insert(r);
                list.clear();
            showReservation();
   
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficheReservationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AfficheReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Reservation ajouter avec success");
            a.show();
        
    }
    
    public void UpdateReservation(){

     try {
            ReservationService  reservationService=new ReservationService();
            RandonneService randonneService=new RandonneService();
            Randonnee rand=randonneService.findById(36);
            Reservation r=new Reservation(Integer.parseInt(tfid.getText()),Integer.parseInt(tfnumres.getText()), datepickeresr.getValue().toString(),tfobs.getText(),Double.parseDouble(tfmont.getText()),rand,Integer.parseInt(tfnbperrsonne.getText()),"en cours");
            reservationService.update(r);
            list.clear();
            showReservation();
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficheReservationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AfficheReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Reservation a été modifier avec success");
            a.show();
    }
    
    
    public void DeleteReservation(){
        ReservationService reservationService=null;
        RandonneService randonneService=null;
     try {
         randonneService=new RandonneService();
            reservationService=new ReservationService();
              Randonnee rand =randonneService.findById(Integer.parseInt(tfid.getText()));
      Reservation r=new Reservation(Integer.parseInt(tfid.getText()),Integer.parseInt(tfnumres.getText()), datepickeresr.getValue().toString(),tfobs.getText(),Double.parseDouble(tfmont.getText()),rand,Integer.parseInt(tfnbperrsonne.getText()),"en cours");
      reservationService.delete(r.getId());
       
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficheReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
           
        try {
                            list.clear();

            showReservation();
              Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Reservation a été supprimer avec success");
            a.show(); 
        } catch (SQLException ex) {
            Logger.getLogger(AfficheReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void handleMouseAction(MouseEvent event){
        Reservation reservation= tbreservation.getSelectionModel().getSelectedItem();
        System.out.println();
        tfid.setText(Integer.toString(reservation.getId()));
        tfnumres.setText(Integer.toString(reservation.getNumreservation()));
        datepickeresr.setValue(LocalDate.parse(reservation.getDatereservation()));
        colobs.setText(reservation.getObservation());
        tfmont.setText(Double.toString(reservation.getMontant()));
        tfnbperrsonne.setText(Integer.toString(reservation.getNombrepersonne()));
        coletat.setText(reservation.getEtat());
        
    }
    
    public  void DetailsReservation() {
        Reservation r=tbreservation.getSelectionModel().getSelectedItem();
        tfid.setText(Integer.toString(r.getId()));
        tfnumres.setText(Integer.toString(r.getNumreservation()));
        tfobs.setText(r.getObservation());
        tfmont.setText(Double.toString(r.getMontant()));
       
        tfnbperrsonne.setText(Integer.toString(r.getNombrepersonne()));
        datepickeresr.setValue(LocalDate.parse(r.getDatereservation()));
      
        
    }
    
    public void download(ActionEvent e) throws FileNotFoundException{
                Reservation r=tbreservation.getSelectionModel().getSelectedItem();
                 Document document = new Document(PageSize.A4);
        document.addAuthor("brahim");
        document.addTitle("Randonne");
        
        
         try {
             PdfWriter.getInstance(document, new FileOutputStream("download"+".pdf"));
             document.open();
        System.out.println("aaaa");
        Paragraph para = new Paragraph(r.toString());
         float[] columnWidths = {1.5f, 2f, 5f, 2f};
          Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
   Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12); 
   //create PDF table with the given widths
             PdfPTable table = new PdfPTable(columnWidths);
               insertCell(table, "Order No", Element.ALIGN_RIGHT, 1, bfBold12);
   insertCell(table, "Account No", Element.ALIGN_LEFT, 1, bfBold12);
   insertCell(table, "Account Name", Element.ALIGN_LEFT, 1, bfBold12);
   insertCell(table, "Order Total", Element.ALIGN_RIGHT, 1, bfBold12);
   table.setHeaderRows(1);
  // para.add(table);

       document.add(table);
        document.add(para);
  
        document.close();
        System.out.println("Document generated"); 
         } catch (DocumentException ex) {
             Logger.getLogger(AfficheRandonneeController.class.getName()).log(Level.SEVERE, null, ex);
         }
        

    }
       
 private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){
   
  //create a new cell with the specified Text and Font
     PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
  //set the cell alignment
  cell.setHorizontalAlignment(align);
  //set the cell column span in case you want to merge two or more cells
  cell.setColspan(colspan);
  //in case there is no text and you wan to create an empty row
  if(text.trim().equalsIgnoreCase("")){
   cell.setMinimumHeight(10f);
  }
  //add the call to the table
  table.addCell(cell);
   
 }
    
    
    
    
}
