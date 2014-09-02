/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.BaseDatos.DataBase;
import Modelo.Log.Log;
import Modelo.Log.LogIndividual;
import Vista.BotonIr;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
import javafx.util.Duration;

/**
 *
 * @author Fran
 */
public class ControlLog implements Observer, Initializable {

    @FXML
    TableView<Log> tablaId;
    @FXML
    TableView<LogIndividual> tablaId2;
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
    
    @FXML
    Label panel;
    
    
    @FXML
    TableColumn<LogIndividual, Integer> CGroupId2;
    
    
    public TableColumn<LogIndividual, String> CStatusId2;
    public TableColumn<LogIndividual, String> CDireccionId;
    public TableColumn<LogIndividual, String> CbotonId;
    
    private Timeline tl;
    public TableColumn<Log, String> CStatusId;
    public TableColumn<Log, String> CArchivedId;
    public ObservableList<Log> data = FXCollections.observableArrayList();
    public ObservableList<LogIndividual> data2 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(10), 
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    onAddItem();
            }
        }));
        tl.setCycleCount(Animation.INDEFINITE);
        
        this.CGroupId.setCellValueFactory(new PropertyValueFactory<Log, Integer>("group"));
        this.CGroupId2.setCellValueFactory(new PropertyValueFactory<LogIndividual, Integer>("group"));
        this.CMembersId.setCellValueFactory(new PropertyValueFactory<Log, Integer>("members"));
        this.CSizeId.setCellValueFactory(new PropertyValueFactory<Log, Integer>("size"));

        this.CStatusId = new TableColumn<Log, String>("Status");
        this.CStatusId.setCellValueFactory(new PropertyValueFactory<Log, String>("status"));
        this.CStatusId.setCellFactory(new Callback<TableColumn<Log, String>, TableCell<Log, String>>() {

            @Override
            public TableCell<Log, String> call(TableColumn<Log, String> param) {
                TableCell<Log, String> cell;
                cell = new TableCell<Log, String>() {

                    @Override
                    public void updateIndex(int i) {//Log item, boolean empty) {

                        if (i < data.size() && i >= 0) {
                            HBox box = new HBox();
                            box.setSpacing(10);
                            VBox vbox = new VBox();

                            ImageView imageview = new ImageView();
                            imageview.setFitHeight(30);
                            imageview.setFitWidth(30);
                            Log aux = data.get(i);
                            if (aux != null) {
                                String stat = aux.getStatus();
                                if (stat != null) {
                                    if (stat.equals("CURRENT")) {
                                        imageview.setImage(new Image(ControlLog.class.getResourceAsStream("resources/Star.png")));
                                    }
                                    if (stat.equals("INACTIVE")) {
                                        imageview.setImage(new Image(ControlLog.class.getResourceAsStream("resources/red.png")));
                                    }
                                    if (stat.equals("ACTIVE")) {
                                        imageview.setImage(new Image(ControlLog.class.getResourceAsStream("resources/Green_ball.png")));
                                    }
                                    if (stat.equals("UNUSED")) {
                                        imageview.setImage(new Image(ControlLog.class.getResourceAsStream("resources/new.jpg")));
                                    }
                                    if (stat.equals("CLEARING")) {
                                        imageview.setImage(new Image(ControlLog.class.getResourceAsStream("resources/clear.jpg")));
                                    }
                                    if (stat.equals("CLEARING_CURRENT")) {
                                        imageview.setImage(new Image(ControlLog.class.getResourceAsStream("resources/clear2.jpg")));
                                    }

                                    box.getChildren().addAll(imageview, vbox);
                                    //SETTING ALL THE GRAPHICS COMPONENT FOR CELL
                                    setGraphic(box);

                                }
                            }
                        }
                    }
                };
                return cell;
            }
        });

        tablaId.getColumns().add(CStatusId);
        /////////////////////////////////////////////////////////////////////////////////////////////////

       this.CArchivedId = new TableColumn<Log, String>("Archived");
        this.CArchivedId.setCellValueFactory(new PropertyValueFactory<Log, String>("archived"));
        this.CArchivedId.setCellFactory(new Callback<TableColumn<Log, String>, TableCell<Log, String>>() {

            @Override
            public TableCell<Log, String> call(TableColumn<Log, String> param) {
                TableCell<Log, String> cell;
                cell = new TableCell<Log, String>() {

                    @Override
                    public void updateIndex(int i) {//Log item, boolean empty) {

                        if (i < data.size() && i >= 0) {
                            HBox box = new HBox();
                            box.setSpacing(10);
                            VBox vbox = new VBox();

                            ImageView imageview = new ImageView();
                            imageview.setFitHeight(30);
                            imageview.setFitWidth(30);
                            Log aux = data.get(i);
                            if (aux != null) {
                                String stat = aux.getArchived();
                                if (stat != null) {
                                    if (stat.equals("YES")) {
                                        imageview.setImage(new Image(ControlLog.class.getResourceAsStream("resources/cd.png")));
                                    }
                                    if (stat.equals("NO")) {
                                        imageview.setImage(new Image(ControlLog.class.getResourceAsStream("resources/xx.png")));
                                    }

                                    box.getChildren().addAll(imageview, vbox);
                                    //SETTING ALL THE GRAPHICS COMPONENT FOR CELL
                                    setGraphic(box);

                                }
                            }
                        }
                    }
                };
                return cell;
            }
        });

        tablaId.getColumns().add(CArchivedId);

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        this.tablaId.setItems(data);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        this.CStatusId2=new TableColumn<LogIndividual, String>("Status");
        this.CStatusId2.setCellValueFactory(new PropertyValueFactory<LogIndividual, String>("status"));
        this.CStatusId2.setCellFactory(new Callback<TableColumn<LogIndividual, String>, TableCell<LogIndividual, String>>() {

            @Override
            public TableCell<LogIndividual, String> call(TableColumn<LogIndividual, String> param) {
                TableCell<LogIndividual, String> cell;
                cell = new TableCell<LogIndividual, String>() {

                    @Override
                    public void updateIndex(int i) {//Log item, boolean empty) {

                        if (i < data2.size() && i >= 0) {
                            HBox box = new HBox();
                            box.setSpacing(10);
                            VBox vbox = new VBox();

                            ImageView imageview = new ImageView();
                            imageview.setFitHeight(30);
                            imageview.setFitWidth(30);
                            LogIndividual aux = data2.get(i);
                            if (aux != null) {
                                String stat = aux.getStatus();
                                
                                if (stat != null) {
                                  
                                    if (stat.equals("INVALID")) {
                                        imageview.setImage(new Image(ControlLog.class.getResourceAsStream("resources/nop.jpg")));
                                    }
                                    if (stat.equals("STALE")) {
                                        imageview.setImage(new Image(ControlLog.class.getResourceAsStream("resources/inco.jpg")));
                                    }
                                    if (stat.equals("DELETED ")) {
                                        imageview.setImage(new Image(ControlLog.class.getResourceAsStream("resources/del.png")));
                                    }
                             
                                    box.getChildren().addAll(imageview, vbox);
                                    //SETTING ALL THE GRAPHICS COMPONENT FOR CELL
                                    setGraphic(box);

                                }else{
                                imageview.setImage(new Image(ControlLog.class.getResourceAsStream("resources/ok1.jpg")));
                                box.getChildren().addAll(imageview, vbox);
                                 setGraphic(box);
                            }
                            }
                        }
                    }
                };
                return cell;
            }
        });
        
        tablaId2.getColumns().add(CStatusId2);
        
        
        
        
        
        
        this.CbotonId=new TableColumn<LogIndividual, String>("IR");
        this.CbotonId.setCellValueFactory(new PropertyValueFactory<LogIndividual, String>("direccion"));
        this.CbotonId.setCellFactory(new Callback<TableColumn<LogIndividual, String>, TableCell<LogIndividual, String>>() {

            @Override
            public TableCell<LogIndividual, String> call(TableColumn<LogIndividual, String> param) {
                TableCell<LogIndividual, String> cell;
                cell = new TableCell<LogIndividual, String>() {

                    @Override
                    public void updateIndex(int i) {//Log item, boolean empty) {

                        if (i < data2.size() && i >= 0) {
                            HBox box = new HBox();
                            box.setSpacing(10);
                            VBox vbox = new VBox();

                            LogIndividual aux = data2.get(i);
                            if (aux != null) {
                                String stat = aux.getDireccion();
                                if (stat != null) {
                                    BotonIr bt=new BotonIr(stat);
                                    box.getChildren().addAll(bt, vbox);
                                    //SETTING ALL THE GRAPHICS COMPONENT FOR CELL
                                    setGraphic(box);

                                }
                            }
                        }
                    }
                };
                return cell;
            }
        });
        
        tablaId2.getColumns().add(CbotonId);
        this.CDireccionId=new TableColumn<LogIndividual, String>("Direccion");
        this.CDireccionId.setCellValueFactory(new PropertyValueFactory<LogIndividual, String>("direccion"));
         tablaId2.getColumns().add(CDireccionId);
         
         tablaId2.setItems(data2);

        
         DataBase db = DataBase.getInstance();
         db.addObserver(this);
        
         
         
         
         

    }
    @FXML
    public void onAddItem(/*ActionEvent e*/) {

        ArrayList<Log> nueva = new ArrayList<Log>();

        DataBase db = DataBase.getInstance();
        if (db.isConnected()) {
            try {
                ResultSet resp = db.ExecuteQuery("SELECT * FROM V$LOG");
                while (resp.next()) {
                    /////////////////////// los numeros son los numeros de columna y empiezan en 1 aparentemente
                    Log aux = new Log(resp.getInt(1), resp.getInt(6), resp.getInt(4) / (1024 * 1024), resp.getString(8), resp.getString(7));
                    nueva.add(aux);

                }
                resp.close();
            } catch (SQLException ex) {
                Logger.getLogger(ControlLog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (data != null) {
            data.clear();
            int i = 0;
            while (i < nueva.size()) {
                data.add(nueva.get(i));
                i++;
            }
        }
       onAddItem2();
       nextJump();

    }
    
     public void onAddItem2(/*ActionEvent e*/) {

        ArrayList<LogIndividual> nueva = new ArrayList<LogIndividual>();

        DataBase db = DataBase.getInstance();
        if (db.isConnected()) {
            try {
                ResultSet resp = db.ExecuteQuery("SELECT * FROM V$LOGFILE");
                while (resp.next()) {
                    /////////////////////// los numeros son los numeros de columna y empiezan en 1 aparentemente
                   
                    LogIndividual aux=new LogIndividual(resp.getInt(1),resp.getString(2),resp.getString(4));
                    nueva.add(aux);

                }
                resp.close();
            } catch (SQLException ex) {
                Logger.getLogger(ControlLog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (data2 != null) {
            data2.clear();
            int i = 0;
            while (i < nueva.size()) {
                data2.add(nueva.get(i));
                i++;
            }
        }
       

    }
     
     public void nextJump(){/*
         DataBase db = DataBase.getInstance();
        if (db.isConnected()) {
            try {
                ResultSet resp = db.ExecuteQuery("SELECT \n" +
"to_char(first_time,'YYYY-MON-DD') day,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'00',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'01',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'02',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'03',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'04',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'05',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'06',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'07',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'08',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'09',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'10',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'11',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'12',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'13',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'14',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'15',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'16',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'17',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'18',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'19',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'20',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'21',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'22',1,0)),'99') ,\n" +
"to_char(sum(decode(to_char(first_time,'HH24'),'23',1,0)),'99') \n" +
"from\n" +
"   v$log_history\n" +
"GROUP by \n" +
"   to_char(first_time,'YYYY-MON-DD');");
                int prom=0;
                int cant=0;
                Date d=new Date();
                while (resp.next()) {
                   prom+=resp.getInt(d.getHours()+1);
                   cant++;
                    

                }
                resp.close();
                prom/=cant;
                if(prom!=0){
                    panel.setText("Promedio de cabio de\n logs por hora: "+prom);
                }else{
                    panel.setText("los logs no cambiaran hasta\n la proxima hora");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControlLog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
     }

    @Override
    public void update(Observable o, Object arg) {
        if(DataBase.getInstance().isConnected()){
            onAddItem();
            tl.play();
        }
        else tl.stop();
    }
    
   
}
