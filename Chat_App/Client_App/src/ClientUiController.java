import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class ClientUiController {
    public TextArea txtMessageArea;
    public TextField txtMessage;
    public TextField txtName;
    static Socket socket = null;
    DataOutputStream output = null;
    DataInputStream input = null;
    static BufferedImage img;

    public void initialize() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("localhost", 5000);
                    input = new DataInputStream(socket.getInputStream());
                    output = new DataOutputStream(socket.getOutputStream());

                    String messageIn = "";

                    while (!messageIn.equals("end")) {
                        messageIn = input.readUTF();
                        txtMessageArea.appendText("\n" + messageIn.trim());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMessage(MouseEvent actionEvent) {
        try {
            String name = txtName.getText().trim();
            String message = txtMessage.getText().trim();

            if (name.length() == 0) {
                new Alert(Alert.AlertType.WARNING, "Please enter the name !",
                        ButtonType.OK).show();
            }
            if (message.length() == 0) {
                return;
            }
            output.writeUTF("[" + name + "]:" + message + "\n");
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void photoUpload(MouseEvent mouseEvent){
    }

   public void emojiUpload(MouseEvent mouseEvent){
    }
}
