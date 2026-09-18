package fu.de190381.pojo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity đại diện cho phòng ban trong công ty.
 * Inverse side của quan hệ 1-N với Employee.
 */
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String location;

    // Inverse side: mappedBy trỏ đúng tên field "department" trong Employee
    // cascade = ALL: persist/merge/remove Department sẽ áp dụng luôn cho Employee
    // orphanRemoval = true: xóa Employee khỏi list thì xóa luôn trong DB
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    // Constructor không tham số — bắt buộc cho JPA
    public Department() {
    }

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Department{id=" + id + ", name='" + name + "', location='" + location + "'}";
    }
}
