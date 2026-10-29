module fu.de190381.ch08javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;      // T5
    requires javafx.media;    // T5

    // FXMLLoader cần reflection vào controller (@FXML private field/method)
    // → thêm "opens <package> to javafx.fxml" cho mỗi package chứa controller (b4, b5, b6, t1...)
    // → thêm "opens <package> to javafx.base" cho package chứa model dùng PropertyValueFactory (t1, t4)
    opens fu.de190381.ch08javafx to javafx.fxml;

    // Application class phải được export để javafx.graphics khởi tạo được
    // → thêm "exports <package>" cho mỗi package chứa class Application (b1...b6, t1...t6)
    exports fu.de190381.ch08javafx;
    exports fu.de190381.ch08javafx.b1;   // B1 — UI viết bằng code (không cần opens)
    exports fu.de190381.ch08javafx.b2;   // B2 — UI viết bằng code (không cần opens)

    // Lưu ý: package common (AlertUtil) dùng chung trong cùng module → KHÔNG cần exports/opens.
}
