/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.BaseDatos.DataBase;
import Modelo.Log.Log;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author Fran
 */
public class ControlLog implements Initializable {

    @FXML
    TableView<Log> tablaId;
    @FXML
    TableColumn<Log, Integer> CGroupId;
    @FXML
    TableColumn<Log, Integer> CMembersId;
    @FXML
    TableColumn<Log, Integer> CSizeId;
    @FXML
    ImageView imagen;

    @FXML
    Button asd;

    @FXML
    Button refresh;

    @FXML
    AnchorPane root;

    public TableColumn<Log, String> CStatusId;
    public ObservableList<Log> data = FXCollections.observableArrayList(new Log(12, 45, 78, "cu"), new Log(89, 56, 23, "cu"));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.CGroupId.setCellValueFactory(new PropertyValueFactory<Log, Integer>("group"));
        this.CMembersId.setCellValueFactory(new PropertyValueFactory<Log, Integer>("members"));
        this.CSizeId.setCellValueFactory(new PropertyValueFactory<Log, Integer>("size"));

        this.CStatusId = new TableColumn<Log, String>("Status");
        this.CStatusId.setCellValueFactory(new PropertyValueFactory<Log, String>("status"));
        this.CStatusId.setCellFactory(new Callback<TableColumn<Log, String>, TableCell<Log, String>>() {

            @Override
            public TableCell<Log, String> call(TableColumn<Log, String> param) {
                TableCell<Log, String> cell = new TableCell<Log, String>() {
                   
                    @Override
                    public void updateIndex(int i){//Log item, boolean empty) {
                        
                        if (i < data.size()) {
                            HBox box = new HBox();
                            box.setSpacing(10);
                            VBox vbox = new VBox();

                            ImageView imageview = new ImageView();
                            imageview.setFitHeight(50);
                            imageview.setFitWidth(50);
//                            imageview.setImage(new Image(ControlLog.class.getResourceAsStream("resorces/red.png")));

                            box.getChildren().addAll(imageview, vbox);
                            //SETTING ALL THE GRAPHICS COMPONENT FOR CELL
                            setGraphic(box);
                            
                        }
                    }
                };
                return cell;
            }
        });

        tablaId.getColumns().add(CStatusId);

        this.tablaId.setItems(data);
//        Image ik = new Image(ControlLog.class.getResourceAsStream("resorces/Green_ball.png"));
//        imagen.setImage(ik);

    }

    public void onAddItem(ActionEvent e) {
        data.add(null);
        //Random rn = new Random();
        //data=FXCollections.observableArrayList(new Log(rn.nextInt()%5,rn.nextInt()%5,rn.nextInt()%5),new Log(rn.nextInt()%5,rn.nextInt()%5,rn.nextInt()%5));
        //Modelo m = new Modelo("sys as sysdba", "root", 123); ///Query
        //ArrayList<Log> nueva = m.queryLogs();
        
        ArrayList<Log> nueva=new ArrayList<Log>();

            DataBase db = DataBase.getInstance();
            if(db.isConnected()){
            try {
                ResultSet resp= db.ExecuteQuery("SELECT * FROM V$LOG");
                while(resp.next()){
                    /////////////////////// los numeros son los numeros de columna y empiezan en 1 aparentemente
                    Log aux=new Log(resp.getInt(1),resp.getInt(6),resp.getInt(4)/(1024*1024),"cu");
                    nueva.add(aux);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControlLog.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        
        
        data.clear();
        int i = 0;
        while (i < nueva.size()) {
            data.add(nueva.get(i));
            i++;
        }
/*        tablaId.setItems(data);
        Image ik = new Image(ControlLog.class.getResourceAsStream("resorces/red.png"));
        imagen.setImage(ik);*/

        TableCell s;
        
    }

}
