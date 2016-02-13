package evm;

import static evm.DeviceConfig.processConfig;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;
 
/**
 *
 * @author airat
 */
public class Memory extends Conditionable{
    private BufferedImage image = new BufferedImage(101, 131, BufferedImage.TYPE_INT_RGB);
    private Graphics graphics = image.getGraphics();
    private Color activeColor = new Color(249, 201, 153);
    private Random r = new Random();
    private int x, y, widthFile;
    private Point[] points = new Point[DeviceConfig.processConfig.length];
    public Image paint() {//840 120
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        if (getCondition() != Condition.INACTIVE) {
            graphics.setColor(activeColor);
            graphics.fillRect(0, 30, 100, 100);
            
            graphics.setColor(Color.RED);
            graphics.fillRect(x, y + 30, widthFile, widthFile);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(x, y + 30, widthFile, widthFile);
        }
        graphics.setColor(Color.BLACK);
        graphics.drawString("Оперативная", 10, 10);
        graphics.drawString("память", 30, 25);
        graphics.drawRect(0, 30, 100, 100);
        return image;
    }

    public void setIdProcess(int id) {
        double fileSize= processConfig[id].dlWrite / processConfig[id].kolObr;
        double koef = fileSize / (DeviceConfig.memorySize * 1024);
        widthFile = (int) Math.sqrt(koef * 10_000);
        if (widthFile < 5) {
            widthFile = 5;
        }
        if(points[id] == null){
            points[id] = new Point(r.nextInt(101 - widthFile), r.nextInt(101 - widthFile));
        }
        x = points[id].x;
        y = points[id].y;
    }
    
}
