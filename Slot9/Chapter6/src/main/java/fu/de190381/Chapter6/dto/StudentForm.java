package fu.de190381.Chapter6.dto;

import jakarta.validation.constraints.*;

/**
 * DTO dùng cho form Create/Edit — tách khỏi entity Student.
 * Tránh mass assignment: người dùng không thể gửi thêm field ngoài ý muốn.
 */
public class StudentForm {

    /** null khi Create, có giá trị khi Edit */
    private Long id;

    @NotBlank(message = "Tên không được để trống")
    @Size(min = 2, max = 50, message = "Tên phải từ 2 đến 50 ký tự")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Size(max = 100, message = "Email tối đa 100 ký tự")
    private String email;

    @NotNull(message = "Tuổi không được để trống")
    @Min(value = 18, message = "Tuổi tối thiểu là 18")
    @Max(value = 30, message = "Tuổi tối đa là 30")
    private Integer age;

    @NotBlank(message = "Chuyên ngành không được để trống")
    private String major;

    @NotNull(message = "GPA không được để trống")
    @DecimalMin(value = "0.0", message = "GPA tối thiểu là 0.0")
    @DecimalMax(value = "4.0", message = "GPA tối đa là 4.0")
    private Double gpa;

    // ========== Constructors ==========

    public StudentForm() {}

    /** Tạo từ entity (dùng khi load form Edit) */
    public static StudentForm fromEntity(fu.de190381.Chapter6.entity.Student student) {
        StudentForm form = new StudentForm();
        form.id    = student.getId();
        form.name  = student.getName();
        form.email = student.getEmail();
        form.age   = student.getAge();
        form.major = student.getMajor();
        form.gpa   = student.getGpa();
        return form;
    }

    // ========== Getters & Setters ==========

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }
}
