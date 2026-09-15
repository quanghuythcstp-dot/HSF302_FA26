package fu.de190381.pojo;

/**
 * Enum đại diện giới tính nhân viên.
 * Dùng @Enumerated(EnumType.STRING) trong Employee để lưu chuỗi thay vì ordinal.
 */
public enum Gender {
    MALE,
    FEMALE,
    OTHER
}
