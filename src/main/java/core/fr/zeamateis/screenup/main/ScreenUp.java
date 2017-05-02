package fr.zeamateis.screenup.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;

public class ScreenUp
{
    static Minecraft mc = Minecraft.getMinecraft();

    public static void saveScreen() throws Exception {
        Robot robot = new Robot();

        // The hard part is knowing WHERE to capture the screen shot from
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Display.getX(), Display.getY(), Display.getWidth(), Display.getHeight()));
        Graphics2D graphics = screenShot.createGraphics();

        // Save your screen shot with its label
        ImageIO.write(screenShot, "PNG", new File("./myScreenShot.png"));
    }
}
