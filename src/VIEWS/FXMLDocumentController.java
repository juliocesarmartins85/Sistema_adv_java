/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEWS;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author Julio
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private Label label;
    @FXML
    private MenuItem cadc;
    {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLcadc.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
