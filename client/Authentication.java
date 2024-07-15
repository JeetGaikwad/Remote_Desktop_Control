package client;

import javax.swing.JButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Authentication extends JFrame implements ActionListener{
    private Socket cSocket = null;
    DataOutputStream password_check = null;
    DataInputStream verification = null;
    String verify = "";
    JButton submit;
    JPanel panel;
    JLabel label,label1;
    String width="",height="";
    JTextField text1;

    Authentication(Socket cSocket){
        label1 = new JLabel();
        label1.setText("Password");
        text1 = new JTextField(15);
        this.cSocket = cSocket;
        label = new JLabel();
        label.setText("");
        this.setLayout(new BorderLayout());
        submit = new JButton("submit");
        panel = new JPanel(new GridLayout(2,1));
        panel.add(label1);
        panel.add(text1);
        panel.add(label);
        panel.add(submit);
        add(panel,BorderLayout.CENTER);
        submit.addActionListener(this);
        setTitle("Login form");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String value = text1.getText();
        try {
            // Java used to write primitive java data type to output stream.
            password_check = new DataOutputStream(cSocket.getOutputStream());
            verification = new DataInputStream(cSocket.getInputStream());
            password_check.writeUTF(value);
            verify = verification.readUTF();
        } catch (IOException exp) {
            exp.printStackTrace();
        }
        if(verify.equals("valid")){
            try {
                width = verification.readUTF();
                height = verification.readUTF();
            } catch (IOException exp2) {
                exp2.printStackTrace();
            }
            CreateFrame abc = new CreateFrame(cSocket, width, height);
            dispose();
        }
        else{
            System.out.print("please enter valid password");
            JOptionPane.showMessageDialog(this, "Incorrect Password","Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
}
