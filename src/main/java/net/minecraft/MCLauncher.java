package net.minecraft;

import net.minecraft.settings.Utils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MCLauncher extends JFrame {

     private static final long serialVersionUID = 1L;

     public Map<String, String> clientData = new HashMap<String, String>();

     public MCLauncher(String user, String session){
         try{
             String bin = Utils.getWorkDir().toString() + File.separator + "bin" + File.separator;
             setForeground(Color.BLACK);
             setBackground(Color.BLACK);

             URL[] urls = new URL[4];
             urls[0] = new File(bin, "minecraft.jar").toURI().toURL();
             urls[1] = new File(bin, "lwjgl.jar").toURI().toURL();
             urls[2] = new File(bin, "jinput.jar").toURI().toURL();
             urls[3] = new File(bin, "lwjgl_util.jar").toURI().toURL();

             final McStart mcapplet = new McStart(bin, urls);
             mcapplet.clientData.put("username", user);
             mcapplet.clientData.put("sessionid", session);
             mcapplet.clientData.put("stand-alone", "true");

             setTitle("Minecraft");
             addWindowListener(new WindowListener() {
                 @Override
                 public void windowOpened(WindowEvent e) {}

                 @Override
                 public void windowClosing(WindowEvent e) {
                     mcapplet.stop();
                     mcapplet.destroy();
                     System.exit(0);
                 }

                 @Override
                 public void windowClosed(WindowEvent e) {}

                 @Override
                 public void windowIconified(WindowEvent e) {}

                 @Override
                 public void windowDeiconified(WindowEvent e) {}

                 @Override
                 public void windowActivated(WindowEvent e) {}

                 @Override
                 public void windowDeactivated(WindowEvent e) {}
             });
             mcapplet.setForeground(Color.BLACK);
             mcapplet.setBackground(Color.BLACK);
             setLayout(new BorderLayout());
             add(mcapplet, BorderLayout.CENTER);
             validate();
             Toolkit toolkit = Toolkit.getDefaultToolkit();
             Dimension dimension = toolkit.getScreenSize();
             setBounds(dimension.width/2 - 400, dimension.height/2 - 240, 800, 480);
             this.setVisible(true);
             mcapplet.init();
             mcapplet.start();
         }catch (Exception e){
             e.printStackTrace();
         }
     }

}
