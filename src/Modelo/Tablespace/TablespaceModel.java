/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Tablespace;

import Modelo.BaseDatos.DataBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/**
 *
 * @author aaron
 */
public class TablespaceModel {

/////////////////////////////////////////////////////////    
    DataBase database;
    ObservableList<PieChart.Data> pieChartData;
    ObservableList<Tablespace> datostabla;
    /*Consultas SQL*/
    String consulTS;
    String consulTS1;

    public TablespaceModel() {
        database = DataBase.getInstance();

        //db.changeUser("sys as sysdba", "root"); //se conecta automanticamente a XE en localhost puerto 1521
        //database.setConnection("localhost", 1521, "XE", "sys as sysdba", "root");
        
        this.pieChartData = FXCollections.observableArrayList();
        datostabla = FXCollections.observableArrayList();
        this.consulTS = "SELECT tablespace_name, ROUND(bytes/1024000) FROM dba_data_files";
        this.consulTS1 = "SELECT tablespace_name, ROUND(bytes/1024000), online_status,file_name FROM dba_data_files";

    }

    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }

    public void ActualizarListTabla() throws SQLException {
        if(!database.isConnected()) return;
        ResultSet result = this.database.ExecuteQuery(consulTS1);
        this.datostabla.clear();
        while (result.next()) {
            String tsname = result.getString("tablespace_name");
            int mb = result.getInt("ROUND(bytes/1024000)");
            String mb1 = String.valueOf(mb);
            String file_name = result.getString("file_name");
            String status = result.getString("online_status");
            this.datostabla.add(new Tablespace(tsname, mb1, status, file_name));
        }
        result.getStatement().close();
    }

    public void ActualizarListaPieChart() throws SQLException {
        if(!database.isConnected()) return;
        ResultSet result = this.database.ExecuteQuery(consulTS);
        this.pieChartData.clear();
        while (result.next()) {
            String tsname = result.getString("tablespace_name");
            int mb = result.getInt("ROUND(bytes/1024000)");
            this.pieChartData.add(new PieChart.Data(tsname, mb));
        }
        result.getStatement().close();
    }

    public ObservableList<Tablespace> getDatostabla() {
        return datostabla;
    }

    public void setDatostabla(ObservableList<Tablespace> datostabla) {
        this.datostabla = datostabla;
    }
    

}
