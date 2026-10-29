module fu.de190381.ch08javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;      // T5
    requires javafx.media;    // T5

    // FXMLLoader cần reflection vào controller (@FXML private field/method)
    // → thêm "opens <package> to javafx.fxml" cho mỗi package chứa controller (b4, b5, b6, t1...)
    // → thêm "opens <package> to javafx.base" cho package chứa model dùng PropertyValueFactory (t1, t4)
    opens fu.de190381.ch08javafx to javafx.fxml;
    opens fu.de190381.ch08javafx.b4 to javafx.fxml;  // B4 — FXML + Controller

    // Application class phải được export để javafx.graphics khởi tạo được
    // → thêm "exports <package>" cho mỗi package chứa class Application (b1...b6, t1...t6)
    exports fu.de190381.ch08javafx;
    exports fu.de190381.ch08javafx.b1;   // B1 — UI viết bằng code (không cần opens)
    exports fu.de190381.ch08javafx.b2;   // B2 — Layout & Form đăng nhập, code thuần (không cần opens)
    exports fu.de190381.ch08javafx.b3;   // B3 — Form đăng ký + Validate (không cần opens)
    exports fu.de190381.ch08javafx.b4;   // B4 — FXML + Controller (cần opens ở trên)

    // Lưu ý: package common (AlertUtil) dùng chung trong cùng module → KHÔNG cần exports/opens.
}
