/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.Tablespace;

import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Aaron
 */
public class Tablespace {
    SimpleStringProperty tablespace_name;
    SimpleStringProperty total_bytes;
    SimpleStringProperty online_status;
    SimpleStringProperty file_name;
    SimpleStringProperty used_bytes;
    SimpleStringProperty free_bytes;

    public Tablespace(String tablespace_name, String total_bytes, String online_status, String file_name) {
        this.tablespace_name = new SimpleStringProperty(tablespace_name);
        this.total_bytes = new SimpleStringProperty(total_bytes);
        this.online_status = new SimpleStringProperty(online_status);
        this.file_name = new SimpleStringProperty(file_name);
    }

    public Tablespace(String tablespace_name, String total_bytes, String online_status, String file_name, String used_bytes, String free_bytes) {
        this.tablespace_name = new SimpleStringProperty(tablespace_name);
        this.total_bytes = new SimpleStringProperty(total_bytes);
        this.online_status = new SimpleStringProperty(online_status);
        this.file_name = new SimpleStringProperty(file_name);
        this.used_bytes = new SimpleStringProperty(used_bytes);
        this.free_bytes = new SimpleStringProperty(free_bytes);
    }
    
    

    public String getTablespace_name() {
        return tablespace_name.get();
    }

    public void setTablespace_name(String tablespace_name) {
        this.tablespace_name.set(tablespace_name);
    }

    public String getTotal_bytes() {
        return this.total_bytes.get();
    }

    public void setTotal_bytes(String total_bytes) {
        this.total_bytes.set(total_bytes);
    }

    public String getOnline_status() {
        return online_status.get();
    }

    public void setOnline_status(String online_status) {
        this.online_status.set(online_status);
    }

    public String getFile_name() {
        return file_name.get();
    }

    public void setFile_name(String file_name) {
        this.file_name.set(file_name);
    }

    public String getUsed_bytes() {
        return used_bytes.get();
    }

    public void setUsed_bytes(String used_bytes) {
        this.used_bytes.set(used_bytes);
    }

    public String getFree_bytes() {
        return free_bytes.get();
    }

    public void setFree_bytes(String free_bytes) {
        this.free_bytes.get();
    }
    
  
    
    
    
    
    
    
}
