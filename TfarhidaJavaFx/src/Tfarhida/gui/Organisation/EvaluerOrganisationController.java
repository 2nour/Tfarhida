/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.Organisation;
import Tfarhida.entities.Organisation;
import Tfarhida.services.OrganisationService;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author 21651
 */
public class EvaluerOrganisationController implements Initializable {

    public static int idOrganisation = 0;
    @FXML
    private TableView<Organisation> tableview;
    @FXML
    private TableColumn<?, ?> clnid;
    @FXML
    private TableColumn<?, ?> clnbpersonne;
    @FXML
    private TableColumn<?, ?> clnnbJours;
    @FXML
    private TableColumn<?, ?> clndate;
    @FXML
    private TableColumn<?, ?> clnactivite;
    @FXML
    private TableColumn<?, ?> clnlieu;
    @FXML
    private TableColumn<?, ?> clnCommentaire;
    @FXML
    private TableColumn<?, ?> clnEtat;
    @FXML
    private ImageView etoile1;
    @FXML
    private ImageView etoile2;
    @FXML
    private ImageView etoile3;
    @FXML
    private ImageView etoile4;
    @FXML
    private ImageView etoile5;
    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            OrganisationService os = new OrganisationService();
            List<Organisation> lo = os.afficherOrganisation();
            ObservableList<Organisation> data = FXCollections.observableArrayList(lo);
            clnid.setCellValueFactory(new PropertyValueFactory<>("id"));

            clnbpersonne.setCellValueFactory(new PropertyValueFactory<>("nbrpersonne"));
            clnCommentaire.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
            clnEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            clnnbJours.setCellValueFactory(new PropertyValueFactory<>("nbrjours"));
            clndate.setCellValueFactory(new PropertyValueFactory<>("date"));
            clnactivite.setCellValueFactory(new PropertyValueFactory<>("activite"));
            clnlieu.setCellValueFactory(new PropertyValueFactory<>("Lieu"));
            tableview.setItems(data);

            //les images 
            etoile1.setImage(new Image("/image/empty.png"));
            etoile2.setImage(new Image("/image/empty.png"));
            etoile3.setImage(new Image("/image/empty.png"));
            etoile4.setImage(new Image("/image/empty.png"));
            etoile5.setImage(new Image("/image/empty.png"));

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ligneselectionner(MouseEvent event) throws SQLException {
        OrganisationService os = new OrganisationService();
        Organisation o = tableview.getSelectionModel().getSelectedItem();
        EvaluerOrganisationController.idOrganisation = o.getId();
        int noteOrganisation = os.getNoteOrganisationById(idOrganisation) ;
        if (noteOrganisation == 1) {
            etoile1.setImage(new Image("/image/filled.png"));
            etoile2.setImage(new Image("/image/empty.png"));
            etoile3.setImage(new Image("/image/empty.png"));
            etoile4.setImage(new Image("/image/empty.png"));
            etoile5.setImage(new Image("/image/empty.png"));
        }

        if (noteOrganisation == 2) {
            etoile1.setImage(new Image("/image/filled.png"));
            etoile2.setImage(new Image("/image/filled.png"));
            etoile3.setImage(new Image("/image/empty.png"));
            etoile4.setImage(new Image("/image/empty.png"));
            etoile5.setImage(new Image("/image/empty.png"));
        }
        if (noteOrganisation == 3) {
            etoile1.setImage(new Image("/image/filled.png"));
            etoile2.setImage(new Image("/image/filled.png"));
            etoile3.setImage(new Image("/image/filled.png"));
            etoile4.setImage(new Image("/image/empty.png"));
            etoile5.setImage(new Image("/image/empty.png"));
        }
        if (noteOrganisation == 4) {
            etoile1.setImage(new Image("/image/filled.png"));
            etoile2.setImage(new Image("/image/filled.png"));
            etoile3.setImage(new Image("/image/filled.png"));
            etoile4.setImage(new Image("/image/filled.png"));
            etoile5.setImage(new Image("/image/empty.png"));
        }
        if (noteOrganisation == 5) {
            etoile1.setImage(new Image("/image/filled.png"));
            etoile2.setImage(new Image("/image/filled.png"));
            etoile3.setImage(new Image("/image/filled.png"));
            etoile4.setImage(new Image("/image/filled.png"));
            etoile5.setImage(new Image("/image/filled.png"));
        }

        if ((o.getEtat().equals("Refuser")) || (o.getEtat().equals("en attente"))) {
            etoile1.setImage(null);
            etoile2.setImage(null);
            etoile3.setImage(null);
            etoile4.setImage(null);
            etoile5.setImage(null);

            JOptionPane.showMessageDialog(null, "Vous ne pouvez pas évaluer une organisation qui n'est pas encore accepté");

        }

    }

    @FXML
    private void mettreetoile1(MouseEvent event) throws SQLException {
        Organisation o = tableview.getSelectionModel().getSelectedItem();
        OrganisationService og = new OrganisationService();
        System.out.println("ID =" + EvaluerOrganisationController.idOrganisation);
        og.mettreEvaluation(EvaluerOrganisationController.idOrganisation, 1);
        etoile1.setImage(new Image("/image/filled.png"));
        etoile2.setImage(new Image("/image/empty.png"));
        etoile3.setImage(new Image("/image/empty.png"));
        etoile4.setImage(new Image("/image/empty.png"));
        etoile5.setImage(new Image("/image/empty.png"));

    }

    @FXML
    private void mettreetoile2(MouseEvent event) throws SQLException {
        Organisation o = tableview.getSelectionModel().getSelectedItem();
        OrganisationService og = new OrganisationService();
        System.out.println("ID =" + EvaluerOrganisationController.idOrganisation);
        og.mettreEvaluation(EvaluerOrganisationController.idOrganisation, 2);
        etoile1.setImage(new Image("/image/filled.png"));
        etoile2.setImage(new Image("/image/filled.png"));
        etoile3.setImage(new Image("/image/empty.png"));
        etoile4.setImage(new Image("/image/empty.png"));
        etoile5.setImage(new Image("/image/empty.png"));
    }

    @FXML
    private void mettreetoile3(MouseEvent event) throws SQLException {
        Organisation o = tableview.getSelectionModel().getSelectedItem();
        OrganisationService og = new OrganisationService();
        System.out.println("ID =" + EvaluerOrganisationController.idOrganisation);
        og.mettreEvaluation(EvaluerOrganisationController.idOrganisation, 3);
        etoile1.setImage(new Image("/image/filled.png"));
        etoile2.setImage(new Image("/image/filled.png"));
        etoile3.setImage(new Image("/image/filled.png"));
        etoile4.setImage(new Image("/image/empty.png"));
        etoile5.setImage(new Image("/image/empty.png"));
    }

    @FXML
    private void mettreetoile4(MouseEvent event) throws SQLException {
        Organisation o = tableview.getSelectionModel().getSelectedItem();
        OrganisationService og = new OrganisationService();
        System.out.println("ID =" + EvaluerOrganisationController.idOrganisation);
        og.mettreEvaluation(EvaluerOrganisationController.idOrganisation, 4);
        etoile1.setImage(new Image("/image/filled.png"));
        etoile2.setImage(new Image("/image/filled.png"));
        etoile3.setImage(new Image("/image/filled.png"));
        etoile4.setImage(new Image("/image/filled.png"));
        etoile5.setImage(new Image("/image/empty.png"));
    }

    @FXML
    private void mettreetoile5(MouseEvent event) throws SQLException {
        Organisation o = tableview.getSelectionModel().getSelectedItem();
        OrganisationService og = new OrganisationService();
        System.out.println("ID =" + EvaluerOrganisationController.idOrganisation);
        og.mettreEvaluation(EvaluerOrganisationController.idOrganisation, 5);
        etoile1.setImage(new Image("/image/filled.png"));
        etoile2.setImage(new Image("/image/filled.png"));
        etoile3.setImage(new Image("/image/filled.png"));
        etoile4.setImage(new Image("/image/filled.png"));
        etoile5.setImage(new Image("/image/filled.png"));
    }

}
