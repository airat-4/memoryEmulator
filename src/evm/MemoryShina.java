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
public class MemoryShina extends Conditionable{
    private BufferedImage image = new BufferedImage(140, 40, BufferedImage.TYPE_INT_RGB);
    private Graphics graphics = image.getGraphics();
    private Color activeColor = new Color(249, 201, 153);
    
    public Image paint() {//700 195
        graphics.setColor(Color.WHITE);
        graphics.fillRect(1, 0, image.getWidth() - 1, image.getHeight());
        graphics.setColor(activeColor);
        int d = 100;
        int[] arrayX = {0, 20, 20 , d + 20, 20 + d, 40 + d, 20 + d, 20 + d, 20, 20};
        int[] arrayY = {20, 40, 25, 25, 40, 20, 0, 15, 15 , 0};
        Polygon poly = new Polygon(arrayX, arrayY, arrayX.length);
        if (getCondition() == Condition.ACTIVE) {
            graphics.fillPolygon(poly);
        }
        graphics.setColor(Color.BLACK);
        graphics.drawPolygon(poly);
        graphics.drawString("Шина памяти", 25, 10);
        return image;
    }
}
