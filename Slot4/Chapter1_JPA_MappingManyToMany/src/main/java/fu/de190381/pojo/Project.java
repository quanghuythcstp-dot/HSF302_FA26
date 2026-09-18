package fu.de190381.pojo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO 5.1 — Entity đại diện cho dự án trong hệ thống quản lý dự án.
 * Inverse side của quan hệ N-N với Employee.
 * Dùng Set<Employee> (không dùng List) để tránh trùng lặp.
 */
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // unique + not null: mỗi dự án có mã riêng biệt
    @Column(name = "project_code", unique = true, nullable = false)
    private String projectCode;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal budget;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    // endDate có thể null nếu dự án chưa kết thúc
    @Column(name = "end_date")
    private LocalDate endDate;

    // TODO 5.3 — Inverse side của quan hệ N-N với Employee.
    // mappedBy = "projects" trỏ đúng tên field trong Employee (owning side).
    // Inverse side KHÔNG tạo bảng trung gian — chỉ để navigate ngược từ Project → Employee.
    // Không dùng cascade = ALL ở N-N vì tránh xóa nhầm entity phía bên kia:
    //   ví dụ xóa 1 Project không được kéo theo xóa Employee.
    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees = new HashSet<>();

    // Constructor không tham số — bắt buộc cho JPA
    public Project() {
    }

    public Project(String projectCode, String projectName, BigDecimal budget, LocalDate startDate) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.budget = budget;
        this.startDate = startDate;
    }

    public Project(String projectCode, String projectName, BigDecimal budget,
                   LocalDate startDate, LocalDate endDate) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Project{id=" + id
                + ", projectCode='" + projectCode + "'"
                + ", projectName='" + projectName + "'"
                + ", budget=" + budget
                + ", startDate=" + startDate
                + ", endDate=" + endDate
                + "}";
    }
}
