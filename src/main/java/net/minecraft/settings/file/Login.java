package net.minecraft.settings.file;

import net.minecraft.LauncherFrame;
import net.minecraft.settings.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Login {

    private static Properties data;

    public static void saveName() {
        try {
            data = new Properties();
            FileInputStream in = new FileInputStream(Utils.getWorkDir().getAbsolutePath() +
                    File.separator + "settings.properties");
            data.load(in);
            LauncherFrame.name.setText(data.getProperty("name"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void loadName(){
        try {
            data.setProperty("name", LauncherFrame.name.getText());
            FileOutputStream out = new FileOutputStream(Utils.getWorkDir().getAbsolutePath() +
                    File.separator + "settings.properties");
            data.store(out, "Save name");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
