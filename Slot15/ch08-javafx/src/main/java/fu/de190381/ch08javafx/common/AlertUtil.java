package fu.de190381.ch08javafx.common;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Tiện ích hiển thị hộp thoại thông báo dùng chung cho các bài tập Chapter 08.
 * <p>
 * Cùng module → các package b1..b6, t1..t6 gọi {@code AlertUtil.info(...)} mà không cần
 * khai báo {@code exports} trong module-info.
 */
public final class AlertUtil {
    private AlertUtil() {}

    public static void info(String msg)  { show(Alert.AlertType.INFORMATION, msg); }
    public static void warn(String msg)  { show(Alert.AlertType.WARNING, msg); }
    public static void error(String msg) { show(Alert.AlertType.ERROR, msg); }

    public static boolean confirm(String msg) {
        return new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO)
                .showAndWait().filter(b -> b == ButtonType.YES).isPresent();
    }

    private static void show(Alert.AlertType type, String msg) {
        Alert a = new Alert(type, msg);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
