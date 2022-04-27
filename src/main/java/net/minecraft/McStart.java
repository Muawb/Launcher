package net.minecraft;

import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class McStart extends Applet implements Runnable, AppletStub, MouseListener {

    private static final long serialVersionUID = 1L;
    private Applet mcApplet;
    public Map<String, String> clientData = new HashMap<String, String>();
    private int context;
    public boolean active = false;
    private URL[] urls;
    private String bin;

    public McStart(String bin, URL[] urls){
        this.bin = bin;
        this.urls = urls;
    }

    @Override
    public void init(){
        if (mcApplet != null){
            mcApplet.init();
            return;
        }
        init(0);
    }

    public void init(int i){
        URLClassLoader c1 = new URLClassLoader(urls);
        System.setProperty("org.lwjgl.librarypath", bin + "natives");
        System.setProperty("net.java.games.input.librarypath", bin + "natives");
        try{
            Class<?> mine = c1.loadClass("net.minecraft.client.MinecraftApplet");
            Applet applet = (Applet) mine.newInstance();
            mcApplet = applet;
            applet.setStub(this);
            applet.setSize(getWidth(),  getHeight());
            setLayout(new BorderLayout());
            add(applet, "Center");
            applet.init();
            active = true;
            validate();
        } catch (Exception e){
        e.printStackTrace();
        }
    }

    @Override
    public String getParameter(String name){
        String custom = (String) clientData.get(name);
        if (custom != null) return custom;

        try{
            return super.getParameter(name);
        } catch (Exception e){
            clientData.put(name, null);
        }
        return null;
    }

    @Override
    public void start(){
        if (mcApplet != null){
            mcApplet.start();
            return;
        }
    }

    @Override
    public boolean isActive(){

        if (context == 0){
            context = -1;

            try {

            } catch (Exception e){
                if (getAppletContext() != null){
                    context = 1;

                }
            }
        }
        if (context == -1){
            return active;
        }
        return super.isActive();
    }

    @Override
    public URL getDocumentBase(){
        try {
            return new URL("https://minecrafttest.ucoz.net");
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public void stop(){

        if (mcApplet != null){
            active = false;
            mcApplet.stop();
            return;
        }
    }

    @Override
    public void destroy(){

        if (mcApplet != null){
            mcApplet.destroy();
            return;
        }
    }

    public void mouseClicked(MouseEvent paramMouseEvent){}
    public void mousePressed(MouseEvent paramMouseEvent){}
    public void mouseReleased(MouseEvent paramMouseEvent){}
    public void mouseEntered(MouseEvent paramMouseEvent) {}
    public void mouseExited(MouseEvent paramMouseEvent){}
    public void appletResize(int paramInt1, int paramInt2){}
    public void run(){}
}















