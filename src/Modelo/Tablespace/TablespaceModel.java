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
    String consulTS2;

    public TablespaceModel() {
        database = DataBase.getInstance();

        //db.changeUser("sys as sysdba", "root"); //se conecta automanticamente a XE en localhost puerto 1521
        //database.setConnection("localhost", 1521, "XE", "sys as sysdba", "root");
        this.pieChartData = FXCollections.observableArrayList();
        datostabla = FXCollections.observableArrayList();
        this.consulTS = "SELECT tablespace_name, ROUND(bytes/1024000) FROM dba_data_files";
        this.consulTS1 = "SELECT tablespace_name, ROUND(bytes/1024000), online_status,file_name FROM dba_data_files";
        this.consulTS2 = "Select t.tablespace_name \"Tablespace\", t.status \"Estado\",\n"
                + "ROUND(MAX(d.bytes)/1024/1024,2) \"MB Tamaño\",\n"
                + "ROUND((MAX(d.bytes)/1024/1024) -\n"
                + "(SUM(decode(f.bytes, NULL,0, f.bytes))/1024/1024),2) \"MB Usados\",\n"
                + "ROUND(SUM(decode(f.bytes, NULL,0, f.bytes))/1024/1024,2) \"MB Libres\",\n"
                + "t.pct_increase \"% incremento\",\n"
                + "SUBSTR(d.file_name,1,80) \"Fichero de datos\"\n"
                + "FROM DBA_FREE_SPACE f, DBA_DATA_FILES d, DBA_TABLESPACES t\n"
                + "WHERE t.tablespace_name = d.tablespace_name AND\n"
                + "f.tablespace_name(+) = d.tablespace_name\n"
                + "AND f.file_id(+) = d.file_id GROUP BY t.tablespace_name,\n"
                + "d.file_name, t.pct_increase, t.status ORDER BY 1,3 DESC";
    }

    

    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }

    public void ActualizarListTabla() throws SQLException {
        if (!database.isConnected()) {
            return;
        }
        ResultSet result = this.database.ExecuteQuery(consulTS2);
        this.datostabla.clear();
        while (result.next()) {
            String tsname = result.getString("Tablespace");
            int mb = result.getInt("MB Tamaño");
            String mb1 = String.valueOf(mb);
            int usemb=result.getInt("MB Usados");
            String usemb1=String.valueOf(usemb);
            int freemb=result.getInt("MB Libres");
            String freemb1=String.valueOf(freemb);
            String file_name = result.getString("Fichero de datos");
            String status = result.getString("Estado");
            this.datostabla.add(new Tablespace(tsname, mb1, status, file_name, usemb1,freemb1));
        }
        result.getStatement().close();
    }

    public void ActualizarListaPieChart() throws SQLException {
        if (!database.isConnected()) {
            return;
        }
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
