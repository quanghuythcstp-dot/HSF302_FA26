package fu.de190381.ch08javafx.b4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * TODO 7: Controller cho welcome-view.fxml.
 * setUser() được gọi từ LoginController sau loader.load().
 */
public class WelcomeController {

    @FXML private Label lblWelcome;

    public void setUser(String user, String role) {
        lblWelcome.setText("Xin chào %s (%s)!".formatted(user, role));
    }

    @FXML
    private void handleLogout(ActionEvent e) {
        ((Stage) lblWelcome.getScene().getWindow()).close();
    }
}
