import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerUiController {
    public TextArea txtMessageArea;
    static ServerSocket serverSocket;
    static Socket socket;
    List<TaskClientConnection> connectionList = new ArrayList<TaskClientConnection>();
    String messageIn="";

    public void initialize() {
        new Thread(()->{
            try {
                serverSocket = new ServerSocket(5000);
                Platform.runLater(()
                        -> txtMessageArea.appendText("Server Started" + new Date() + '\n'));

                while (true){
                    socket=serverSocket.accept();
                    TaskClientConnection connection=new TaskClientConnection(socket,this);
                    connectionList.add(connection);
                    Thread thread=new Thread(connection);
                    thread.start();

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void broadcast(String message) {
        for (TaskClientConnection clientConnection : this.connectionList) {
            clientConnection.sendMessage(message);
        }
    }
}
