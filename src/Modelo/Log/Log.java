/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.Log;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Fran
 */
public class Log {
    
    
    public Log(int g,int m,int s,String st){
        group=new SimpleIntegerProperty(g);
        members=new SimpleIntegerProperty(m);
        size=new SimpleIntegerProperty(s);
        
        status=st;
        
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
     
     //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////   Sets   /////////////////////////////////////////////////////////////////////////////////////////////////////
     
     public void setGroup(Integer n){
         group.set(n);
        
     }
     
     public void setMember(Integer n){
         members.set(n);
         members.notifyAll();
     }
     
     public void setSize(Integer n){
         size.set(n);
         size.notifyAll();
     }
    
    
    
    private SimpleIntegerProperty group; 
    private SimpleIntegerProperty members;
    private SimpleIntegerProperty size; 
    private String status;
}
