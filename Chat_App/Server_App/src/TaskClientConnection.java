import javafx.application.Platform;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class TaskClientConnection implements Runnable{
    Socket socket;
    ServerUiController server;
    DataInputStream input;
    DataOutputStream output;

    public TaskClientConnection(Socket socket, ServerUiController server){
        this.socket=socket;
        this.server=server;
    }
    @Override
    public void run() {
        try {

            input = new DataInputStream(
                    socket.getInputStream());
            output = new DataOutputStream(
                    socket.getOutputStream());

            while (true) {

                String message = input.readUTF();
                server.broadcast(message);
                Platform.runLater(() -> {
                    server.txtMessageArea.appendText(message + "\n");
                });

               /* BufferedImage img=ImageIO.read(ImageIO.createImageInputStream(socket.getInputStream()));
                JFrame frame = new JFrame();
                frame.getContentPane().add(new JLabel(new ImageIcon(img)));
                frame.pack();
                frame.setVisible(true);*/

            }



        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }
    public void sendMessage(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void writeOutput(String str) {

    }

}
