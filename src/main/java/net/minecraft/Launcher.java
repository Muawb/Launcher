package net.minecraft;

import javax.swing.*;
import javax.swing.UIManager.*;

public class Launcher {

    private static LauncherFrame launcherFrame;

    public static void main(String[] args) {

        try{
           for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
               if("Nimbus".equals(info.getName())){
                   UIManager.setLookAndFeel(info.getClassName());
               }
           }
        } catch (Exception e){
            System.out.println("Error");
        }

        launcherFrame = new LauncherFrame();
        launcherFrame.frame();
    }
}
