package fu.de190381.pojo;

import jakarta.persistence.*;

/**
 * Entity đại diện cho phòng ban trong công ty.
 * Quan hệ 1-N với Employee (inverse side).
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

    @Override
    public String toString() {
        return "Department{id=" + id + ", name='" + name + "', location='" + location + "'}";
    }
}
