package evm;

import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import static evm.DeviceConfig.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author airat
 */
public class Process {

    private final int id;
    private boolean finished;
    private boolean waiting;
    private int currentOperation;
    private int currentType;
    private static int SEARCH_MARK_TIME = 1000;
    private static int ANIMATION_TIME = 1000;
    private Plata plata;
    private Prosessor prosessor;
    private BufferedImage image = new BufferedImage(201, 21, BufferedImage.TYPE_INT_RGB);
    private Graphics graphics = image.getGraphics();
    private static final ExecutorService threadPool = Executors.newSingleThreadExecutor();
    private int pdpTime;
    private TypeOperation typeOperation = TypeOperation.ACTION_COMANDS;
    public Color getProcessColor() {
        return processColor;
    }

    public void setProcessColor(Color processColor) {
        this.processColor = processColor;
    }

    private Color processColor;

    public TypeOperation getTypeOperation() {
        return typeOperation;
    }
    public int getCurrentOperation(){
        return processConfig[id].prog[currentOperation];
    }
    public Process(int id, Plata plata, Prosessor prosessor) {
        this.id = id;
        this.plata = plata;
        this.prosessor = prosessor;
        if (processConfig[id] == null) {
            finished = true;
            return;
        }
        double minPerfomans = SystemShinaPerfomens;
        if (minPerfomans > ICHPerfomens) {
            minPerfomans = ICHPerfomens;
        }
        if (minPerfomans > memoryShinaPerfomens) {
            minPerfomans = memoryShinaPerfomens;
        }
        if (minPerfomans > vzuShinaPerfomens) {
            minPerfomans = vzuShinaPerfomens;
        }
        if (minPerfomans > GMSHPerfomens) {
            minPerfomans = GMSHPerfomens;
        }
        pdpTime = (int) ((processConfig[id].dlWrite * 1000) / minPerfomans);
    }

    public boolean action() {
        boolean success = true;
        switch (processConfig[id].prog[currentOperation]) {
            case -1:
                success = pdp();
                break;
            case -3:
                success = processingFile();
                break;
            default:
                actionComands();
        }
        if (success) {
            ++currentOperation;
            if (currentOperation == processConfig[id].prog.length) {
                finished = true;
            }
        }
        return success;
    }

    public Image paint() {//90 30
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        if (!finished) {
            int amountOperations = 0;
            int type = 0;
            int nextType;

            for (int i = currentOperation; i <  DeviceConfig.processConfig[id].prog.length; ++i){
                if (amountOperations == 5)
                    break;
                nextType = (int)Math.signum(DeviceConfig.processConfig[id].prog[i]);
                if(type != nextType && nextType == 1)
                    ++amountOperations;
                type = nextType;
            }
            for (int j = 0; j < amountOperations; ++j) {
                graphics.setColor(processColor);
                graphics.fillRect(160 - j * 40, 0, 40, 20);
            }
            graphics.setColor(Color.BLACK);
            for (int j = 0; j < 5; ++j) {
                graphics.drawRect(j * 40, 0, 40, 20);
            }
            for (int j = 0; j < amountOperations -1; ++j) {
                graphics.setColor(Color.black);
                graphics.fillOval(156 - j * 40, 6, 8, 8);
                graphics.setColor(Color.red);
                graphics.fillOval(157 - j * 40, 7, 6, 6);
            }
            if(DeviceConfig.processConfig[id].prog[currentOperation] < 0){
                graphics.setColor(Color.black);
                graphics.fillOval(155+40, 5, 10, 10);
                graphics.setColor(Color.red);
                graphics.fillOval(157+40, 7, 6, 6);
            }
        }else{
            graphics.setColor(Color.BLACK);
            for (int j = 0; j < 5; ++j) {
                graphics.drawRect(j * 40, 0, 40, 20);
            }
        }

        return image;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public boolean isLocked() {
        if (processConfig[id].prog[currentOperation] < 0) {
            return plata.gmch.getCondition() != Condition.INACTIVE;
        }
        return false;
    }



    private boolean pdp() {
        typeOperation = TypeOperation.SEARCH_MARK;
        if (plata.gmch.getCondition() == Condition.INACTIVE) {
            waiting = true;
            prosessor.setTypeOperation(TypeOperation.SEARCH_MARK);
            plata.systemShina.setCondition(Condition.ACTIVE);
            plata.gmch.setCondition(Condition.ACTIVE);
            plata.ich.setCondition(Condition.ACTIVE);
            plata.vzu[processConfig[id].numVzu].setCondition(Condition.ACTIVE);
            threadPool.submit(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(SEARCH_MARK_TIME);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            plata.systemShina.setCondition(Condition.INACTIVE);
                            currentOperation++;
                            //  ПДП
                            typeOperation = TypeOperation.PDP;

                            prosessor.setTypeOperation(TypeOperation.PDP);
                            plata.memoryShina.setCondition(Condition.ACTIVE);
                            plata.gmch.setCondition(Condition.ACTIVE);
                            plata.ich.setCondition(Condition.ACTIVE_WITH_FILE);
                            plata.vzu[processConfig[id].numVzu].setCondition(Condition.ACTIVE_WITH_FILE);
                            plata.memory.setCondition(Condition.ACTIVE_WITH_FILE);
                            plata.memory.setIdProcess(id);
                            plata.vzu[processConfig[id].numVzu].setIdProcess(id);
                            plata.etapPDP = 1;
                            if(Model.model != null)
                                Model.model.paintTread.interrupt();
                            for (int i = 0; i < 6; i++) {
                                try {
                                    Thread.sleep(ANIMATION_TIME/6);
                                    plata.etapPDP = 2+i;
                                    if(Model.model != null)
                                        Model.model.paintTread.interrupt();
                                } catch (InterruptedException ex) {}
                            }
                            try {
                                Thread.sleep(pdpTime);
                            } catch (InterruptedException ex) {}
                            plata.prerivanie = true;
                            plata.etapPDP = 0;
                            typeOperation = TypeOperation.ACTION_COMANDS;
                            prosessor.prerivanie.add(id);
                            try {
                                Thread.sleep(SEARCH_MARK_TIME/2);
                            } catch (InterruptedException ex) { }
                            plata.prerivanie = false;
                            plata.memoryShina.setCondition(Condition.INACTIVE);
                            plata.ich.setCondition(Condition.INACTIVE);
                            plata.vzu[processConfig[id].numVzu].setCondition(Condition.INACTIVE);
                            plata.memory.setCondition(Condition.INACTIVE);
                            plata.gmch.setCondition(Condition.INACTIVE);
                            waiting = false;
                            currentOperation++;
                            currentType++;

                        }
                    });

        }
        return false;

    }

    private boolean processingFile() {
        prosessor.prerivanie.remove((Object)id);
        typeOperation = TypeOperation.PROCESSING_FILE;
        if (plata.gmch.getCondition() == Condition.INACTIVE) {
            prosessor.setTypeOperation(TypeOperation.PROCESSING_FILE);
            prosessor.setCondition(Condition.ACTIVE_WITH_FILE);
            plata.systemShina.setCondition(Condition.ACTIVE);
            plata.gmch.setCondition(Condition.ACTIVE_WITH_FILE);
            plata.memoryShina.setCondition(Condition.ACTIVE);
            plata.memory.setCondition(Condition.ACTIVE_WITH_FILE);
            plata.memory.setIdProcess(id);
            double minPerfomans = SystemShinaPerfomens;
            if (minPerfomans > GMSHPerfomens) {
                minPerfomans = GMSHPerfomens;
            }
            if (minPerfomans > memoryShinaPerfomens) {
                minPerfomans = memoryShinaPerfomens;
            }
            try {
                Thread.sleep((int) ((processConfig[id].dlWrite * 1000) / (processConfig[id].kolObr * minPerfomans)));
            } catch (InterruptedException ex) {
                Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
            }
            plata.systemShina.setCondition(Condition.INACTIVE);
            plata.gmch.setCondition(Condition.INACTIVE);
            plata.memory.setCondition(Condition.INACTIVE);
            plata.memoryShina.setCondition(Condition.INACTIVE);
        } else {
            return false;
        }
        return true;
    }

    private void actionComands() {
        prosessor.prerivanie.remove((Object)id);
        typeOperation = TypeOperation.ACTION_COMANDS;
        prosessor.setTypeOperation(TypeOperation.ACTION_COMANDS);
        try {
            Thread.sleep((int) (1000 / perfomens));
        } catch (InterruptedException ex) {
        }
    }

    public char getType(){
        if(currentType >= DeviceConfig.typeOperation[id].length)
            return 'n';
        return DeviceConfig.typeOperation[id][currentType];
    }
    
}
