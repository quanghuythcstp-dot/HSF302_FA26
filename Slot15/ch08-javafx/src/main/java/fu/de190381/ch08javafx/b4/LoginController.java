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

    // TODO 5: Xử lý đăng nhập — validate, kiểm tra tài khoản, đếm số lần sai
    @FXML
    private void handleLogin(ActionEvent e) {
        String user = tfUsername.getText().trim().toLowerCase();
        String pass = pfPassword.getText();
        String role = cbRole.getValue();

        // Khoá sau 3 lần sai
        if (failedCount >= 3) {
            lblError.setText("Sai quá 3 lần. Vui lòng khởi động lại ứng dụng.");
            return;
        }
        // Validate định dạng username
        if (!user.matches("[a-z0-9_]{3,20}")) {
            lblError.setText("Username 3–20 ký tự, chỉ chữ thường/số/_");
            return;
        }
        // Kiểm tra tài khoản
        String[] acc = ACCOUNTS.get(user);
        if (acc == null || !acc[0].equals(pass)) {
            failedCount++;
            lblError.setText("Sai username hoặc mật khẩu (còn %d lần)".formatted(3 - failedCount));
            pfPassword.clear();
            return;
        }
        // Kiểm tra vai trò khớp tài khoản
        if (!acc[1].equals(role)) {
            lblError.setText("Vai trò không đúng với tài khoản");
            return;
        }
        lblError.setText("");
        openWelcome(user, role, (Stage) btnLogin.getScene().getWindow());
    }

    @FXML
    private void handleForgot() {
        TextInputDialog d = new TextInputDialog();
        d.setHeaderText("Nhập email để lấy lại mật khẩu");
        d.showAndWait().ifPresent(email -> {
            if (email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$"))
                AlertUtil.info("Đã gửi hướng dẫn tới " + email);
            else
                AlertUtil.warn("Email không hợp lệ");
        });
    }

    // TODO 6: Mở FXML thứ 2 và TRUYỀN DỮ LIỆU qua controller
    // loader.getController() trả về WelcomeController SAU khi load() → gọi setter để truyền dữ liệu
    private void openWelcome(String user, String role, Stage loginStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome-view.fxml"));
            Parent root = loader.load();
            WelcomeController ctrl = loader.getController();
            ctrl.setUser(user, role);

            Stage stage = new Stage();
            stage.setTitle("Chào mừng");
            stage.setScene(new Scene(root, 360, 200));
            stage.show();
            loginStage.close();
        } catch (IOException ex) {
            AlertUtil.error("Không mở được màn hình chào mừng: " + ex.getMessage());
        }
    }
}
