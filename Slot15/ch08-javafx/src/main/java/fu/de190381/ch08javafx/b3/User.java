package fu.de190381.ch08javafx.b3;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO 1: Model — dùng record cho gọn; bất biến sau khi tạo.
 */
public record User(String fullName, String email, String username, String password,
                   String gender, LocalDate dob, String major, List<String> hobbies) {}
