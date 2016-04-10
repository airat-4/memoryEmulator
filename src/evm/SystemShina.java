package evm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

/**
 *
 * @author airat
 */
public class SystemShina extends Conditionable{
    private BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
    private Graphics graphics = image.getGraphics();
    private Plata plata;

    public SystemShina(Plata plata) {
        this.plata = plata;
    }
    
    public Image paint() {// 400 60
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        int[] arrayX = {0, 20, 20, 100, 100, 115, 95, 75, 90, 90, 20, 20};
        int[] arrayY = {20, 0, 15, 15, 80, 80, 100, 80, 80, 25, 25, 40};
        Polygon poly = new Polygon(arrayX, arrayY, arrayY.length);
        graphics.setColor(plata.prosessor.getColor());
        if (getCondition() != Condition.INACTIVE) {
            graphics.fillPolygon(poly);
        }
        graphics.setColor(Color.BLACK);
        graphics.drawPolygon(poly);
        graphics.drawString("Системная", 120, 30);
        graphics.drawString("шина", 140, 50);
        return image;
    }
    
}
