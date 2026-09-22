package fu.de190381.pojo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
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

    // TODO 5.2 — Owning side của quan hệ N-N với Project.
    // Employee giữ bảng trung gian employee_project với 2 FK:
    //   employee_id → trỏ về bảng employees (joinColumns)
    //   project_id  → trỏ về bảng projects  (inverseJoinColumns)
    // Không dùng cascade = ALL ở N-N vì tránh xóa nhầm entity phía bên kia:
    //   ví dụ xóa 1 Employee không được kéo theo xóa Project.
    // FetchType mặc định của @ManyToMany là LAZY — giữ nguyên để tránh query thừa.
    @ManyToMany
    @JoinTable(
        name = "employee_project",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
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

    // TODO 5.5 — Helper method đồng bộ 2 chiều khi phân công nhân viên vào dự án.
    // Phải add vào CẢ 2 phía để in-memory object nhất quán ngay lập tức,
    // không cần reload từ DB mới thấy quan hệ ngược chiều.
    // Lưu ý: chỉ cần gọi method này 1 lần từ owning side (Employee),
    // KHÔNG gọi thêm p.getEmployees().add(this) ở chỗ khác để tránh thêm 2 lần.
    public void assignToProject(Project p) {
        this.projects.add(p);         // owning side: Employee → Project
        p.getEmployees().add(this);   // inverse side: Project → Employee (đồng bộ in-memory)
    }

    // TODO 5.9 — Helper method đồng bộ 2 chiều khi gỡ nhân viên khỏi dự án.
    // Phải remove khỏi CẢ 2 phía để in-memory object nhất quán ngay lập tức.
    // Hibernate sẽ tự DELETE dòng tương ứng trong bảng employee_project khi commit.
    // Không xóa Employee hay Project gốc — chỉ xóa liên kết trong bảng trung gian.
    public void unassignFromProject(Project p) {
        this.projects.remove(p);      // owning side: Employee → Project
        p.getEmployees().remove(this); // inverse side: Project → Employee (đồng bộ in-memory)
    }

    // TODO 5.4 — Dùng business key (email) thay vì id cho equals/hashCode.
    // Lý do KHÔNG dùng id:
    //   - Entity mới chưa persist có id = null → 2 object khác nhau sẽ bằng nhau (sai).
    //   - Khi entity được thêm vào HashSet trước khi persist, id = null,
    //     sau persist id được gán → hashCode thay đổi → Set không tìm được phần tử (sai).
    // Lý do dùng email:
    //   - email là unique + not null → đảm bảo phân biệt đúng mọi Employee.
    //   - Dùng Set<Employee> trong Project.employees sẽ hoạt động đúng:
    //     thêm cùng 1 Employee 2 lần (cùng email, khác object) → Set chỉ giữ 1 phần tử.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee other = (Employee) o;
        return Objects.equals(this.email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
