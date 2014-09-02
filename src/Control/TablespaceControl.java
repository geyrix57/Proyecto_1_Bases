/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Modelo.BaseDatos.DataBase;
import Modelo.Tablespace.Tablespace;
import Modelo.Tablespace.TablespaceModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author aaron
 */
public class TablespaceControl implements Initializable, Observer {

    @FXML
    Label label;
    @FXML
    PieChart tablespacepiechart;
    @FXML
    TablespaceModel model;
    @FXML
    TableView tableTS;
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        this.tableTS.setEditable(true);
        
        model = new TablespaceModel();
        
        DataBase.getInstance().addObserver(this);
        TableColumn name= new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("tablespace_name"));
        TableColumn total_bytes= new TableColumn("MB Tamaño");
        total_bytes.setCellValueFactory(new PropertyValueFactory<>("total_bytes"));
        TableColumn mbusados= new TableColumn("MB Usados");
        mbusados.setCellValueFactory(new PropertyValueFactory<>("used_bytes"));
        TableColumn mbfree= new TableColumn("MB Libres");
        mbfree.setCellValueFactory(new PropertyValueFactory<>("free_bytes"));
        TableColumn online_status= new TableColumn("Status");
        online_status.setCellValueFactory(new PropertyValueFactory<>("online_status"));
        TableColumn file_name= new TableColumn("Fichero");
        file_name.setCellValueFactory(new PropertyValueFactory<>("file_name"));
        this.tableTS.setItems(model.getDatostabla());
        this.tableTS.getColumns().addAll(name,total_bytes,mbusados,mbfree,online_status,file_name);
    }    

    @Override
    public void update(java.util.Observable o, Object arg) {
        //revisar si esta conectado
        try {
            model.ActualizarListTabla();
        } catch (SQLException ex) {
            Logger.getLogger(TablespaceControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            model.ActualizarListaPieChart();
        } catch (SQLException ex) {
            Logger.getLogger(TablespaceControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.tablespacepiechart.setData(model.getPieChartData());
    }
    
}
