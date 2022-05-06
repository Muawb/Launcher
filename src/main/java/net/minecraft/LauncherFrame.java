package net.minecraft;

import net.minecraft.settings.GuardUtils;
import net.minecraft.settings.Utils;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.Random;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class LauncherFrame extends JFrame {

    public static JFrame frame;
    public static Properties saveName = null;
    public static JTextField name;
    public static ImageIcon image;

    private static BufferedInputStream input;
    private static JProgressBar upload;
    private static JLabel updText;
    private static FileOutputStream output;
    private static MCLauncher mineStart;
    private static File client = null;


    public void frame() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frame.setBounds(dimension.width / 2 - 350, dimension.height / 2 - 200, 700, 400);
        frame.setTitle("Launcher");

        LauncherComponent paint = new LauncherComponent();
        frame.setContentPane(paint);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.GRAY);
        panel.setBounds(0, 1, 200, 359);
        frame.add(panel);

        JPanel pane = new JPanel();
        pane.setBounds(240, 200, 400, 70);
        pane.setBackground(Color.lightGray);
        pane.setLayout(new GridBagLayout());
        frame.add(pane);
        pane.setVisible(false);

        updText = new JLabel();
        updText.setText("");

        upload = new JProgressBar();
        upload.setStringPainted(true);
        upload.setMinimum(0);
        upload.setMaximum(100);
        upload.setValue(0);
        upload.setPreferredSize(new Dimension(400,35));
        pane.add(upload, new GridBagConstraints(0,0,1,1,0.0,0.9,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(15,0,0,0),0,0));
        pane.add(updText, new GridBagConstraints(0,0,1,1,0.0,0.9,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(5,0,0,0), 0,0));

        image = new ImageIcon(this.getClass().getResource("/settings.png"));
        JButton settings = new JButton();
        settings.setPreferredSize(new Dimension(40,40));
        settings.setIcon(image);
        panel.add(settings, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(47,0,0,130), 0,0));

        JButton start = new JButton("Играть");
        start.setPreferredSize(new Dimension(94, 35));
        panel.add(start, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(50, 13, 0, 0), 0, 0));

        name = new JTextField(8);
        name.setPreferredSize(new Dimension(25, 25));
        panel.add(name, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(18, 12, 0, 0), 0, 0));

        JLabel login = new JLabel("Логин: ");
        panel.add(login, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.9,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(21, 0, 0, 125), 0, 0));

        try {
            saveName = new Properties();
            FileInputStream in = new FileInputStream(Utils.getWorkDir().getAbsolutePath() +
                    File.separator + "settings.properties");
            saveName.load(in);
            name.setText(saveName.getProperty("name"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setVisible(true);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.getText().equals("")) {
                    JOptionPane.showMessageDialog((Component)
                                    null,
                            "Вы не ввели ник в игре !",
                            "Ошибка",
                            JOptionPane.WARNING_MESSAGE);

                } else {
                    new Thread(() -> {
                        try {
                            saveName.setProperty("name", name.getText());
                            FileOutputStream out = new FileOutputStream(Utils.getWorkDir().getAbsolutePath() +
                                    File.separator + "settings.properties");
                            saveName.store(out, "Save name");
                            start.setVisible(false);
                            pane.setVisible(true);
                            name.setVisible(false);
                            login.setVisible(false);
                            GuardUtils.chekFile(Utils.getWorkDir().toString() + File.separator + "bin" +
                                            File.separator + "minecraft.jar" + File.separator,
                                    name.getText(), sessionUpdate());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    })
                    .start();
                }
            }
        });
    }

    @NotNull
    public static String sessionUpdate(){
        Random r = new Random();
        int x = r.nextInt(1000000000);
        return String.valueOf(x);
    }

    public static void getUpdate() {
        new Thread(() -> {
            try {
                updText.setText("Подключение к веб серверу...");
                URL url = new URL("https://minecrafttest.ucoz.net/Download/client.zip");
                URLConnection connection = url.openConnection();

                File client = new File(Utils.getWorkDir().getAbsolutePath()
                        + File.separator, "client.zip");

                long cll_web = connection.getContentLength();
                upload.setMaximum((int) cll_web);

                input = new BufferedInputStream(connection.getInputStream());
                updText.setText("Проверяем пути...");
                output = new FileOutputStream(client);
                updText.setText("Загружаю клиент...");

                byte[] buffer = new byte[1024];
                int count = 0;

                while ((count = input.read(buffer)) != -1) {
                    output.write(buffer, 0, count);
                    upload.setValue((int) client.length());
                    upload.setString("Скачано " + (int) client.length() / 1024 + " Кбайт из: " + (cll_web / 1024));

                }
                downloadNative();
                output.close();
                updText.setText("Распаковываем клиент");

                new File(Utils.getWorkDir().getAbsolutePath() + File.separator + "bin").mkdir();
                Utils.unZip(Utils.getWorkDir().toString() + File.separator + "client.zip",
                        Utils.getWorkDir().toString() + File.separator + "bin" + File.separator);

                new File(Utils.getWorkDir().toString() + File.separator + "bin" +  File.separator + "natives").mkdir();
                Utils.unZipNatives(Utils.getWorkDir() + File.separator + "native.zip",
                        Utils.getWorkDir().toString() + File.separator + "bin" + File.separator + "natives" + File.separator);

                updText.setText("Распаковка завершена");
                upload.setString("Готово !");
                mineStart = new MCLauncher(name.getText(), sessionUpdate());
                frame.setVisible(false);

            } catch (IOException e) {
                e.printStackTrace();
            }
        })
         .start();
    }

    public static void downloadNative(){
            try {
                URL url = new URL("https://minecrafttest.ucoz.net/Download/native.zip");
                URLConnection connection = url.openConnection();

                client = new File(Utils.getWorkDir().toString() + File.separator + "native.zip");

                BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
                FileOutputStream output = new FileOutputStream(client);

                byte[] buffer = new byte[4096];
                int count = 0;

                while ((count = input.read(buffer)) != -1) {
                    output.write(buffer, 0, count);
                }
                output.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}

