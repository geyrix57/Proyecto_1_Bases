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
public class Log {
    
    
    public Log(int g,int m,int s,String st,String a){
        group=new SimpleIntegerProperty(g);
        members=new SimpleIntegerProperty(m);
        size=new SimpleIntegerProperty(s);
        
        status=new SimpleStringProperty(st);
        archived=new SimpleStringProperty(a);
        
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////   Gets   /////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public Integer getGroup(){
        return group.getValue();
    }
    
     public Integer getMembers(){
        return members.getValue();
    }
     
     public Integer getSize(){
        return size.getValue();
    }
     
    public String getStatus(){
        return status.getValue();
    }
    
    public String getArchived(){
        return archived.getValue();
    }
     
     //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////   Sets   /////////////////////////////////////////////////////////////////////////////////////////////////////
     
     public void setGroup(Integer n){
         group.set(n);
        
     }
     
     public void setMember(Integer n){
         members.set(n);
         
     }
     
     public void setSize(Integer n){
         size.set(n);
         
     }
     
     public void setStatus(String n){
         status.set(n);
         
     }
     public void setArchived(String n){
         archived.set(n);
         
     }
     
    
    
    
    private SimpleIntegerProperty group; 
    private SimpleIntegerProperty members;
    private SimpleIntegerProperty size; 
    private SimpleStringProperty status;
    private SimpleStringProperty archived;
}
