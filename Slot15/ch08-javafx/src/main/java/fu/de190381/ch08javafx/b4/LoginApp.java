package fu.de190381.ch08javafx.b4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * TODO 8: Application cho B4 — load login-view.fxml bằng FXMLLoader.
 * Đường dẫn tương đối → tìm trong resources cùng package với class này.
 */
public class LoginApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // getResource("login-view.fxml") → tìm ở resources/fu/de190381/ch08javafx/b4/
        FXMLLoader loader = new FXMLLoader(LoginApp.class.getResource("login-view.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("B4 - Đăng nhập (FXML)");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
