package net.minecraft;

import net.minecraft.settings.GuardUtils;
import net.minecraft.settings.Utils;
import net.minecraft.settings.file.Login;
import net.minecraft.settings.file.Zipper;
import net.minecraft.settings.user.SessionID;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class LauncherFrame {

    public String nickName;
    public static JFrame frame;
    public static JTextField name = new JTextField();
    public static MCLauncher mineStart;
    private static BufferedInputStream input;
    private static JProgressBar upload;
    private static JLabel updText;
    private static FileOutputStream output;
    private Properties saveName;
    private ImageIcon image;
    private static Path folder;
    private static JButton settings;

    public void frame() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frame.setBounds(dimension.width / 2 - 350, dimension.height / 2 - 200, 700, 400);
        frame.setTitle("Main");

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
        settings = new JButton();
        settings.setBackground(new Color(255,204,102));
        settings.setPreferredSize(new Dimension(40,40));
        settings.setIcon(image);
        panel.add(settings, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(47,0,0,130), 0,0));

        JButton start = new JButton("Играть");
        start.setBackground(new Color(255,204,102));
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

        JButton exit = new JButton("Назад");
        exit.setBackground(new Color(255,204,102));
        exit.setVisible(false);
        panel.add(exit, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(53,0,0,0), 0,0));

        JCheckBox big = new JCheckBox();
        big.setVisible(false);
        panel.add(big, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(30,85,0,0),0,0));

        JLabel label_big = new JLabel("На весь экран:");
        label_big.setVisible(false);
        panel.add(label_big, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(30,0,0,20),0,0));

        JLabel label_settings = new JLabel("Настрйки");
        label_settings.setVisible(false);
        panel.add(label_settings, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(0,0,0,0),0,0));
        Login l = new Login();
        l.update();
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
                    l.load();
                    start.setVisible(false);
                    pane.setVisible(true);
                    name.setVisible(false);
                    login.setVisible(false);
                    GuardUtils.chekFile();
                }
            }
        });
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name.setVisible(false);
                start.setVisible(false);
                login.setVisible(false);
                exit.setVisible(true);
                settings.setVisible(false);
                big.setVisible(true);
                label_big.setVisible(true);
                label_settings.setVisible(true);
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit.setVisible(false);
                name.setVisible(true);
                start.setVisible(true);
                login.setVisible(true);
                exit.setVisible(false);
                settings.setVisible(true);
                big.setVisible(false);
                label_big.setVisible(false);
                label_settings.setVisible(false);
            }
        });
        big.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MCLauncher.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            }
        });
    }


    public static void getUpdate() {
        new Thread(() -> {
            URLConnection connection = null;
            settings.setVisible(false);
            try {
                updText.setText("Подключение к веб серверу...");
                URL url = new URL("https://minecrafttest.ucoz.net/Download/client.zip");
                connection = url.openConnection();

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
                updText.setText("Распаковываем клиент");

                folder = Files.createDirectory(Paths.get(
                        Utils.getWorkDir().getAbsolutePath() + File.separator + "bin"));
                Zipper.unZipBin();

                folder = Files.createDirectory(Paths.get(Utils.getWorkDir().getAbsolutePath() +
                        File.separator + "bin" + File.separator + "natives"));
                Zipper.unZipNatives();
                updText.setText("Распаковка завершена");
                upload.setString("Готово !");
                mineStart = new MCLauncher(name.getText(), SessionID.sessionUpdate());
                frame.setVisible(false);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(output);
            }
        })
         .start();
    }

    public static void downloadNative(){
            try {
                URL url = new URL("https://minecrafttest.ucoz.net/Download/native.zip");
                URLConnection connection = url.openConnection();
                File client = new File(Utils.getWorkDir().toString() + File.separator + "native.zip");

                BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
                FileOutputStream output = new FileOutputStream(client);

                byte[] buffer = new byte[4096];
                int count = 0;

                while ((count = input.read(buffer)) != -1) {
                    output.write(buffer, 0, count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(output);
            }
    }
}

