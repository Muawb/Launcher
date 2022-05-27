package net.minecraft.settings.file;

import net.minecraft.settings.Utils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zipper {

    private static ZipEntry entry;
    private static long size;
    private static String name;

    public static void unZipBin() {
        FileOutputStream out = null;
        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(
                Utils.getWorkDir().toString() + File.separator + "client.zip"))) {
            while ((entry = zis.getNextEntry()) != null){
                size = entry.getSize();
                name = entry.getName();

                out = new FileOutputStream(Utils.getWorkDir().toString() +
                        File.separator + "bin" + File.separator + name);
                IOUtils.copy(zis, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(out);
        }
    }

    public static void unZipNatives(){
        FileOutputStream out = null;
        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(
                Utils.getWorkDir() + File.separator + "native.zip"))) {
            while ((entry = zis.getNextEntry()) != null){
                name = entry.getName();
                size = entry.getSize();

                out = new FileOutputStream(Utils.getWorkDir().toString() +
                        File.separator + "bin" + File.separator + "natives" + File.separator + name);
                IOUtils.copy(zis, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
}
