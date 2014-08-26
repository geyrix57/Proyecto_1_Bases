/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.ScreensController;
import Modelo.BaseDatos.DataBase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


/**
 *
 * @author Geykel
 */
public class Proyecto_Bases extends Application {
    
    public static String screen1ID = "login";
    public static String screen1File = "Login.fxml";
    public static String screen2ID = "main";
    public static String screen2File = "vistaGrid.fxml";
    public static Stage STAGE;
    
    @Override
    public void start(Stage stage) throws Exception {
//      Parent root = FXMLLoader.load(getClass().getResource("vistaGrid.fxml"));
//      Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = (Parent)loader.load();
        ControlLogin loginControl = (ControlLogin)loader.getController();
        //loginControl.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Oracle Data Base Monitor");
        stage.show();*/
        STAGE = stage;
        
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Proyecto_Bases.screen1ID, Proyecto_Bases.screen1File);
        mainContainer.loadScreen(Proyecto_Bases.screen2ID, Proyecto_Bases.screen2File);
        
        mainContainer.setScreen(Proyecto_Bases.screen1ID);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Oracle Data Base Monitor");
        stage.sizeToScene();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                DataBase.getInstance().close();
                //System.exit(0);
            }
        });
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
