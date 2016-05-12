package evm;

import static evm.DeviceConfig.processConfig;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author airat
 */
enum TypeOperation {

    SEARCH_MARK,
    PDP,
    PROCESSING_FILE,
    ACTION_COMANDS
}

public class Prosessor extends Conditionable {

    private Process[] process;
    private int currentProcess = 0;
    private int amountCurrentProcessIteration = 0;
    private static final int KVANT_SIZE = 4;
    private long startTime;
    private long endTime = -1;
    private TypeOperation typeOperation = TypeOperation.ACTION_COMANDS;
    private BufferedImage image = new BufferedImage(401, 150, BufferedImage.TYPE_INT_RGB);
    private Graphics graphics = image.getGraphics();
    private Color activeColor = new Color(249, 201, 153);
    ArrayList<Integer> prerivanie = new ArrayList<Integer>();
    public Prosessor(Plata plata) {
        process = new Process[3];
        for (int i = 0; i < process.length; i++) {
            process[i] = new Process(i, plata, this);
        }
        process[0].setProcessColor(Color.cyan);
        process[1].setProcessColor(Color.magenta);
        process[2].setProcessColor(Color.gray);

    }

    public boolean action() {
        startTime = System.currentTimeMillis();
        for (;;) {
            // пропускает закончившиеся процессы
            int amountFinishedProcess = 0;
            while (process[currentProcess].isFinished()) {
                amountFinishedProcess++;
                if (amountFinishedProcess == process.length) {
                    setCondition(Condition.INACTIVE);
                    endTime = System.currentTimeMillis();
                    return false;
                }
                currentProcess = (currentProcess < process.length - 1) ? currentProcess + 1 : 0;
                amountCurrentProcessIteration = 0;
            }
            // пропускает ждучие процессы
            int amountWaitingProcess = 0;
            while (process[currentProcess].isWaiting() || process[currentProcess].isFinished() || process[currentProcess].isLocked()) {
                amountWaitingProcess++;
                if (amountWaitingProcess == process.length) {
                    setCondition(Condition.INACTIVE);
                    Thread.yield();
                    continue;
                }
                currentProcess = (currentProcess < process.length - 1) ? currentProcess + 1 : 0;
                amountCurrentProcessIteration = 0;
            }
            // выполняет один квант
            setCondition(Condition.ACTIVE);
            boolean success = process[currentProcess].action();
            
            amountCurrentProcessIteration++;
            if (amountCurrentProcessIteration == KVANT_SIZE || !success) {
                currentProcess = (currentProcess < process.length - 1) ? currentProcess + 1 : 0;
                amountCurrentProcessIteration = 0;
            }
        }
    }

    public Image paint() {//0  20
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        if (getCondition() != Condition.INACTIVE) {
            graphics.setColor(activeColor);
            graphics.fillRect(100, 20, 300, 110);
        }
        graphics.setColor(Color.BLACK);
        graphics.drawString("Процессор ", 230, 10);
        graphics.drawRect(100, 20, 300, 110);// Процессор
        for (int i=0;i<prerivanie.size();++i) {
            graphics.setColor(Color.black);
            graphics.fillOval(390 - i*10,  30, 8, 8);
            graphics.setColor(process[prerivanie.get(i)].getProcessColor());
            graphics.fillOval(391 -i*10,  31, 6, 6);
        }
        if(getCondition() == Condition.ACTIVE_WITH_FILE){//файл
            graphics.setColor(Color.RED);
            graphics.fillRect(380, 40, 20, 20);
        }
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < DeviceConfig.processConfig.length; ++i) {
            graphics.drawLine(310, 40 + i * 30, 330, 40 + i * 30);
        }
        graphics.drawLine(330, 40, 330, 100);//шины
        graphics.drawLine(330, 70, 380, 70); //
        graphics.drawString("ПДП", 3, 30);
        for (int i = 0; i < 3; i++) {
            graphics.setColor(Color.black);
            graphics.fillOval(9,  35 + 30 * i, 8, 8);
            graphics.setColor(process[i].isWaiting() ? Color.green : Color.red);
            graphics.fillOval(10,  36 + 30 * i, 6, 6);
        }
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < DeviceConfig.processConfig.length; ++i) {
            graphics.drawString("Задача " + (i + 1), 25, 45 + 30 * i);
            graphics.drawImage(process[i].paint(), 110, 30 + i * 30, null);
        }

        if(getCondition() != Condition.INACTIVE){
            graphics.drawString("Задача " + (currentProcess + 1) + ">", 25, 45 + 30 * currentProcess);
            graphics.setColor(new Color(0, 0, 220));
            graphics.drawRect(109, 29 + currentProcess * 30, 202, 22);
            graphics.setColor(process[currentProcess].getProcessColor());
            graphics.fillRect(380, 60, 20, 40);
        }
        graphics.setColor(Color.BLACK);
        graphics.drawRect(380, 40, 20, 20);
        graphics.drawRect(380, 60, 20, 40);
        return image;
    }

    public void setTypeOperation(TypeOperation typeOperation) {
        this.typeOperation = typeOperation;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        if(endTime == -1)
            return System.currentTimeMillis();
        return endTime;
    }
    
    Process getProcess(int id){
        return process[id];
    }
    public Color getColor(){
        for (Process pr :process) {
            if(pr.getTypeOperation() == TypeOperation.SEARCH_MARK){
                return Color.RED;
            }
            if(pr.getTypeOperation() == TypeOperation.PDP){
                return Color.GREEN;
            }
            if(pr.getTypeOperation() == TypeOperation.PROCESSING_FILE){
                return Color.BLUE;
            }
        }
        return null;
    }

    public char getType(){
        for (Process pr :process) {
            if(!(pr.getTypeOperation() == TypeOperation.ACTION_COMANDS)){
                return pr.getType();
            }
        }
        return 'n';
    }

    public TypeOperation getTypeOperation(){
        for (Process pr :process) {
            if(!(pr.getTypeOperation() == TypeOperation.ACTION_COMANDS)){
                return pr.getTypeOperation();
            }
        }
        return TypeOperation.ACTION_COMANDS;
    }
}
