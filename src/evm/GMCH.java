package evm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author airat
 */
public class GMCH extends Conditionable{
    private BufferedImage image = new BufferedImage(401, 101, BufferedImage.TYPE_INT_RGB);
    private Graphics graphics = image.getGraphics();
    private Plata plata;
    public GMCH(Plata plata) {
        this.plata = plata;
    }
    
    public Image paint() {//300 160
        
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.setColor(plata.prosessor.getColor());
        if (getCondition() == Condition.ACTIVE_WITH_FILE) {
            graphics.fillRect(190, 20, 10, 40);
            graphics.fillRect(190, 50, 210, 10);
            graphics.fillRect(175, 0, 40, 20);
        }
        graphics.setColor(plata.prosessor.getColor());
        if (plata.ich.getCondition() == Condition.ACTIVE) {
            graphics.fillRect(190, 20, 10, 80);
        }
        if (plata.ich.getCondition() == Condition.ACTIVE_WITH_FILE) {
            graphics.fillRect(190, 50, 210, 10);
            graphics.fillRect(190, 50, 10, 50);
        }
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, 400, 100);
        graphics.drawRect(175, 0, 40, 20);//команда
        graphics.drawRect(190, 20, 10, 40);//---------------
        graphics.drawRect(190, 60, 10, 40);//канал
        graphics.drawRect(190, 50, 210, 10);//
        graphics.drawRect(0, 50, 190, 10);//--------------
        graphics.drawString("Северный мост", 10, 15);
        graphics.drawString("( GMCH )", 30, 30);
        return image;
    }
}
