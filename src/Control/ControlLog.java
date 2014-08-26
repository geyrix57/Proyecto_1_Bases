/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.BaseDatos.DataBase;
import Modelo.Log.Log;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    
    
    private Timeline tl;
    public TableColumn<Log, String> CStatusId;
    public TableColumn<Log, String> CArchivedId;
    public ObservableList<Log> data = FXCollections.observableArrayList();

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
//        Image ik = new Image(ControlLog.class.getResourceAsStream("resorces/Green_ball.png"));
//        imagen.setImage(ik);
       /* Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    onAddItem();
                    try {
                        sleep(10000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ControlLog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        t.start();*/
        
       // onAddItem();
        this.tablaId.setItems(data);
        
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
        /*        tablaId.setItems(data);
         Image ik = new Image(ControlLog.class.getResourceAsStream("resorces/red.png"));
         imagen.setImage(ik);*/

       

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
