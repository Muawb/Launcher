package net.minecraft;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class LauncherComponent extends JComponent {

    private Image image;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        try {
            image = new ImageIcon("src/main/resources/icon.jpg").getImage();
        }catch (Exception e){
            System.out.println("Файл не найден !");
        }

        int w = getWidth();
        int h = getHeight();

        for (int x = 0; x <= w / 64; x ++){
            for (int y = 0; y <= h / 64; y++){
                g2.drawImage(image, x * 64, y * 64, null);
            }
        }

        Point2D p1u = new Point2D.Float(0, 0);
        Point2D p2u = new Point2D.Float(200, 0);

        Point2D p1r = new Point2D.Float(200, 0);
        Point2D p2r = new Point2D.Float(200, 400);

        Point2D p1d = new Point2D.Float(0,360);
        Point2D p2d = new Point2D.Float(200, 360);

        Line2D lineDown = new Line2D.Float(p1d, p2d);
        Line2D lineRight = new Line2D.Float(p1r, p2r);
        Line2D lineUp = new Line2D.Float(p1u, p2u);

        Font logo = new Font("Bitstream Charter", Font.BOLD, 50);
        g2.setFont(logo);
        g2.drawString("Minecraft", 345,60);


        g2.draw(lineUp);
        g2.draw(lineRight);
        g2.draw(lineDown);
    }
}
