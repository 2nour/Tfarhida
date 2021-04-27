/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import Tfarhida.gui.Menu.AccueilController;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

/**
 *
 * @author User
 */
public class NewFXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       
        try {
       Parent parent = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/Menu/Accueil.fxml"));
       //Parent parent = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/AjouterMaison.fxml"));
       Scene scene = new Scene(parent);
            
        primaryStage.setTitle("Tfarhida");
        primaryStage.setScene(scene);
        primaryStage.setOpacity(1);
        primaryStage.show();
        } catch (Exception e) {
        }
        
     
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
