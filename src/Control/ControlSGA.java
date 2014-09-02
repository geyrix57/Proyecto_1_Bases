/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Modelo.BaseDatos.DataBase;
import Modelo.SGA.AddSGAInfo;
import Modelo.SGA.SGAComponent;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.util.Duration;

/**
 *
 * @author Geykel
 */
public class ControlSGA implements Initializable{

    @FXML
    private BarChart<Number,String> sgaFreeSpaceChart;
    @FXML
    private NumberAxis xAxis = new NumberAxis();
    @FXML
    private CategoryAxis yAxis = new CategoryAxis();
    @FXML
    private LineChart<String,Number> sgaPoolChart;
    @FXML
    private CategoryAxis xAxis_Pool = new CategoryAxis();
    @FXML
    private NumberAxis yAxis_Pool = new NumberAxis();
    @FXML
    private Slider slider;
    
    private final static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final int MAX_DATA_POINTS = 10;
    
    private final ConcurrentLinkedQueue<SGAComponent> dataQ = new ConcurrentLinkedQueue();
    private ExecutorService executor;
    private AddSGAInfo addToQueue;
    
    private XYChart.Series usedSgaSizeSirie = new XYChart.Series();
    private XYChart.Series freeSGASerie = new XYChart.Series();
    
    private XYChart.Series Spseries = new XYChart.Series();
    private XYChart.Series Lpseries = new XYChart.Series();
    private XYChart.Series Jpseries = new XYChart.Series();
    
    Timeline tl;

    private void prepareTimeline() {
        tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), 
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    addDataToSeries();
            }
        }));
        tl.setCycleCount(Animation.INDEFINITE);
        /*new AnimationTimer() {
            @Override 
            public void handle(long now) {
                addDataToSeries();
            }
        }.start();*/
    }
    
    private void addDataToSeries() {
        if (dataQ.isEmpty()) return;
        SGAComponent sgac = dataQ.remove();
        if(sgac!=null){
           if(!freeSGASerie.getData().isEmpty()){
                freeSGASerie.getData().remove(0);
                usedSgaSizeSirie.getData().remove(0);
            }
            freeSGASerie.getData().add(new XYChart.Data(sgac.getSGAFreeSpace(),"Memoria"));
            usedSgaSizeSirie.getData().add(new XYChart.Data(sgac.getMaxSGASize()-sgac.getSGAFreeSpace(),"Memoria"));
            
            Spseries.getData().add(new AreaChart.Data(dateFormat.format(sgac.getHora()), sgac.getSharedPool()));
            Lpseries.getData().add(new AreaChart.Data(dateFormat.format(sgac.getHora()), sgac.getLargePool()));
            Jpseries.getData().add(new AreaChart.Data(dateFormat.format(sgac.getHora()), sgac.getJavaPool()));
            if (Spseries.getData().size() > MAX_DATA_POINTS) {
                Spseries.getData().remove(0, 1/*Spseries.getData().size() - MAX_DATA_POINTS*/);
                Lpseries.getData().remove(0, 1/*Lpseries.getData().size() - MAX_DATA_POINTS*/);
                Jpseries.getData().remove(0, 1/*Jpseries.getData().size() - MAX_DATA_POINTS*/);
            } 
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                System.out.println(new_val.longValue());
                AddSGAInfo.timeout = new_val.longValue();
                synchronized(executor){
                    tl.stop();
                    dataQ.clear();
                    executor.notify();
                    tl.play();
                }
            }
        });
        
        sgaFreeSpaceChart.setAnimated(false);
        freeSGASerie.setName("Libre");
        usedSgaSizeSirie.setName("En Uso");
        sgaFreeSpaceChart.getData().addAll(usedSgaSizeSirie,freeSGASerie);
        
        xAxis_Pool.setGapStartAndEnd(false);
        Spseries.setName("Shared Pool");
        Lpseries.setName("Large Pool");
        Jpseries.setName("Java Pool");
        sgaPoolChart.getData().addAll(Spseries,Lpseries,Jpseries);
        
        executor = Executors.newCachedThreadPool();
        prepareTimeline();
        
        addToQueue = new AddSGAInfo(tl,executor, dataQ);
        
        DataBase.getInstance().addObserver(addToQueue);
        //executor.execute(addToQueue);
    }
    
}
