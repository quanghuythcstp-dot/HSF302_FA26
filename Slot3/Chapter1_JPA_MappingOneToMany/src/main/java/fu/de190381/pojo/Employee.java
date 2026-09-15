package fu.de190381.pojo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity đại diện cho nhân viên trong công ty.
 * Owning side của quan hệ ManyToOne với Department (giữ FK department_id).
 */
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal salary;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(unique = true, nullable = false)
    private String email;

    // Lưu chuỗi MALE/FEMALE/OTHER thay vì số thứ tự ordinal
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Gender gender;

    // true = đang làm việc, false = đã nghỉ
    @Column(nullable = false)
    private boolean active = true;

    // Constructor không tham số — bắt buộc cho JPA
    public Employee() {
    }

    public Employee(String email, String fullName, Gender gender,
                    BigDecimal salary, LocalDate hireDate) {
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.salary = salary;
        this.hireDate = hireDate;
        this.active = true;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id
                + ", fullName='" + fullName + "'"
                + ", email='" + email + "'"
                + ", salary=" + salary
                + ", hireDate=" + hireDate
                + ", gender=" + gender
                + ", active=" + active
                + "}";
    }
}
