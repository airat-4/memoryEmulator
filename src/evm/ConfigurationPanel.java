package evm;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author Айрат
 */
class DeviceConfig {

    static double perfomens;
    static double memorySize;
    static double vzuSize;
    static double SystemShinaPerfomens;
    static double GMSHPerfomens;
    static double ICHPerfomens;
    static double memoryShinaPerfomens;
    static double vzuShinaPerfomens;
    static int amountComand;
    static ProcessConfig[] processConfig;
    
}

class ProcessConfig {

    int kolCom;
    int kolObr;
    double dlFile;
    double dlWrite;
    int numVzu;
    int prog[];

}

public class ConfigurationPanel extends javax.swing.JFrame {
    private String workDirectory;
    static  ConfigurationPanel configurationPanel;
    
    public ConfigurationPanel() {
        configurationPanel = this;
        String classPath = System.getProperty("java.class.path");
        workDirectory = classPath.substring(0, classPath.lastIndexOf(File.separator));
        initComponents();
        jFileChooser1.setCurrentDirectory(new File(workDirectory));
    }

    private boolean ini() {
        try {
            label1.setForeground(Color.RED);
            DeviceConfig.processConfig = new ProcessConfig[3];
            Random r = new Random();
            DeviceConfig.perfomens = Double.parseDouble(jTextField1.getText());
            if (DeviceConfig.perfomens <= 0) {
                label1.setText("Оппаньки!!! :-( Что то не так с процессором");
                return false;
            }
            DeviceConfig.memorySize = Double.parseDouble(jTextField2.getText());
            DeviceConfig.vzuSize = Double.parseDouble(jTextField3.getText());
            DeviceConfig.SystemShinaPerfomens = Double.parseDouble(jTextField4.getText());
            if (DeviceConfig.SystemShinaPerfomens <= 0) {
                label1.setText("Кажется системная шина несправна :'(");
                return false;
            }
            DeviceConfig.GMSHPerfomens = Double.parseDouble(jTextField5.getText());
            DeviceConfig.ICHPerfomens = Double.parseDouble(jTextField6.getText());
            if (DeviceConfig.GMSHPerfomens <= 0 || DeviceConfig.ICHPerfomens <= 0) {
                label1.setText("Материнская плата накрылась придется менять : ' [");
                return false;
            }
            DeviceConfig.memoryShinaPerfomens = Double.parseDouble(jTextField7.getText());
            if (DeviceConfig.memoryShinaPerfomens <= 0) {
                label1.setText("Шина памяти есть, но его как бы нет :D");
                return false;
            }
            DeviceConfig.vzuShinaPerfomens = Double.parseDouble(jTextField8.getText());
            if (DeviceConfig.vzuShinaPerfomens <= 0) {
                label1.setText("Поставь нормальную шину ВЗУ");
                return false;
            }
            DeviceConfig.amountComand = Integer.parseInt(jTextField9.getText());
            if (DeviceConfig.amountComand <= 0) {
                label1.setText("Ты точно уверен что сучествуют программы без команд???");
                return false;
            }
            //-----------------------------------------------------------
            if (jCheckBox1.isSelected()) {

                DeviceConfig.processConfig[0] = new ProcessConfig();
                DeviceConfig.processConfig[0].kolCom = Integer.parseInt(jTextField9.getText());
                DeviceConfig.processConfig[0].kolObr = Integer.parseInt(jTextField10.getText());
                DeviceConfig.processConfig[0].dlFile = Double.parseDouble(jTextField11.getText());
                DeviceConfig.processConfig[0].dlWrite = Double.parseDouble(jTextField12.getText());
                if (DeviceConfig.processConfig[0].dlFile > DeviceConfig.vzuSize * 1024) {
                    label1.setText("Ставь бльше ВЗУ она бесплатная.(нехватает памяти)");
                    return false;
                }
                if (DeviceConfig.processConfig[0].kolObr != 0) {
                    if (DeviceConfig.memorySize * 1024 < DeviceConfig.processConfig[0].dlWrite / DeviceConfig.processConfig[0].kolObr && DeviceConfig.processConfig[0].kolObr != 0) {
                        label1.setText("Не жалей оперативки она халявная.(нехватает памяти)");
                        return false;
                    }
                }
                if (DeviceConfig.processConfig[0].kolCom < DeviceConfig.processConfig[0].kolObr) {
                    label1.setText("По техническим причинам кол-во обращений не могут превышать кол-во команд :-(");
                    return false;
                }
                DeviceConfig.processConfig[0].numVzu = Integer.parseInt(jTextField19.getText());
                if (DeviceConfig.processConfig[0].numVzu < 0 || DeviceConfig.processConfig[0].numVzu > 1) {
                    label1.setText("Номер ВЗУ может быть только 0 или 1.:( больше нету :(");
                    return false;
                }
                DeviceConfig.processConfig[0].prog = new int[DeviceConfig.processConfig[0].kolCom + DeviceConfig.processConfig[0].kolObr * 3];//формирование программы
                for (int i = 0; i < DeviceConfig.processConfig[0].kolObr; ++i) {
                    int a = r.nextInt(DeviceConfig.processConfig[0].kolCom / DeviceConfig.processConfig[0].kolObr) + i * (DeviceConfig.processConfig[0].prog.length / DeviceConfig.processConfig[0].kolObr);
                    DeviceConfig.processConfig[0].prog[a] = -1;
                    DeviceConfig.processConfig[0].prog[a + 1] = -2;
                    DeviceConfig.processConfig[0].prog[a + 2] = -3;
                }
                int k = 1;
                for (int i = 0; i < DeviceConfig.processConfig[0].prog.length; i++) {
                    if (DeviceConfig.processConfig[0].prog[i] == 0) {
                        DeviceConfig.processConfig[0].prog[i] = k;
                        ++k;
                    }

                }
                if (DeviceConfig.processConfig[0].dlFile < DeviceConfig.processConfig[0].dlWrite) {
                    label1.setText("Ты правда думаешь что сможешь прочесть из файла больше чем сам файл?");
                    return false;
                }

            }

            if (jCheckBox2.isSelected()) {
                DeviceConfig.processConfig[1] = new ProcessConfig();
                DeviceConfig.processConfig[1].kolCom = Integer.parseInt(jTextField9.getText());
                DeviceConfig.processConfig[1].kolObr = Integer.parseInt(jTextField13.getText());
                DeviceConfig.processConfig[1].dlFile = Double.parseDouble(jTextField14.getText());
                DeviceConfig.processConfig[1].dlWrite = Double.parseDouble(jTextField15.getText());
                DeviceConfig.processConfig[1].numVzu = Integer.parseInt(jTextField20.getText());
                if (DeviceConfig.processConfig[1].dlFile > DeviceConfig.vzuSize * 1024) {
                    label1.setText("Ставь бльше ВЗУ она бесплатная.(нехватает памяти)");
                    return false;
                }
                if (DeviceConfig.memorySize * 1024 < DeviceConfig.processConfig[1].dlWrite / DeviceConfig.processConfig[1].kolObr && DeviceConfig.processConfig[1].kolObr != 0) {
                    label1.setText("Не жалей оперативки она халявная.(нехватает памяти)");
                    return false;
                }
                if (DeviceConfig.processConfig[1].kolCom < DeviceConfig.processConfig[1].kolObr) {
                    label1.setText("По техническим причинам кол-во обращений не могут превышать кол-во команд :(");
                    return false;
                }
                if (DeviceConfig.processConfig[1].numVzu < 0 || DeviceConfig.processConfig[1].numVzu > 1) {
                    label1.setText("Номер ВЗУ может быть только 0 или 1.:( больше нету :(");
                    return false;
                }
                DeviceConfig.processConfig[1].prog = new int[DeviceConfig.processConfig[1].kolCom + DeviceConfig.processConfig[1].kolObr * 3];//формирование программы
                for (int i = 0; i < DeviceConfig.processConfig[1].kolObr; ++i) {
                    int a = r.nextInt(DeviceConfig.processConfig[1].kolCom / DeviceConfig.processConfig[1].kolObr) + i * (DeviceConfig.processConfig[1].prog.length / DeviceConfig.processConfig[1].kolObr);
                    DeviceConfig.processConfig[1].prog[a] = -1;
                    DeviceConfig.processConfig[1].prog[a + 1] = -2;
                    DeviceConfig.processConfig[1].prog[a + 2] = -3;
                }
                int k = 1;
                for (int i = 0; i < DeviceConfig.processConfig[1].prog.length; i++) {
                    if (DeviceConfig.processConfig[1].prog[i] == 0) {
                        DeviceConfig.processConfig[1].prog[i] = k;
                        ++k;
                    }

                }
                if (DeviceConfig.processConfig[1].dlFile < DeviceConfig.processConfig[1].dlWrite) {
                    label1.setText("Ты правда думаешь что сможешь прочесть из файла больше чем сам файл?");
                    return false;
                }
            }

            if (jCheckBox3.isSelected()) {
                DeviceConfig.processConfig[2] = new ProcessConfig();
                DeviceConfig.processConfig[2].kolCom = Integer.parseInt(jTextField9.getText());
                DeviceConfig.processConfig[2].kolObr = Integer.parseInt(jTextField16.getText());
                DeviceConfig.processConfig[2].dlFile = Double.parseDouble(jTextField17.getText());
                DeviceConfig.processConfig[2].dlWrite = Double.parseDouble(jTextField18.getText());
                DeviceConfig.processConfig[2].numVzu = Integer.parseInt(jTextField21.getText());
                if (DeviceConfig.processConfig[2].dlFile > DeviceConfig.vzuSize * 1024) {
                    label1.setText("Ставь бльше ВЗУ она бесплатная.(нехватает памяти)");
                    return false;
                }
                if (DeviceConfig.memorySize * 1024 < DeviceConfig.processConfig[2].dlWrite / DeviceConfig.processConfig[2].kolObr && DeviceConfig.processConfig[2].kolObr != 0) {
                    label1.setText("Не жалей оперативки она халявная.(нехватает памяти)");
                    return false;
                }
                if (DeviceConfig.processConfig[2].kolCom < DeviceConfig.processConfig[2].kolObr) {
                    label1.setText("По техническим причинам кол-во обращений не могут превышать кол-во команд :(");
                    return false;
                }
                if (DeviceConfig.processConfig[2].numVzu < 0 || DeviceConfig.processConfig[2].numVzu > 1) {
                    label1.setText("Номер ВЗУ может быть только 0 или 1.:( больше нету :(");
                    return false;
                }
                DeviceConfig.processConfig[2].prog = new int[DeviceConfig.processConfig[2].kolCom + DeviceConfig.processConfig[2].kolObr * 3];//формирование программы
                for (int i = 0; i < DeviceConfig.processConfig[2].kolObr; ++i) {
                    int a = r.nextInt(DeviceConfig.processConfig[2].kolCom / DeviceConfig.processConfig[2].kolObr) + i * (DeviceConfig.processConfig[2].prog.length / DeviceConfig.processConfig[2].kolObr);
                    DeviceConfig.processConfig[2].prog[a] = -1;
                    DeviceConfig.processConfig[2].prog[a + 1] = -2;
                    DeviceConfig.processConfig[2].prog[a + 2] = -3;
                }
                int k = 1;
                for (int i = 0; i < DeviceConfig.processConfig[2].prog.length; i++) {
                    if (DeviceConfig.processConfig[2].prog[i] == 0) {
                        DeviceConfig.processConfig[2].prog[i] = k;
                        ++k;
                    }
                    //----------------------------

                }
                if (DeviceConfig.processConfig[2].dlFile < DeviceConfig.processConfig[2].dlWrite) {
                    label1.setText("Ты правда думаешь что сможешь прочесть из файла больше чем сам файл?");
                    return false;
                }
            }
            if (!jCheckBox1.isSelected() && !jCheckBox2.isSelected() && !jCheckBox3.isSelected()) {
                label1.setText("Довай запустим какую нибудь задачу так интереснее :D");
                return false;
            }
            return true;
        } catch (Exception e) {
            label1.setText("Проверте правильность введения данных");
            return false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jToggleButton1 = new javax.swing.JToggleButton();
        jFrame1 = new javax.swing.JFrame();
        jLabel15 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jFrame2 = new javax.swing.JFrame();
        jFileChooser1 = new javax.swing.JFileChooser();
        jLabel13 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTextField9 = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        label1 = new java.awt.Label();

        jToggleButton1.setText("jToggleButton1");

        jFrame1.setMinimumSize(new java.awt.Dimension(400, 150));

        jLabel15.setText("Ввидите имя файла для сохранения");

        jButton6.setText("Сохранить");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame1Layout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jTextField22)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(85, 85, 85))
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jFrame2.setMinimumSize(new java.awt.Dimension(500, 300));

        jFileChooser1.setCurrentDirectory(new java.io.File("/home/airat"));
        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });
        jFrame2.getContentPane().add(jFileChooser1, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Понель управления");

        jPanel1.setBackground(new java.awt.Color(252, 216, 179));

        jLabel1.setText("Ггц   Частота процессора");

        jLabel2.setText("Гб   Ёмкость ОП");

        jLabel3.setText("Гб  Ёмкость ВЗУ");

        jLabel4.setText("Мб/с  Пропускная способность системной шины");

        jLabel5.setText("Мб/с  Пропускная способность северного моста");

        jLabel6.setText("Мб/с  Пропускная способность южного моста");

        jLabel7.setText("Мб/с  Пропускная способность шины памяти");

        jLabel8.setText("Мб/с  Пропускная способность шины ВЗУ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(252, 216, 179));

        jCheckBox1.setText("Задача 1");

        jCheckBox2.setText("Задача 2");

        jCheckBox3.setText("Задача 3");

        jLabel9.setText("Кол-во процессорных команд (в миллиардах)");

        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });

        jLabel10.setText("Количество обращений к файлу");

        jLabel11.setText("Мб Длина файла");

        jLabel12.setText("Мб Длина записи");

        jLabel14.setText("Номер ВЗУ (0 или 1)");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField20))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jTextField11, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField14)
                                    .addComponent(jTextField15)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField13))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCheckBox3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jTextField16)
                            .addComponent(jTextField17)
                            .addComponent(jTextField18)
                            .addComponent(jTextField21)))
                    .addComponent(jTextField9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("По умолчанию");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Сохранить как умолчание");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Сохранить настройки");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Загрузить настройки");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Пуск");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel16.setForeground(new java.awt.Color(153, 153, 153));
        jLabel16.setText("Айрат Гареев 2016 г.");

        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(207, 207, 207)
                        .addComponent(jLabel13))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
static JFrame frame;

    void start() {
        JFrame frame = new JFrame("Модель ЭВМ и ВЗУ");
        this.frame = frame;
        frame.setSize(1300, 550);
        frame.add(new Model());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        this.setVisible(false);
    }
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (ini()) {
            label1.setText("");
            start();

        }


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jFrame1.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (ini()) {
            try {
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(workDirectory + File.separator + jTextField22.getText() + ".cfg"));
                save(dos);
                dos.close();
                jFrame1.setVisible(false);
                label1.setText("Сохранено");
                label1.setForeground(Color.GREEN);
            } catch (Exception ex) {
                label1.setForeground(Color.RED);
                label1.setText("Неудалось сохранить");
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (ini()) {
            try {
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(workDirectory + File.separator + "0cfgUm0.cfg"));
                save(dos);
                dos.close();
                label1.setForeground(Color.GREEN);
                label1.setText("Сохранено");
            } catch (Exception ex) {
                label1.setForeground(Color.RED);
                label1.setText("Неудалось сохранить");
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jFrame2.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(jFileChooser1.getSelectedFile()));
            load(dis);
            dis.close();
            jFrame2.setVisible(false);
            label1.setForeground(Color.GREEN);
            label1.setText("Настройки загружены");
        } catch (Exception ex) {
            label1.setForeground(Color.RED);
            label1.setText("Неизвестная ошибка");
        }
    }//GEN-LAST:event_jFileChooser1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(workDirectory + File.separator + "0cfgUm0.cfg"));
            load(dis);
            dis.close();
            jFrame2.setVisible(false);
            label1.setForeground(Color.GREEN);
            label1.setText("Настройки по умолчанию загружены");
        } catch (Exception ex) {
            label1.setForeground(Color.RED);
            label1.setText("Настройки по умолчанию загрузить не удалось");
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConfigurationPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigurationPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigurationPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigurationPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfigurationPanel().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JToggleButton jToggleButton1;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables

    private void load(DataInputStream dis) throws IOException {
        jTextField1.setText(Double.toString(dis.readDouble()));
        jTextField2.setText(Double.toString(dis.readDouble()));
        jTextField3.setText(Double.toString(dis.readDouble()));
        jTextField4.setText(Double.toString(dis.readDouble()));
        jTextField5.setText(Double.toString(dis.readDouble()));
        jTextField6.setText(Double.toString(dis.readDouble()));
        jTextField7.setText(Double.toString(dis.readDouble()));
        jTextField8.setText(Double.toString(dis.readDouble()));
        jTextField9.setText("");
        if (dis.readBoolean()) {
            jTextField9.setText(Integer.toString(dis.readInt()));
            jTextField10.setText(Integer.toString(dis.readInt()));
            jTextField11.setText(Double.toString(dis.readDouble()));
            jTextField12.setText(Double.toString(dis.readDouble()));
            jTextField19.setText(Integer.toString(dis.readInt()));
            jCheckBox1.setSelected(true);
        } else {
            jCheckBox1.setSelected(false);
            jTextField10.setText("");
            jTextField11.setText("");
            jTextField12.setText("");
            jTextField19.setText("");
        }
        //------------------------
        if (dis.readBoolean()) {
            jCheckBox2.setSelected(true);
            jTextField9.setText(Integer.toString(dis.readInt()));
            jTextField13.setText(Integer.toString(dis.readInt()));
            jTextField14.setText(Double.toString(dis.readDouble()));
            jTextField15.setText(Double.toString(dis.readDouble()));
            jTextField20.setText(Integer.toString(dis.readInt()));
        } else {
            jCheckBox2.setSelected(false);
            jTextField13.setText("");
            jTextField14.setText("");
            jTextField15.setText("");
            jTextField20.setText("");
        }
        //-----------------------
        if (dis.readBoolean()) {
            jCheckBox3.setSelected(true);
            jTextField9.setText(Integer.toString(dis.readInt()));
            jTextField16.setText(Integer.toString(dis.readInt()));
            jTextField17.setText(Double.toString(dis.readDouble()));
            jTextField18.setText(Double.toString(dis.readDouble()));
            jTextField21.setText(Integer.toString(dis.readInt()));
        } else {
            jCheckBox3.setSelected(false);
            jTextField16.setText("");
            jTextField17.setText("");
            jTextField18.setText("");
            jTextField21.setText("");
        }
    }

    private void save(DataOutputStream dos) throws IOException {
        dos.writeDouble(DeviceConfig.perfomens);
        dos.writeDouble(DeviceConfig.memorySize);
        dos.writeDouble(DeviceConfig.vzuSize);
        dos.writeDouble(DeviceConfig.SystemShinaPerfomens);
        dos.writeDouble(DeviceConfig.GMSHPerfomens);
        dos.writeDouble(DeviceConfig.ICHPerfomens);
        dos.writeDouble(DeviceConfig.memoryShinaPerfomens);
        dos.writeDouble(DeviceConfig.vzuShinaPerfomens);
        //-------------------------------
        if (jCheckBox1.isSelected()) {
            dos.writeBoolean(true);
            dos.writeInt(DeviceConfig.processConfig[0].kolCom);
            dos.writeInt(DeviceConfig.processConfig[0].kolObr);
            dos.writeDouble(DeviceConfig.processConfig[0].dlFile);
            dos.writeDouble(DeviceConfig.processConfig[0].dlWrite);
            dos.writeInt(DeviceConfig.processConfig[0].numVzu);
        } else {
            dos.writeBoolean(false);
        }
        //-------------------------------------
        if (jCheckBox2.isSelected()) {
            dos.writeBoolean(true);
            dos.writeInt(DeviceConfig.processConfig[1].kolCom);
            dos.writeInt(DeviceConfig.processConfig[1].kolObr);
            dos.writeDouble(DeviceConfig.processConfig[1].dlFile);
            dos.writeDouble(DeviceConfig.processConfig[1].dlWrite);
            dos.writeInt(DeviceConfig.processConfig[1].numVzu);
        } else {
            dos.writeBoolean(false);
        }
        //--------------------------------------
        if (jCheckBox3.isSelected()) {
            dos.writeBoolean(true);
            dos.writeInt(DeviceConfig.processConfig[2].kolCom);
            dos.writeInt(DeviceConfig.processConfig[2].kolObr);
            dos.writeDouble(DeviceConfig.processConfig[2].dlFile);
            dos.writeDouble(DeviceConfig.processConfig[2].dlWrite);
            dos.writeInt(DeviceConfig.processConfig[2].numVzu);
        } else {
            dos.writeBoolean(false);
        }
    }
}
