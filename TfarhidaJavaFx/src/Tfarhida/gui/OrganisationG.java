package Tfarhida.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class OrganisationG extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
            //Pour evaluer l'organisation :
          //  Parent root = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/EvaluerOrganisation.fxml"));
            //Afficher les meilleurs organisation
//Parent root = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/AfficheBestOrg.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/Menu/Accueil.fxml"));
            //   Parent root = FXMLLoader.load(getClass().getResource("AjouterOrganisation.fxml"));
              // Parent root = FXMLLoader.load(getClass().getResource("OrganisationAdmin.fxml"));
            //           Parent root = FXMLLoader.load(getClass().getResource("AfficherOrganisation.fxml"));
            Scene scene = new Scene(root, 1900, 900);

            primaryStage.setTitle("");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(OrganisationG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
