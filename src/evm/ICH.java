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
public class ICH extends Conditionable{
    private BufferedImage image = new BufferedImage(580, 211, BufferedImage.TYPE_INT_RGB);
    private Graphics graphics = image.getGraphics();
    private Plata plata;

    public ICH(Plata plata) {
        this.plata = plata;
    }
    public Image paint() {//210 260
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        int x = 265, y = 0, d = 20;
        int[] arrayX = {x + 20, 40 + x, 25 + x, x + 25, 40 + x, 20 + x, x, 15 + x, 15 + x, x};
        int[] arrayY = {y, y + 20, y + 20, y + d + 20, y + d + 20, 40 + d + y, y + d + 20, 20 + d + y, 20 + y, 20 + y};
        Polygon poly = new Polygon(arrayX, arrayY, arrayX.length);
        if(getCondition() != Condition.INACTIVE){
            graphics.setColor(plata.prosessor.getColor());
            graphics.fillPolygon(poly);
        }
        graphics.setColor(Color.BLACK);
        graphics.drawPolygon(poly);
        int[] arrayX0 = {230, 240, 130, 130};
        int[] arrayY0 = {90, 90, 170, 160};
        Polygon poly0 = new Polygon(arrayX0, arrayY0, arrayX0.length);
        int[] arrayX1 = {340, 350, 450, 450};
        int[] arrayY1 = {90, 90, 160, 170};
        Polygon poly1 = new Polygon(arrayX1, arrayY1, arrayX1.length);
        x = 0; y = 160; d = 50;
        int[] arrayVZU0X = {x, 20 + x, 20 + x, x + d + 20, 20 + x + d, 40 + d + x, 20 + d + x, 20 + d + x, 20 + x, 20 + x};
        int[] arrayVZU0Y = {y, y - 20, y - 5, y - 5, y - 20, y, 20 + y, 5 + y, 5 + y, 20 + y};
        Polygon polyVZU0 = new Polygon(arrayVZU0X, arrayVZU0Y, arrayVZU0X.length);
        x = 490; y = 160; d = 50;
        int[] arrayVZU1X = {x, 20 + x, 20 + x, x + d + 20, 20 + x + d, 40 + d + x, 20 + d + x, 20 + d + x, 20 + x, 20 + x};
        int[] arrayVZU1Y = {y, y - 20, y - 5, y - 5, y - 20, y, 20 + y, 5 + y, 5 + y, 20 + y};
        Polygon polyVZU1 = new Polygon(arrayVZU1X, arrayVZU1Y, 10);
        if(getCondition() != Condition.INACTIVE){
            if (plata.vzu[0].getCondition() != Condition.INACTIVE) {
                graphics.setColor(plata.prosessor.getColor());
                graphics.fillPolygon(poly0);
                graphics.fillPolygon(polyVZU0);
                if(getCondition() == Condition.ACTIVE_WITH_FILE){
                    graphics.fillRect(90, 140, 40, 50);
                    graphics.fillRect(220, 60, 70, 30);
                }
            }
        if (plata.vzu[1].getCondition() != Condition.INACTIVE) {
            graphics.setColor(plata.prosessor.getColor());
                graphics.fillPolygon(poly1);
                graphics.fillPolygon(polyVZU1);
                if(getCondition() == Condition.ACTIVE_WITH_FILE){
                    graphics.fillRect(450, 140, 40, 50);
                    graphics.fillRect(290, 60, 70, 30);
                }
        }
        
        }
        graphics.setColor(Color.BLACK);
        graphics.drawRect(90, 60, 400, 150);
        graphics.drawRect(220, 60, 70, 30);//команда
        graphics.drawRect(290, 60, 70, 30);//команда
        graphics.drawRect(90, 140, 40, 50);// порт 0
        graphics.drawRect(450, 140, 40, 50);// порт 1
        graphics.drawPolygon(poly0);
        graphics.drawPolygon(poly1);
        graphics.drawString("Южный мост ( ICH ) ", 100, 50);
        graphics.drawString("Порт 0", 92, 140);
        graphics.drawString("Порт 1", 452, 140);
        graphics.drawPolygon(polyVZU0);
        graphics.drawPolygon(polyVZU1);
        return image;
    }
}
