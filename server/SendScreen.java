package server;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

public class SendScreen extends Thread {
    Socket socket = null;
    Robot robot = null;
    Rectangle rect = null;
    boolean continueLoop = true;
    OutputStream oos = null;

    public SendScreen(Socket socket, Robot robot, Rectangle rect){
        this.socket = socket;
        this.robot = robot;
        this.rect = rect;
        start();
    }

    public void run(){
        try {
            oos = socket.getOutputStream();            
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (continueLoop) {
            BufferedImage bi = robot.createScreenCapture(rect);
            try {
                ImageIO.write(bi, "jpeg", oos);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
