package fu.de190381.Chapter6.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "majors")
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 10, unique = true)
    private String code;   // VD: "CNTT"

    @Column(name = "name", nullable = false, length = 100)
    private String name;   // VD: "Công nghệ thông tin"

    // ========== Constructors ==========

    public Major() {}

    public Major(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // ========== Getters & Setters ==========

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return code;
    }
}
