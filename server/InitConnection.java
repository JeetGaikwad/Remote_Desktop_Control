package server;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.GraphicsEnvironment;
import java.awt.Dimension;
import java.awt.GraphicsDevice;

public class InitConnection {

    ServerSocket socket = null;
    DataInputStream password = null;
    DataOutputStream verify = null;
    String width="",height="";

    InitConnection(int port, String value1){
        Robot robot = null;
        Rectangle rect = null;
        try {
            System.out.println("waiting for client connection");
            socket = new ServerSocket(port);
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
            GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            String width = "" + dim.getWidth();
            String height = "" + dim.getHeight();
            rect = new Rectangle(dim);
            robot = new Robot(gDev);

            drawGUI();

            while(true){
                Socket cs = socket.accept();
                password = new DataInputStream(cs.getInputStream());
                verify = new DataOutputStream(cs.getOutputStream());
                String pwd = password.readUTF();

                if(pwd.equals(value1)){
                    System.out.println("Valid password");
                    verify.writeUTF("valid");
                    verify.writeUTF(width);
                    verify.writeUTF(height);
                    new SendScreen(cs,robot,rect);
                    new RecieveEvents(cs,robot); 
                }
                else{
                    System.out.println("invalid");
                    verify.writeUTF("invalid");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void drawGUI(){}

}
