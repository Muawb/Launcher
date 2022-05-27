package net.minecraft.settings;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Utils {

    private static File vorkDir = null;
    private static File wrkDir;
    private static ZipEntry entry;
    private static String name;
    private static long size = 0;

    public static File getWorkDir(){
        if (vorkDir == null) {
            vorkDir = getPath("minecraft");
        }
            return vorkDir;
    }
    private static File getPath(String workDir){
        String userhome = System.getProperty("user.home",".");
        switch (getPlatform().ordinal()) {
            case 0:
                String appData = System.getenv("APPDATA");
                if (appData != null) {
                    wrkDir = new File(appData, "." + workDir + "/");
                } else {
                    wrkDir = new File(userhome, "." + workDir + "/");
                }
                break;
            case 1:
            case 2:
                wrkDir = new File(userhome, "." + workDir + "/");
                break;
            case 3:
                wrkDir = new File(userhome, "Library/Application Support" + workDir);
                break;
            default:
                wrkDir = new File(userhome, workDir + "/");
                break;
        }
            if ((!wrkDir.exists()) && (!wrkDir.mkdir())) {
                throw new RuntimeException("Рабочая директория не обнаружена (" + wrkDir + ")");
            }
            return wrkDir;
    }

    public static OS getPlatform(){
        String name = System.getProperty("os.name").toLowerCase();
        if (name.contains("win")){
            return OS.windows;
        } else if ((name.contains("linux") || (name.contains("unix")))){
            return OS.linux;
        } else if(name.contains("macos")){
            return OS.macos;
        } else if (name.contains("solaris")){
            return OS.solaris;
        } else {
            return OS.unknown;
        }
    }

    public enum OS{
        windows,
        linux,
        solaris,
        macos,
        unknown
    }
}
