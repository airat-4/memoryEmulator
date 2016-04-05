/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
    private static final int FPS = 3;
    private Image back;
    private Image activeBack;
    private boolean active;

    public Model() {
        try {
            back = ImageIO.read(getClass().getResource("back.png"));
            activeBack = ImageIO.read(getClass().getResource("active_back.png"));
        } catch (IOException ex) {
        }

        paintTread = new Thread() {

            @Override
            public void run() {
                for (;;) {
                    try {
                        sleep(1000 / FPS);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    repaint();
                }
            }

        };
        paintTread.start();
        actionThread = new Thread() {

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
        if (active) {
            graphics.drawImage(activeBack, 0, 0, null);
        } else {
            graphics.drawImage(back, 0, 0, null);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setForeground(new java.awt.Color(249, 201, 153));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

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

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        active = evt.getX() < 25 && evt.getY() < 25;
        if (active) {
            getGraphics().drawImage(activeBack, 0, 0, null);
        } else {
            getGraphics().drawImage(back, 0, 0, null);
        }
    }//GEN-LAST:event_formMouseMoved

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if (active) {
            ConfigurationPanel.configurationPanel.setVisible(true);
            ConfigurationPanel.frame.setVisible(false);
            paintTread.stop();
            actionThread.stop();
        }
    }//GEN-LAST:event_formMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
