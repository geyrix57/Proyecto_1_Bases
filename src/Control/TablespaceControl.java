/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.BaseDatos.DataBase;
import Modelo.Tablespace.Tablespace;
import Modelo.Tablespace.TablespaceModel;
import Vista.Proyecto_Bases;
import java.net.URL;
import java.sql.SQLException;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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

    @FXML
    StackedBarChart tablespacebarchart;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.tableTS.setEditable(true);

        model = new TablespaceModel();

        DataBase.getInstance().addObserver(this);
        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("tablespace_name"));
        TableColumn total_bytes = new TableColumn("MB Tamaño");
        total_bytes.setCellValueFactory(new PropertyValueFactory<>("total_bytes"));
        TableColumn mbusados = new TableColumn("MB Usados");
        mbusados.setCellValueFactory(new PropertyValueFactory<>("used_bytes"));
        TableColumn mbfree = new TableColumn("MB Libres");
        mbfree.setCellValueFactory(new PropertyValueFactory<>("free_bytes"));
        TableColumn online_status = new TableColumn("Status");
        online_status.setCellValueFactory(new PropertyValueFactory<>("online_status"));
        TableColumn file_name = new TableColumn("Fichero");
        file_name.setCellValueFactory(new PropertyValueFactory<>("file_name"));
        this.tableTS.setItems(model.getDatostabla());
        this.tableTS.getColumns().addAll(name, total_bytes, mbusados, mbfree, online_status, file_name);
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
        
        final Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: 35 arial;");

        for (final PieChart.Data data : tablespacepiechart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_RELEASED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf(data.getPieValue()));
                        }
                    });
        }
        ((Group) Proyecto_Bases.STAGE.getScene().getRoot()).getChildren().addAll(caption);

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Usado");
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Libre");
        for (Tablespace tsd : model.getDatostabla()) {

            series2.getData().add(new XYChart.Data(tsd.getTablespace_name(), Integer.parseInt(tsd.getUsed_bytes())));
            series3.getData().add(new XYChart.Data(tsd.getTablespace_name(), Integer.parseInt(tsd.getFree_bytes())));
        }
        this.tablespacebarchart.getData().addAll(series2, series3);
         
        
    }

}
