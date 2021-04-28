/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.randonne;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author PC
 */
public class TestReservation extends Application {
    
    public void start(Stage primaryStage) {
     try {
           
           
            Parent root = FXMLLoader.load(getClass().getResource("/Tfarhida/gui/Menu/Accueil.fxml"));
             Scene scene = new Scene(root);
            
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
