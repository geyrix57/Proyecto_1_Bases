/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Vista.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Estudiante
 */
public class TabControl  implements Initializable, ControlledScreen{

    @FXML
    TabPane pest;
    
    ScreensController myController;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            AnchorPane log=(AnchorPane) FXMLLoader.load(Proyecto_Bases.class.getResource("vistaLogs.fxml"));
            AnchorPane sga=(AnchorPane) FXMLLoader.load(Proyecto_Bases.class.getResource("Ventana_SGA.fxml"));
            AnchorPane ts=(AnchorPane) FXMLLoader.load(Proyecto_Bases.class.getResource("Tablespace.fxml"));
            
            pest.getTabs().get(0).setContent(sga);
            pest.getTabs().get(1).setContent(ts);
            pest.getTabs().get(2).setContent(log);
        } catch (IOException ex) {
            Logger.getLogger(TabControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
    
}
