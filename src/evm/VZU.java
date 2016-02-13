/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evm;

import static evm.DeviceConfig.processConfig;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author airat
 */
public class VZU extends Conditionable{
    private BufferedImage image = new BufferedImage(151, 171, BufferedImage.TYPE_INT_RGB);
    private Graphics graphics = image.getGraphics();
    private Color activeColor = new Color(249, 201, 153);
    private int id;
    private Random r = new Random();
    private int x, y, widthFile;
    private Point[] points = new Point[DeviceConfig.processConfig.length];
    
    public VZU(int id) {
        this.id = id;
    }
    
    public Image paint() {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        
        if(getCondition() != Condition.INACTIVE){
            graphics.setColor(activeColor);
            graphics.fillRect(0, 20, 150, 150);
        }
        if(getCondition() == Condition.ACTIVE_WITH_FILE){
            graphics.setColor(Color.RED);
            graphics.fillRect(x, y + 20, widthFile, widthFile);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(x, y + 20, widthFile, widthFile);
        }
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 20, 150, 150);
        graphics.drawString("ВЗУ " + id, 50, 15);
        
        
        
        return image;
    }
    public void setIdProcess(int id) {
        double koef = processConfig[id].dlFile / (DeviceConfig.vzuSize * 1024);
        widthFile = (int) Math.sqrt(koef * 22_500);
        if (widthFile < 5) {
            widthFile = 5;
        }
        if(points[id] == null){
            points[id] = new Point(r.nextInt(151 - widthFile), r.nextInt(151 - widthFile));
        }
        x = points[id].x;
        y = points[id].y;
    }
    
    
}
