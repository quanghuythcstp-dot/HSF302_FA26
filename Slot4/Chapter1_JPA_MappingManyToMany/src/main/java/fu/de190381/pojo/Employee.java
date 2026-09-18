package fu.de190381.pojo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO 5.1 — Entity đại diện cho nhân viên trong hệ thống quản lý dự án.
 * Tái sử dụng từ bài OneToMany, bỏ quan hệ ManyToOne với Department.
 * Dùng Set<Project> (không dùng List) để tránh trùng lặp trong quan hệ N-N.
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

    // unique + not null theo yêu cầu đề bài
    @Column(unique = true, nullable = false)
    private String email;

    // Lưu chuỗi MALE/FEMALE/OTHER thay vì ordinal để dễ đọc trong DB
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Gender gender;

    // true = đang làm việc, false = đã nghỉ; mặc định true theo đề bài
    @Column(nullable = false)
    private boolean active = true;

    // Quan hệ N-N với Project — sẽ được mapping ở TODO 5.2
    // Dùng HashSet để đảm bảo không trùng lặp (cần override equals/hashCode ở TODO 5.4)
    private Set<Project> projects = new HashSet<>();

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

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
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
