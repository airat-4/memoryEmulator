/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Айрат
 */

//------------------------------------------------------------------------------
public class Model extends javax.swing.JPanel {
    private Plata plata = new Plata();
    private Thread paintTread;
    private Thread actionThread;
    private static int FPS = 3;
            
    public Model() {
        
        paintTread = new Thread(){

            @Override
            public void run() {
                for(;;){
                    try {
                        sleep(1000/FPS);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    repaint();
                }
            }
            
        };
        paintTread.start();
        actionThread = new Thread(){

            @Override
            public void run() {
                plata.start();
                paintTread.stop();
                repaint();
            }
            
        };
        actionThread.start();
        
        addKeyListener(new esc());
        setFocusable(true);
        initComponents();
    }
    
    class esc extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getExtendedKeyCode() == 27) {
                ConfigurationPanel.configurationPanel.setVisible(true);
                ConfigurationPanel.frame.setVisible(false);
                paintTread.stop();
                actionThread.stop();
            }
        }
    }
    
   public void paint(Graphics graphics) {
        graphics.drawImage(plata.paint(), 0, 0, null);
         
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setForeground(new java.awt.Color(249, 201, 153));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
