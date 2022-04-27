package net.minecraft.settings;

import net.minecraft.LauncherFrame;
import net.minecraft.MCLauncher;
import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GuardUtils {

    private static MCLauncher mineStart = null;
    private static MessageDigest md = null;
    private static StringBuilder sb = null;
    protected final static long size = 4870589;

    public static void chekFile(String filePath, String userName, String sessionID) {
        File folder = new File(filePath);

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String str = hashFile(md, folder);
        String hash = "1be1d81ac1541431fc1051621271c216016015c1511ce128";

        if ((folder.exists()) && (folder.length() == size) && (str.equals(hash))) {
            mineStart = new MCLauncher(userName, sessionID);
            LauncherFrame.frame.setVisible(false);
        } else {
            LauncherFrame.getUpdate();
        }
    }

    @NotNull
    public static String hashFile(MessageDigest digest, @NotNull File pathFile) {
        try {
            if (pathFile.exists()) {
                FileInputStream input = new FileInputStream(pathFile);

                byte[] list = new byte[1024];
                int count = 0;
                while ((count = input.read(list)) != -1) {
                    digest.update(list, 0, count);
                }
                input.close();

                byte[] buffer = digest.digest();

                sb = new StringBuilder();
                for (int i = 0; i < buffer.length; i++) {
                    sb.append(Integer.toString((buffer[i] & 0xff) + 0x100, 16));
                }
            } else {
                LauncherFrame.getUpdate();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}








