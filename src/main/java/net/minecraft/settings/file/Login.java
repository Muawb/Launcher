package net.minecraft.settings.file;

import com.google.gson.Gson;
import net.minecraft.LauncherFrame;

import java.io.*;
import java.util.Properties;
import net.minecraft.settings.Utils;

public class Login {

    private Gson gson;
    private LauncherFrame lch;

    public void update() {
        gson = new Gson();

        try (FileReader input = new FileReader((Utils.getWorkDir().getAbsolutePath() + "settings.properties"))) {
            lch = gson.fromJson(input, LauncherFrame.class);
            lch.name.setText(lch.nickName);
        } catch (Exception e){
            System.out.println(e.getCause());
        }
    }

    public void load(){
        LauncherFrame lch = new LauncherFrame();
        lch.nickName = lch.name.getText();
        gson = new Gson();
        try(FileWriter out = new FileWriter((Utils.getWorkDir().getAbsolutePath() + "settings.properties"))){
            String str = gson.toJson(lch);
            System.out.println(str);
            out.write(str);
            out.close();
        } catch (Exception e){
            System.out.println(e.getCause());
        }
    }

}










