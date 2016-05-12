/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Plata{
    Prosessor prosessor;
    SystemShina systemShina;
    GMCH gmch;
    ICH ich;
    MemoryShina memoryShina;
    Memory memory;
    VZU[] vzu;
    boolean prerivanie;
    Color prerivanieColor = Color.orange;
    private BufferedImage image = new BufferedImage(1300, 550, BufferedImage.TYPE_INT_RGB);
    private Graphics graphics = image.getGraphics();
    private Color activeColor = new Color(249, 201, 153);
    int etapPDP;
    {
        prosessor = new Prosessor(this);
        systemShina = new SystemShina(this);
        gmch = new GMCH(this);
        ich = new ICH(this);
        memoryShina = new MemoryShina(this);
        memory = new Memory();
        vzu =  new VZU[2];
        vzu[0] = new VZU(0);
        vzu[1] = new VZU(1);
    }
    
    public void start(){
        prosessor.action();
    }

    public Image paint() {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.drawImage(prosessor.paint(), 0, 20, null);
        graphics.drawImage(systemShina.paint(), 401, 60, null);
        graphics.drawImage(gmch.paint(), 300, 160, null);
        graphics.drawImage(memoryShina.paint(), 700, 195, null);
        graphics.drawImage(memory.paint(), 840, 120, null);
        graphics.drawImage(ich.paint(), 210, 261, null);
        graphics.drawImage(vzu[0].paint(), 60, 320, null);
        graphics.drawImage(vzu[1].paint(), 790, 320, null);
        graphics.setColor(activeColor);
        graphics.fillRect(950, 0, 350, 550);
        graphics.setColor(Color.RED);
        graphics.fillRect(600, 20, 20, 10);
        graphics.setColor(Color.GREEN);
        graphics.fillRect(600, 40, 20, 10);
        graphics.setColor(Color.BLUE);
        graphics.fillRect(600, 60, 20, 10);
        graphics.setColor(prerivanieColor);
        graphics.fillRect(600, 80, 20, 10);
        graphics.setColor(Color.BLACK);
        graphics.drawString("Поиск начальной метки файла", 630, 30);
        graphics.drawString("Прямой доступ к памяти", 630, 50);
        graphics.drawString("Оброботка файла процессором", 630, 70);
        graphics.drawString("Прерывание 'окончание ПДП'", 630, 90);
        graphics.drawString("Нажмите Esc для возврата к настройкам", 30, 15);
        int x = 160, y = 215, d = 100;
        int[] arrayX = {x, 20 + x, 20 + x, x + d + 20, 20 + x + d, 40 + d + x, 20 + d + x, 20 + d + x, 20 + x, 20 + x};
        int[] arrayY = {y, y - 20, y - 5, y - 5, y - 20, y, 20 + y, 5 + y, 5 + y, 20 + y};
        Polygon poly = new Polygon(arrayX, arrayY, 10);
        graphics.drawPolygon(poly);
        graphics.drawString("Шина графики", 181, 205);
        graphics.drawRect(60, 195, 100, 40);
        graphics.drawString("Видеокарта", 70, 210);
        graphics.drawRect(60, 250, 100, 40);
        graphics.drawString("Монитор", 70, 265);
        graphics.drawLine(110, 235, 110, 250);
        
        graphics.drawString("Общее время : " + (((prosessor.getEndTime() - prosessor.getStartTime())/100)/10.) + " сек." , 975, 30);
        graphics.drawString("Активность устройств :", 975, 50);
        
        graphics.drawString("Процессор", 975, 70);
        graphics.drawString("" + prosessor.activeTimePersent(prosessor.getStartTime(), prosessor.getEndTime()) + " %", 1125, 70);
        graphics.drawString("" + prosessor.activeTime(prosessor.getStartTime(), prosessor.getEndTime()) + " сек.", 1200, 70);
        graphics.drawImage(prosessor.getTimeDiogram(prosessor.getStartTime(), prosessor.getEndTime()), 975, 80, null);

        graphics.drawString("ВЗУ 0", 975, 120);
        graphics.drawString("" + vzu[0].activeTimePersent(prosessor.getStartTime(), prosessor.getEndTime()) + " %", 1125, 120);
        graphics.drawString("" + vzu[0].activeTime(prosessor.getStartTime(), prosessor.getEndTime()) + " сек.", 1200, 120);
        graphics.drawImage(vzu[0].getTimeDiogram(prosessor.getStartTime(), prosessor.getEndTime()), 975, 130, null);
        
        graphics.drawString("ВЗУ 1", 975, 160);
        graphics.drawString("" + vzu[1].activeTimePersent(prosessor.getStartTime(), prosessor.getEndTime()) + " %", 1125, 160);
        graphics.drawString("" + vzu[1].activeTime(prosessor.getStartTime(), prosessor.getEndTime()) + " сек.", 1200, 160);
        graphics.drawImage(vzu[1].getTimeDiogram(prosessor.getStartTime(), prosessor.getEndTime()), 975, 170, null);
        
        graphics.drawString("Северный мост", 975, 210);
        graphics.drawString("" + gmch.activeTimePersent(prosessor.getStartTime(), prosessor.getEndTime()) + " %", 1125, 210);
        graphics.drawString("" + gmch.activeTime(prosessor.getStartTime(), prosessor.getEndTime()) + " сек.", 1200, 210);
        graphics.drawImage(gmch.getTimeDiogram(prosessor.getStartTime(), prosessor.getEndTime()), 975, 220, null);
        
        graphics.drawString("Южный мост", 975, 260);
        graphics.drawString("" + ich.activeTimePersent(prosessor.getStartTime(), prosessor.getEndTime()) + " %", 1125, 260);
        graphics.drawString("" + ich.activeTime(prosessor.getStartTime(), prosessor.getEndTime()) + " сек.", 1200, 260);
        graphics.drawImage(ich.getTimeDiogram(prosessor.getStartTime(), prosessor.getEndTime()), 975, 270, null);
        
        graphics.drawString("Оперативная память", 975, 310);
        graphics.drawString("" + memory.activeTimePersent(prosessor.getStartTime(), prosessor.getEndTime()) + " %", 1125, 310);
        graphics.drawString("" + memory.activeTime(prosessor.getStartTime(), prosessor.getEndTime()) + " сек.", 1200, 310);
        graphics.drawImage(memory.getTimeDiogram(prosessor.getStartTime(), prosessor.getEndTime()), 975, 320, null);
        
        return image;
    }
    
}
