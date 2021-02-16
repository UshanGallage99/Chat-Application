import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginFormController {
    public JFXPasswordField txtPw;
    public JFXTextField txtUserName;
    public JFXButton btnLog;
    public AnchorPane root;

    public void btnLogOnAction(ActionEvent actionEvent) throws IOException {
        String user = txtUserName.getText().trim();
        String password = txtPw.getText().trim();
        if (user.length() > 0 && password.length() > 0) {

            if (user.equalsIgnoreCase("User")
                    && password.equalsIgnoreCase("1234")) {

                this.root.getChildren().clear();
                this.root.getChildren()
                        .add(FXMLLoader.
                                load(this.getClass().getResource("ClientUi.fxml")));

            } else {
                new Alert(Alert.AlertType.WARNING, "Wrong Password Or User Name !",
                        ButtonType.OK).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty !",
                    ButtonType.OK).show();

        }
    }
}
