/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.reclamation;

import Tfarhida.entities.Reclamation;
import Tfarhida.services.ServiceReclamation;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class ImprimerRecController implements Initializable {

    @FXML
    private TableView<Reclamation> tableReclamation;
    @FXML
    private Button fximprimer;
    @FXML
    private TableColumn<Reclamation, String> fxref;
    @FXML
    private TableColumn<Reclamation, String> fxobjet;
    @FXML
    private TableColumn<Reclamation, String> fxmessage;

    public ObservableList<Reclamation> items = FXCollections.observableArrayList();

    List<Reclamation> rec = new ArrayList();
    private Window primaryStage;
    @FXML
    private JFXTextField tObjet;
    @FXML
    private TextArea tMessage;
    @FXML
    private JFXTextField tfRef;
    @FXML
    private VBox papier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceReclamation rec = new ServiceReclamation();
        ObservableList<Reclamation> items = FXCollections.observableArrayList(rec.afficherReclamation());

        fxref.setCellValueFactory(new PropertyValueFactory("ref"));
        fxobjet.setCellValueFactory(new PropertyValueFactory("Objet"));
        fxmessage.setCellValueFactory(new PropertyValueFactory("MSG"));

        tableReclamation.setItems(items);

    }

    @FXML
    private void BPDF(ActionEvent event) {

        System.out.println("To Printer!");
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            Window primaryStage = null;
            job.showPrintDialog(this.primaryStage);

            Node root = this.tMessage;
            
            job.printPage(root);
            job.endJob();
            
            
        }
    }

    @FXML
    private void afficheDet(MouseEvent event) {
        
          Reclamation r = tableReclamation.getSelectionModel().getSelectedItem();
          String s = String.valueOf(r.getRef());
          tfRef.setText(s);
          tObjet.setText(r.getObjet());
          tMessage.setText(r.getMSG());

    }

  
   

}
