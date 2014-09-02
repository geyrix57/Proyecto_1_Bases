/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.Log;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Fran
 */
public class LogIndividual {
    
    public LogIndividual(int g,String s,String dir){
       direccion=new SimpleStringProperty(dir);
       this.group=new SimpleIntegerProperty(g);
      status=new SimpleStringProperty(s);
        
    }

    public Integer getGroup() {
        return group.getValue();
    }

    public void setGroup(int group) {
        this.group.set(group);
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status.setValue(status);
    }

    public String getDireccion() {
        return direccion.getValue();
    }

    public void setDireccion(String direccion) {
        this.direccion.setValue(direccion);
    }
    
    
    
    
    private SimpleIntegerProperty group;
    private SimpleStringProperty status;
    private SimpleStringProperty direccion;
}
