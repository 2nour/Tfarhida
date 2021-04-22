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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.qrcode.WriterException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Extension;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import static java.util.Collections.list;
import static java.util.Locale.filter;
import static java.util.Locale.filter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import sun.util.resources.LocaleData;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * FXML Controller class
 *
 * @author PC
 */
public class AfficheRandonneeController implements Initializable {
    
     public static Randonnee fo;

    @FXML
    private TableView<Randonnee> tableRand;
    public static ObservableList<Randonnee> list;
    private ImageView photo;


    @FXML
    private TableColumn<Randonnee, Integer> rand_id;
    
    @FXML
    private TableColumn<Randonnee, String> rand_vdepart;
    @FXML
    private TableColumn<Randonnee, String> rand_varrive;

    @FXML
    private TableColumn<Randonnee, String> rand_dated;

    @FXML
    private TableColumn<Randonnee, String> rand_dater;
    
    @FXML
    private TableColumn<Randonnee, String> rand_desc;
    
    @FXML
    private TableColumn<Randonnee, String> rand_activite;
    
    @FXML
    private TableColumn<Randonnee, String> rand_diff1;
    
    @FXML
    private TableColumn<Randonnee, Integer> rand_duree;

    @FXML
    private TableColumn<Randonnee, String> rand_diff;

    @FXML
    private TableColumn<Randonnee, Integer> rand_budget;
    
    @FXML
    private TextField Text_id;

    @FXML
    private TextField Text_villedepart;

    @FXML
    private TextField Text_villearrivee;

    @FXML
    private DatePicker Datepicker_depart;

    @FXML
    private DatePicker Datepicker_retour;

    @FXML
    private TextField Text_activite;

    @FXML
    private TextArea Text_description;

    @FXML
    private JFXButton image_R;

    @FXML
    private TextField Text_duree;

    @FXML
    private TextField Text_difficulte;

    @FXML
    private TextField Text_budget;
    
     @FXML
    private JFXTextField txtrechercher;

     @FXML
    private Button btnrechercher;
    
     @FXML
    private Button image;
     
     
     @FXML
    private TextField txchercher;

    

    
      @FXML
    private Button AjouterRandonnee;

    @FXML
    private Button ModifierRandonnee;

    @FXML
    private Button supprimerRandonnee;

    @FXML
    private Button DetailsRandonnee;

     @FXML
    private JFXTextField txt_searchVilleDepart;
     @FXML
    private Text textimg;
     @FXML
    private ImageView image_Randonnee;
     
      @FXML
    private FontAwesomeIconView suivant;

     @FXML
    private Button handlebuttonsearch;
     
      
  public  void importerImage(ActionEvent event){
       FileChooser fc = new FileChooser();
      
       fc.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png","*.jpg" ));
       File f = fc.showOpenDialog(null);
       if(f != null){
          textimg.setText(f.getAbsolutePath());
          textimg.setText(f.getName());
          Image image = new Image(f.toURI().toString(),image_Randonnee.getFitWidth(),image_Randonnee.getFitHeight(),true,true);
      image_Randonnee.setImage(image);
       
       }
       
       
   }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showRandonnee();
              FilteredList<Randonnee> filteredData = new FilteredList<>(list, b -> true);
        // filterField.textProperty().addListener((observable,old));
        txchercher.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(f -> {
				// If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                int s = f.getVillearrivee().toLowerCase().indexOf(lowerCaseFilter);
                int s1 = f.getVilledepart().toLowerCase().indexOf(lowerCaseFilter);
                int s2 = f.getDifficulte().toLowerCase().indexOf(lowerCaseFilter);
				// Compare first name and last name of every person with filter text.

                if (s != -1) {
                    return true; // Filter matches first name.
                } else if (s1 != -1) {
                    return true; // Filter matches last name.
                } else if (s2 != -1) {
                    return true;
                } else {
                    return false; // Does not match.
                }
            });
        });
        
        SortedList<Randonnee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableRand.comparatorProperty());
        tableRand.setItems(sortedData);
     
     
        } catch (SQLException ex) {
            Logger.getLogger(AfficheRandonneeController.class.getName()).log(Level.SEVERE, null, ex);
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
    
      public ObservableList<Randonnee> getRandonneeList() throws SQLException{
        ObservableList<Randonnee> randonneeList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM randonnee";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
             RandonneService randonneService=null;
            Randonnee randonnee;
            randonneService=new RandonneService();
            while(rs.next()){
                randonnee=new Randonnee(rs.getInt("id"),rs.getString("villedepart"), rs.getString("villearrivee"), rs.getString("datedepart"), rs.getString("dateretour"), rs.getString("activite"), rs.getString("description"), rs.getString("image"), rs.getInt("duree"), rs.getString("difficulte"), rs.getInt("budget"));
                
                randonneeList.add(randonnee);
            }
        }catch(Exception ex){
        ex.printStackTrace();
    }
        return randonneeList;
        
       
    
    }
      
     // public void searchRandonnee(){
      //    String sql = "select villedepart,villearrivee,datedepart,dateretour,activite,description,image,duree,difficulte,budget where '"+txt_searchVilleDepart.getText()+"' ";
          
     // }
      
       public void showRandonnee() throws SQLException{
         list = getRandonneeList();
 
        rand_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        rand_vdepart.setCellValueFactory(new PropertyValueFactory<>("villedepart"));
        rand_varrive.setCellValueFactory(new PropertyValueFactory<>("villearrivee"));
        rand_dated.setCellValueFactory(new PropertyValueFactory<>("datedepart"));
        rand_dater.setCellValueFactory(new PropertyValueFactory<>("dateretour"));
        rand_activite.setCellValueFactory(new PropertyValueFactory<>("activite"));
        rand_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        rand_diff1.setCellValueFactory(new PropertyValueFactory<>("image"));
        rand_duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        rand_diff.setCellValueFactory(new PropertyValueFactory<>("difficulte"));
        rand_budget.setCellValueFactory(new PropertyValueFactory<>("budget"));
  
        tableRand.setItems(list);
        
           
        
    }
        public void AddRandonnee() throws IOException {

     try {
          Randonnee rand=null;
         RandonneService randonneService=new RandonneService();
     
          Randonnee r=new Randonnee(Text_villedepart.getText(), Text_villearrivee.getText(),Datepicker_depart.getValue().toString(), Datepicker_retour.getValue().toString(),Text_activite.getText(), Text_description.getText(),textimg.getText(),Integer.parseInt(Text_duree.getText()), Text_difficulte.getText(), Integer.parseInt(Text_budget.getText()));
          
           randonneService.insert(r);
           list.clear();
           showRandonnee();
   
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficheRandonneeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
             Logger.getLogger(AfficheRandonneeController.class.getName()).log(Level.SEVERE, null, ex);
         }
     
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Randonné ajouter avec success!");
            a.show();
            
        
    }
            
             public void UpdateRandonnee(){

     try {
            ReservationService  reservationService=new ReservationService();
            RandonneService randonneService=new RandonneService();
            
            Randonnee r=new Randonnee(Integer.parseInt(Text_id.getText()),Text_villedepart.getText(), Text_villearrivee.getText(),Datepicker_depart.getValue().toString(), Datepicker_retour.getValue().toString(),Text_activite.getText(), Text_description.getText(), "aaa",Integer.parseInt(Text_duree.getText()), Text_difficulte.getText(), Integer.parseInt(Text_budget.getText()));
            randonneService.update(r);
            list.clear();
            showRandonnee();
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficheRandonneeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AfficheReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Randonnee a été modifier avec success");
            a.show();
    }
             
        public void DeleteRandonnee(){
        ReservationService reservationService=null;
        RandonneService randonneService=null;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete?");
        alert.showAndWait();
        Optional <ButtonType> action = alert.showAndWait();
        
        if(action.get() == ButtonType.OK){
             try {
         randonneService=new RandonneService();
            reservationService=new ReservationService();
              Randonnee rand =randonneService.findById(Integer.parseInt(Text_id.getText()));
      randonneService.delete(Integer.parseInt(Text_id.getText()));
           list.clear();
            showRandonnee();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficheRandonneeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AfficheRandonneeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        }
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Randonnee a été supprimer avec success");
            a.show();   
            
    }
        
         public void handleMouseAction(MouseEvent event){
        Randonnee randonnee= tableRand.getSelectionModel().getSelectedItem();
        System.out.println();
        Text_id.setText(Integer.toString(randonnee.getId()));
        Text_villedepart.setText(randonnee.getVilledepart());
        Text_villearrivee.setText(randonnee.getVillearrivee());
        Datepicker_depart.setValue(LocalDate.parse(randonnee.getDatedepart()));
        Datepicker_retour.setValue(LocalDate.parse(randonnee.getDateretour()));
        Text_activite.setText(randonnee.getActivite());
        Text_description.setText(randonnee.getDescription());
        image_R.setText(randonnee.getImage());
        Text_duree.setText(Integer.toString(randonnee.getDuree()));
        Text_difficulte.setText(randonnee.getDifficulte());
        Text_budget.setText(Integer.toString(randonnee.getBudget()));
  
        
    }
         
     @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
    if(event.getSource() == AjouterRandonnee){
      AddRandonnee();
    }else if (event.getSource() == ModifierRandonnee){
        UpdateRandonnee();
    }else if(event.getSource() == supprimerRandonnee){
       DeleteRandonnee() ;   
         }else if(event.getSource() == DetailsRandonnee ){
        DetailsRandonnee();
        }else if(event.getSource() == suivant ){
        suivantButton();
        
        
    }
    }
    
         private void suivantButton() throws IOException {
         suivant.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("AfficheReservation.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show(); }
     @FXML
 //  public void handlebuttonsearch(MouseEvent event) throws SQLException {
  //      if(event.getSource() == handlebuttonsearch){
   //   searchRandonnee();

    //}
    //}

    public  void DetailsRandonnee() {
        Randonnee r=tableRand.getSelectionModel().getSelectedItem();
        Text_id.setText(Integer.toString(r.getId()));
        Text_budget.setText(Integer.toString(r.getBudget()));
        Text_activite.setText(r.getActivite());
        Text_description.setText(r.getDescription());
        Text_difficulte.setText(r.getDifficulte());
        Text_duree.setText(Integer.toString(r.getDuree()));
        Text_villearrivee.setText(r.getVillearrivee());
        Text_villedepart.setText(r.getVilledepart());
        System.out.println(r.getDatedepart()+" "+r.getDateretour());
        Datepicker_depart.setValue(LocalDate.parse(r.getDatedepart()));
        Datepicker_retour.setValue(LocalDate.parse(r.getDateretour()));
       // image_Randonnee.setImage(r.getImage());
        
    }
    
    // void searchRandonnee() throws SQLException{
   //  String sql = "select * from randonnee where villedepart = '"+txtrechercher.getText()+"'";
    //   Connection conn = getConnection();
   //  int n=0;
     //    try{
  //   Statement st;
  //   ResultSet rs;
      //       st = conn.prepareStatement(sql);
       //      rs=st.executeQuery(sql);
     //    if(rs.next()){
       //      Text_villedepart.setText(rs.getString("villedepart"));
        //     Text_villearrivee.setText(rs.getString("villearrivee"));
        //     Text_description.setText(rs.getString("description"));
       //      Text_activite.setText(rs.getString("activite"));
        //     n=1;
      //   }
  //   }catch(Exception e){
   //     e.printStackTrace();  
  //   }
     //    if(n==1){
       //      Alert alert = new Alert(AlertType.ERROR, "Aucun Randonnee trouve avec villedepart = '"+txtrechercher.getText()+"",javafx.scene.control.ButtonType.OK);
        //     alert.showAndWait();
       //  }
   //  }
    
     @FXML
    void search_Randonnee(ActionEvent event) {
        rand_id.setCellValueFactory(new PropertyValueFactory<Randonnee, Integer>("id"));
        rand_vdepart.setCellValueFactory(new PropertyValueFactory<Randonnee, String>("villedepart"));
        rand_varrive.setCellValueFactory(new PropertyValueFactory<Randonnee, String>("villearrivee"));
        rand_dated.setCellValueFactory(new PropertyValueFactory<Randonnee, String>("datedepart"));
        rand_dater.setCellValueFactory(new PropertyValueFactory<Randonnee, String>("dateretour"));
        rand_activite.setCellValueFactory(new PropertyValueFactory<Randonnee, String>("activite"));
        rand_desc.setCellValueFactory(new PropertyValueFactory<Randonnee, String>("description"));
        rand_duree.setCellValueFactory(new PropertyValueFactory<Randonnee, Integer>("duree"));
        rand_diff.setCellValueFactory(new PropertyValueFactory<Randonnee, String>("difficulte"));
        rand_budget.setCellValueFactory(new PropertyValueFactory<Randonnee, Integer>("budget"));
        tableRand.setItems(list);
         FilteredList<Randonnee> filteredData = new FilteredList<>(list, b -> true);  
         txchercher.textProperty().addListener((observable, oldValue, newValue) ->{
           filteredData.setPredicate(randonnee -> {
               if (newValue == null || newValue.isEmpty()) {
               return true;
    }   
               String lowerCaseFilter = newValue.toLowerCase();

               if (randonnee.getVilledepart().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
               return true;
               }
               else if (randonnee.getVillearrivee().toLowerCase().indexOf(lowerCaseFilter) != -1) {
               return true;
               }
               else if (randonnee.getDatedepart().toLowerCase().indexOf(lowerCaseFilter) != -1) {
               return true;
               }
               else if (randonnee.getDateretour().toLowerCase().indexOf(lowerCaseFilter) != -1) {
               return true;
               } else if (randonnee.getActivite().toLowerCase().indexOf(lowerCaseFilter) != -1) {
               return true;
               }
               else if (randonnee.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
               return true;
               }
               else if (randonnee.getDifficulte().toLowerCase().indexOf(lowerCaseFilter) != -1) {
               return true;
               }
              else
                   return false;
               
               });
  }); 
               
               
               
               
               
               
    }
               
      

    
    public void download() throws FileNotFoundException{
                Randonnee r=tableRand.getSelectionModel().getSelectedItem();
                 Document document = new Document(PageSize.A4);
        document.addAuthor("brahim");
        document.addTitle("Randonne");
        
        
         try {
             PdfWriter.getInstance(document, new FileOutputStream("download"+".pdf"));
             document.open();
        System.out.println("aaaa");
        Paragraph para = new Paragraph(r.toString());
      
        document.add(para);
        document.add(para);
        document.add(para);
        document.add(para);
        document.add(para);
        document.add(para);
        document.add(para);
        document.close();
        System.out.println("Document generated"); 
         } catch (DocumentException ex) {
             Logger.getLogger(AfficheRandonneeController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
         
         
         

    }
    	 public String getFile(String image) throws IOException {
		 Path temp = Files.move 
			        (Paths.get("C:\\Users\\Pc\\Desktop\\Document\\image\\"+image),  
			        Paths.get("C:\\Users\\Document\\"+image)); 
			  
			        if(temp != null) 
			        { 
			            System.out.println("File renamed and moved successfully"); 
			        } 
			        else
			        { 
			            System.out.println("Failed to move the file"); 
			        }
	 return "Success";
	 }
  
        

    
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
         
    
       
    }
    
    

