package evm;

import static evm.DeviceConfig.processConfig;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

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
    private BufferedImage image = new BufferedImage(381, 150, BufferedImage.TYPE_INT_RGB);
    private Graphics graphics = image.getGraphics();
    private Color activeColor = new Color(249, 201, 153);

    public Prosessor(Plata plata) {
        process = new Process[3];
        for (int i = 0; i < process.length; i++) {
            process[i] = new Process(i, plata, this);
        }
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

    public Image paint() {//20  20
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        if (getCondition() != Condition.INACTIVE) {
            graphics.setColor(activeColor);
            graphics.fillRect(80, 20, 300, 110);
        }
        graphics.setColor(Color.BLACK);
        graphics.drawString("Процессор ", 210, 10);
        graphics.drawRect(80, 20, 300, 110);// Процессор
        if(getCondition() == Condition.ACTIVE_WITH_FILE){//файл
            graphics.setColor(Color.RED);
            graphics.fillRect(360, 40, 20, 20);
        }
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < DeviceConfig.processConfig.length; ++i) {
            graphics.drawLine(290, 40 + i * 30, 310, 40 + i * 30);
        }
        graphics.drawLine(310, 40, 310, 100);//шины                  
        graphics.drawLine(310, 70, 360, 70); //
        for (int i = 0; i < DeviceConfig.processConfig.length; ++i) {
            graphics.drawString("Задача " + (i + 1), 0, 45 + 30 * i);
            graphics.drawImage(process[i].paint(), 90, 30 + i * 30, null);
        }

        if(getCondition() != Condition.INACTIVE){
            graphics.drawString("Задача " + (currentProcess + 1) + ">", 0, 45 + 30 * currentProcess);
            graphics.setColor(new Color(100, 0, 0));
            graphics.drawRect(89, 29 + currentProcess * 30, 202, 22);
            int type = process[currentProcess].getCurrentOperation();

            if(type == -3){
                graphics.setColor(Color.BLUE);
                graphics.fillRect(360, 60, 20, 40);
            }
            if(type > 0){
                graphics.setColor(Color.BLACK);
                graphics.drawString("" + type, 362, 85);
            }
        }
        graphics.setColor(Color.BLACK);
        graphics.drawRect(360, 40, 20, 20);
        graphics.drawRect(360, 60, 20, 40);
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
