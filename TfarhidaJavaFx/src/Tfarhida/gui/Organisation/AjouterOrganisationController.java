package Tfarhida.gui.Organisation;
import Tfarhida.entities.Organisation;
import Tfarhida.services.OrganisationService;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

public class AjouterOrganisationController implements Initializable {

    @FXML
    private Button btnAjouter;
    @FXML
    private TextField txtnbrjours;
    @FXML
    private DatePicker txtdate;
    @FXML
    private TextArea txtcommentaire;
    @FXML
    private TextField txtnbrpersonne;

    @FXML
    private ComboBox selectActivite;

    @FXML
    private ComboBox selectLieu;

    void select(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

// list des activites
        ObservableList<String> list = FXCollections.observableArrayList("Camping", "TourVelo", "CircuitSahara");
        selectActivite.setItems(list);
        //liste des lieux
        ObservableList<String> listlieu = FXCollections.observableArrayList("Tunis", "Beja", "Jendouba");
        selectLieu.setItems(listlieu);

    }

    @FXML
    private void addOrganisation(ActionEvent event) throws IOException, ParseException {
        OrganisationService orgser;
        String nbrjours = txtnbrjours.getText();
        int nb = Integer.parseInt(nbrjours);
        String nbrpersonne = txtnbrpersonne.getText();
        String commentaire = txtcommentaire.getText();
        String activite = (String) selectActivite.getValue();
        String lieu = (String) selectLieu.getValue();

        //Recuperation de la date ( conversion String to Date )
        String date = String.valueOf(txtdate.getValue());
        System.out.println("Date = : " + date);
        Date dd = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        // DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        //obtenir la date courante
        Date dateSystem = new Date();
        //Condition si la date est inférieur à la date system 
        if (dd.before(dateSystem)) {
            JOptionPane.showMessageDialog(null, "Date non valide , merci de bien vérifier la date svp");
            return;
        }

        Organisation o = new Organisation(50, nb, nbrpersonne, dd, activite, commentaire, lieu, "En attente");
        orgser = new OrganisationService();
        orgser.ajouterOrganisation(o);
        JOptionPane.showMessageDialog(null, "Votre demande de programme a été envoyer");

        // Date date=txtdate.getDate();
        //  String activite=selectActivite. ();
        // String Lieu=selectLieu.();
        //Organisation o=new Organisation (4, nbrjours,  nbrpersonne,  date, activite, commentaire, Lieu,etat);
        //OrganisationService os=new OrganisationService();
        //os.ajouterOrganisation(o);
        //C:\Users\21651\OneDrive\Documents\NetBeansProjects\TfarhidaJavaFx\src\ 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tfarhida/gui/Menu/Accueil.fxml"));

//   FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tfarhida/gui/AfficherOrganisation.fxml"));
        Parent root = loader.load();
        txtnbrjours.getScene().setRoot(root);
    }

    @FXML
    private void selectactivite(ActionEvent event) {
    }

    @FXML
    private void selectlieu(ActionEvent event) {
    }
}
