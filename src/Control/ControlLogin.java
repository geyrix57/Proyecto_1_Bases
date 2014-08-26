/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.BaseDatos.DataBase;
import Vista.Proyecto_Bases;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * FXML Controller class
 *
 * @author aaron
 */
public class ControlLogin implements Initializable,ControlledScreen {

    //Componentes del Login.fxml
    @FXML
    TextField Hostinput;
    @FXML
    TextField Portinput;
    @FXML
    TextField Usernameinput;
    @FXML
    TextField Passwordinput;
    @FXML
    CheckBox Hostcheck;
    @FXML
    CheckBox Portcheck;
    @FXML
    Button connect;
    @FXML
    TextField SIDInput;
    @FXML
    CheckBox SIDcheck;
    @FXML
    CheckBox UsernameCheck;
    
    ScreensController myController;
    
    JPanel panel = new JPanel();
    //Stage stage;
    String host, port, username, password, sid;
    /*-------------------------------------------------*/

    /*-------------------------------------------------*/
    /*public void setStage(Stage stage) {
        this.stage = stage;
    }*/

    private void ErrorDialog(String msg) {
//        Dialogs.create()
//        .owner(stage)
//        .title("Error Dialog")
//        .masthead("Look, an Error Dialog")
//        .message(msg)
//        .showError();
    }

    @FXML
    private void ConnectButtomAction(ActionEvent event) {
        this.host = Hostinput.getText();

        this.port = Portinput.getText();

        this.username = Usernameinput.getText();

        this.password = Passwordinput.getText();

        this.sid = SIDInput.getText();

        int port1 = 0;

        try {
            port1 = Integer.parseInt(port);
        } catch (NumberFormatException e) {

            this.ErrorDialog("El puerto no es un entero");
            return;
        }

        DataBase db = DataBase.getInstance();
        if ("".equals(host)) {
            this.ErrorDialog("El hostname esta vacio");
        } else if ("".equals(port)) {
            this.ErrorDialog("El Port esta vacio");
        } else if ("".equals(username)) {
            this.ErrorDialog("El Username esta vacio");
        } else if ("".equals(password)) {
            this.ErrorDialog("El Password esta vacio");
        } else if ("".equals(sid)) {
            this.ErrorDialog("El SID esta vacio");
        } else {
            db.setConnection(host, port1, sid, username, password);
            myController.setScreen(Proyecto_Bases.screen2ID);
                /*Parent root = FXMLLoader.load(Proyecto_Bases.class.getResource("vistaGrid.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();*/
        }

    }

    @FXML
    private void HostCheckAction(ActionEvent event) {

        if (this.Hostcheck.isSelected()) {
            Hostinput.setText("localhost");
        } else {
            Hostinput.setText("");
        }

    }

    @FXML
    private void PortCheckAction(ActionEvent event) {
        if (this.Portcheck.isSelected()) {
            Portinput.setText("1521");
        } else {
            Portinput.setText("");
        }

    }

    @FXML
    private void SIDCheckAction(ActionEvent event) {
         if (this.SIDcheck.isSelected()) {
            SIDInput.setText("XE");
        } else {
            SIDInput.setText("");
        }

        

    }
    
    @FXML void UsernameCheckAction(ActionEvent event){
        if(this.UsernameCheck.isSelected()){
            this.Usernameinput.setText("sys as sysdba");
            
        }
        else{
            this.Usernameinput.setText("");
        }
    
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

}
