package client;

import java.awt.Graphics;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ReceivingScreen extends Thread {
    private InputStream cInputStream;
    private JPanel cPanel;
    private boolean continueLoop = true;

    public ReceivingScreen(InputStream inputStream, JPanel panel) {
        cInputStream = inputStream;
        cPanel = panel;
        start();
    }

    @Override
    public void run() {
        try {
            while (continueLoop) {
                byte[] bytes = new byte[1024 * 1024];
                int count = 0;
                do {
                    count += cInputStream.read(bytes, count, bytes.length - count);
                } while (!(count > 4 && bytes[count - 2] == (byte) - 1 && bytes[count - 1] == (byte) - 39));

                Image image = ImageIO.read(new ByteArrayInputStream(bytes));
                image = image.getScaledInstance(cPanel.getWidth(), cPanel.getHeight(), Image.SCALE_FAST);

                // Draw the image onto the panel
                cPanel.getGraphics().drawImage(image, 0, 0, cPanel.getWidth(), cPanel.getHeight(), cPanel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
