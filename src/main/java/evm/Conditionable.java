/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author airat
 */
public abstract class Conditionable {
    private Condition condition = Condition.INACTIVE;
    private ArrayList<Long> actionTime= new ArrayList<Long>();
    private BufferedImage image = new BufferedImage(300, 10, BufferedImage.TYPE_INT_RGB);
    private Graphics graphics = image.getGraphics();
    private long startTime;
    private long endTime;
    private long allTime;
    
    public void setCondition(Condition condition) {
        this.condition = condition;
        if(actionTime.size()%2 == 0 && (condition == Condition.ACTIVE || condition == Condition.ACTIVE_WITH_FILE)){
            actionTime.add(System.currentTimeMillis());
        }
        if(actionTime.size()%2 == 1 && condition == Condition.INACTIVE){
            actionTime.add(System.currentTimeMillis());
        }
    }

    public Condition getCondition() {
        return condition;
    }
    
    public Image getTimeDiogram(long startTime, long endTime){
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.setColor(Color.BLACK);
        this.startTime = startTime;
        this.endTime = endTime;
        allTime = endTime - startTime;
        for (int i = 0; i < actionTime.size(); i+=2) {
            long secondTime = endTime;
            if(i + 1 != actionTime.size()){
                secondTime = actionTime.get(i + 1);
            }
            graphics.fillRect(getX(actionTime.get(i)), 0, getX(secondTime) - getX(actionTime.get(i)), 10);
        }
        return image;
    }
    
    private int getX(long time){
        return (int) (300 * (time - startTime)/allTime);
    }
    
    public double activeTime(long startTime, long endTime){
        int activeTime = 0;
        for (int i = 0; i < actionTime.size(); i+=2) {
            long secondTime = endTime;
            if(i + 1 != actionTime.size()){
                secondTime = actionTime.get(i + 1);
            }
            activeTime += (int) (secondTime - actionTime.get(i));
        }
        return  (int)(activeTime/100)/10.;
    }
    
    public double activeTimePersent(long startTime, long endTime){
        int activeTime = 0;
        for (int i = 0; i < actionTime.size(); i+=2) {
            long secondTime = endTime;
            if(i + 1 != actionTime.size()){
                secondTime = actionTime.get(i + 1);
            }
            activeTime += (int) (secondTime - actionTime.get(i));
        }
        allTime = endTime - startTime;
        return (int) (1000. * activeTime / allTime) /10.;
    }
 
}
