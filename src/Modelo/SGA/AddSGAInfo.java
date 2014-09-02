/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.SGA;

import Modelo.BaseDatos.DataBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Timeline;
/**
 *
 * @author Geykel
 */
public class AddSGAInfo implements Runnable, Observer{
    
    public AddSGAInfo(Timeline tl,ExecutorService exe, ConcurrentLinkedQueue<SGAComponent> data){
        dataQ = data;
        db = DataBase.getInstance();
        executor = exe;
        this.tl = tl;
    }
    
    public Double getSGAFreeSpace(){
        Double fs = (double) 0;
        if(db.isConnected()){
            try {
                ResultSet set = db.ExecuteQuery("SELECT * FROM gv$sga_dynamic_free_memory");
                while(set.next())
                    fs = set.getDouble("CURRENT_SIZE")/1048576;
                set.getStatement().close();
            } catch (SQLException ex) {
                Logger.getLogger(AddSGAInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fs;
    }
    
    public Double getMaxSgaSize(){
        Double fs = (double) 0;
        if(db.isConnected()){
            try {
                ResultSet set = db.ExecuteQuery("SELECT NAME,BYTES FROM V$SGAINFO WHERE NAME='Maximum SGA Size'");
                while(set.next())
                    fs = set.getDouble("BYTES")/1048576;
                set.getStatement().close();
            } catch (SQLException ex) {
                Logger.getLogger(AddSGAInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fs;
    }
    
    public SGAComponent getSGAInfo(){
        ResultSet res;
        String comp;
        SGAComponent sgac=null;
        StringBuilder sql = new StringBuilder("SELECT F.POOL, F.NAME, S.SGASIZE, F.BYTES ");
        sql.append("FROM (SELECT SUM(BYTES) SGASIZE, POOL FROM v$sgastat GROUP BY POOL) S, v$sgastat F ");
        sql.append("WHERE F.NAME = 'free memory' AND F.POOL = S.POOL");
        if(db.isConnected()){
            try {
                sgac = new SGAComponent();
                res = db.ExecuteQuery(sql.toString());
                sgac.setSGAFreeSpace(getSGAFreeSpace());
                sgac.setMaxSGASize(getMaxSgaSize());
                while(res.next()){
                    comp = res.getString("POOL");
                    if(comp.equals("shared pool")){
                        sgac.setSharedPool( (res.getDouble("SGASIZE")-res.getDouble("BYTES"))/1048576 );
                        sgac.setSpFree(res.getDouble("BYTES")/1048576);
                    }
                    else if(comp.equals("large pool")){
                        sgac.setLargePool((res.getDouble("SGASIZE")-res.getDouble("BYTES"))/1048576 );
                        sgac.setLpFree(res.getDouble("BYTES")/1048576);
                    }
                    else{
                        sgac.setJavaPool((res.getDouble("SGASIZE")-res.getDouble("BYTES"))/1048576 );
                        sgac.setJpFree(res.getDouble("BYTES")/1048576);
                    }
                }
                res.getStatement().close();
            } catch (SQLException ex) {
                Logger.getLogger(AddSGAInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sgac;
    }
    
    @Override
    public void run() {
        try {
            SGAComponent sagc = getSGAInfo();
            if(sagc != null) dataQ.add(sagc);
            synchronized(executor){
                executor.wait(timeout*1000);
            }
            if(DataBase.getInstance().isConnected())executor.execute(this);
            else executor.shutdownNow();
        } catch (InterruptedException ex) {
            Logger.getLogger(AddSGAInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private final ExecutorService executor;
    private DataBase db;
    private ConcurrentLinkedQueue<SGAComponent> dataQ;
    private Timeline tl;
    public static long timeout = 1;
    
    /*private void checkDatabase(){
        if(db.isConnected()){
            executor.execute(this);
            tl.play();
        }
        /*else{
            executor.shutdownNow();
            tl.stop();
        }*/
            
    //}

    @Override
    public void update(Observable o, Object arg) {
        if(db.isConnected()){
            executor.execute(this);
            tl.play();
        }
        else tl.stop();
        //checkDatabase();
    }
}
