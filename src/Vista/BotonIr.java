/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 *
 * @author Fran
 */
public class BotonIr extends Button {

    public BotonIr(String dir) {
        super("ir");
        direccion = dir;
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                abrirDirectorio();
            }
        });
    }

    public void abrirDirectorio() {
        Desktop d = Desktop.getDesktop();
        String s =direccion.substring(0, direccion.lastIndexOf('\\'));
        File file = new File(s);
        try {
            d.open(file);
        } catch (IOException ex) {
            Logger.getLogger(BotonIr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String direccion;
}
