package fu.de190381.ch08javafx.b4;

import fu.de190381.ch08javafx.common.AlertUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

/**
 * B4 — Controller cho login-view.fxml.
 * Không có new TextField() hay new Button() ở đây — toàn bộ control đến từ FXML.
 */
public class LoginController {

    // TODO 3: @FXML field — tên phải trùng fx:id trong FXML
    // FXMLLoader inject các field này SAU constructor, TRƯỚC initialize()
    @FXML private TextField     tfUsername;
    @FXML private TextField     tfPasswordVisible;
    @FXML private PasswordField pfPassword;
    @FXML private CheckBox      chkShow;
    @FXML private ComboBox<String> cbRole;
    @FXML private Label         lblError;
    @FXML private Button        btnLogin;

    // Tài khoản cố định: username → [password, vai trò]
    private static final Map<String, String[]> ACCOUNTS = Map.of(
            "student", new String[]{"Student@123", "Sinh viên"},
            "teacher", new String[]{"Teacher@123", "Giảng viên"});

    private int failedCount = 0;

    // TODO 4: initialize() — chạy SAU khi @FXML đã được inject (constructor thì chưa có!)
    @FXML
    public void initialize() {
        // Hai ô password đồng bộ nội dung — gõ ở ô nào cũng cập nhật ô kia
        tfPasswordVisible.textProperty().bindBidirectional(pfPassword.textProperty());

        // Hiện/ẩn theo CheckBox:
        // managed=false → node không chiếm chỗ trong layout khi ẩn
        tfPasswordVisible.visibleProperty().bind(chkShow.selectedProperty());
        tfPasswordVisible.managedProperty().bind(chkShow.selectedProperty());
        pfPassword.visibleProperty().bind(chkShow.selectedProperty().not());
        pfPassword.managedProperty().bind(chkShow.selectedProperty().not());

        // Disable nút khi thiếu username, password hoặc chưa chọn vai trò
        btnLogin.disableProperty().bind(
                tfUsername.textProperty().isEmpty()
                        .or(pfPassword.textProperty().isEmpty())
                        .or(cbRole.valueProperty().isNull()));

        Platform.runLater(tfUsername::requestFocus);
    }

    // handleLogin(), openWelcome(), handleForgot() sẽ thêm ở TODO 5–6
}
