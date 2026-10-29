package fu.de190381.ch08javafx.b2;

import fu.de190381.ch08javafx.common.AlertUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * B2 — Layout thông dụng & Form đăng nhập (code thuần, không FXML).
 * Chuyển màn hình bằng scene.setRoot(newRoot) — giữ nguyên Stage & Scene.
 */
public class LoginLayoutApp extends Application {

    private static final Map<String, String> ACCOUNTS = Map.of(
            "admin", "Admin@123",
            "student", "Student@123");

    private static final Border DEBUG_BORDER = new Border(new BorderStroke(
            Color.RED, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(1)));

    private Scene scene;
    private String rememberedUser;
    private final List<Region> containers = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        scene = new Scene(buildLoginView(), 680, 480);
        stage.setScene(scene);
        stage.setTitle("B2 - Layout & Form đăng nhập");
        stage.setMinWidth(480);
        stage.setMinHeight(400);
        stage.show();
    }

    // TODO 1: Khung chính — BorderPane 3 vùng top / center / bottom
    private Parent buildLoginView() {
        containers.clear();
        BorderPane root = new BorderPane();
        root.setTop(buildHeader());
        root.setCenter(new Label("(center)"));
        root.setBottom(new Label("(footer)"));
        return root;
    }

    // TODO 2: Header — HBox: logo | tiêu đề | SPACER giãn | version bên phải
    private Node buildHeader() {
        Label logo = new Label("HSF");
        logo.setStyle("-fx-background-color: #f27024; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 4 8;");

        Label title = new Label("Hệ thống quản lý sinh viên");
        title.setStyle("-fx-font-size: 16; -fx-text-fill: white;");

        Label version = new Label("v1.0");
        version.setStyle("-fx-text-fill: #cfd8dc;");

        // Spacer: setHgrow(ALWAYS) → chiếm hết chỗ trống → đẩy "v1.0" sang phải
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(10, logo, title, spacer, version);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10, 16, 10, 16));
        header.setStyle("-fx-background-color: #263238;");
        return header;
    }

    public static void main(String[] args) { launch(args); }
}
