package fu.de190381;

import fu.de190381.dao.EmployeeDAO;
import fu.de190381.pojo.Employee;
import fu.de190381.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        System.out.println("========== TODO 0.8: DEMO CRUD DAY DU ==========\n");

        // ===== BUOC 1: CREATE =====
        System.out.println("----- CREATE -----");

        // [Lifecycle] emp: NEW/TRANSIENT — vua "new", chua lien quan DB hay EntityManager
        Employee emp = new Employee(
                "Nguyen Van A",
                "nguyenvana@fpt.edu.vn",
                new BigDecimal("15000000"),
                Gender.MALE,
                LocalDate.of(2022, 3, 1)
        );

        dao.save(emp);
        // [Lifecycle] BEN TRONG save():
        //   - Truoc persist()  : emp TRANSIENT
        //   - Sau persist()    : emp MANAGED — Hibernate theo doi thay doi
        //   - Sau commit()     : INSERT duoc thuc thi, emp.getId() co gia tri
        //   - Sau em.close()   : emp DETACHED — EntityManager da dong

        System.out.println("Da tao employee voi id = " + emp.getId());
        System.out.println("Ket qua: " + emp);

        // ===== BUOC 2: READ =====
        System.out.println("\n----- READ (findById) -----");

        // [Lifecycle] found: MANAGED ben trong findById(), DETACHED ngay khi method return
        Employee found = dao.findById(emp.getId());
        System.out.println("Doc lai theo id = " + emp.getId() + ": " + found);

        System.out.println("\n----- READ (findAll) -----");

        // [Lifecycle] moi entity trong list: DETACHED ngay khi findAll() return
        List<Employee> all = dao.findAll();
        System.out.println("Tong so employee trong DB: " + all.size());
        all.forEach(e -> System.out.println("  " + e));

        // ===== BUOC 3: UPDATE =====
        System.out.println("\n----- UPDATE -----");

        // [Lifecycle] found dang DETACHED — sua field KHONG tu dong sync DB
        found.setSalary(new BigDecimal("17000000"));
        found.setFullName("Nguyen Van A (Updated)");

        // [Lifecycle] BEN TRONG update():
        //   - merge(found): copy gia tri tu found(DETACHED) sang "merged"(MANAGED)
        //   - found van DETACHED sau merge()
        //   - Sau commit(): thay doi flush xuong DB
        //   - Sau em.close(): merged tro thanh DETACHED
        //   => PHAI dung "updated" (gia tri tra ve), khong dung "found" cu
        Employee updated = dao.update(found);
        System.out.println("Sau update: " + updated);

        System.out.println("\n----- READ LAI SAU UPDATE (kiem chung) -----");

        // [Lifecycle] reChecked: DETACHED ngay khi findById() return
        Employee reChecked = dao.findById(emp.getId());
        System.out.println("Kiem tra lai: " + reChecked);
        System.out.println("Salary moi: " + reChecked.getSalary() + " (ky vong: 17000000)");

        // ===== BUOC 4: DELETE =====
        System.out.println("\n----- DELETE -----");

        // [Lifecycle] BEN TRONG delete():
        //   - find(): entity MANAGED
        //   - remove(): entity chuyen sang REMOVED
        //   - Sau commit(): DELETE thuc thi, entity bien mat khoi DB
        dao.delete(emp.getId());
        System.out.println("Da xoa employee co id = " + emp.getId());

        System.out.println("\n----- READ LAI SAU DELETE (kiem chung) -----");

        // [Lifecycle] afterDelete = null: entity da bi xoa, khong con o bat ky trang thai nao
        Employee afterDelete = dao.findById(emp.getId());
        if (afterDelete == null) {
            System.out.println("Ket qua: null — da xoa thanh cong (dung ky vong)");
        } else {
            System.out.println("LOI: van con trong DB — xoa that bai!");
        }

        System.out.println("\n========== KET THUC DEMO CRUD ==========");

        // ===== TODO 0.9: KIEM CHUNG UNIQUE CONSTRAINT TREN EMAIL =====
        System.out.println("\n========== TODO 0.9: DEMO UNIQUE EMAIL CONSTRAINT ==========\n");

        // [Lifecycle] dup1, dup2: NEW/TRANSIENT
        Employee dup1 = new Employee(
                "User 1", "trung@fpt.edu.vn",
                new BigDecimal("10000000"), Gender.FEMALE, LocalDate.of(2023, 1, 15)
        );
        Employee dup2 = new Employee(
                "User 2", "trung@fpt.edu.vn", // cung email — vi pham UNIQUE
                new BigDecimal("11000000"), Gender.MALE, LocalDate.of(2023, 6, 1)
        );

        // [Lifecycle] dup1: TRANSIENT -> MANAGED -> DETACHED
        dao.save(dup1);
        System.out.println("Luu dup1 thanh cong, id = " + dup1.getId());

        // [Lifecycle] dup2: persist() bi rollback vi unique constraint
        //   => dup2 van TRANSIENT, khong bao gio vao duoc DB
        try {
            dao.save(dup2);
            System.out.println("LOI: Khong thay exception nhu ky vong!");
        } catch (RuntimeException ex) {
            System.out.println("Da bat duoc loi trung email nhu ky vong!");
            System.out.println("Exception type : " + ex.getClass().getSimpleName());
            System.out.println("Message        : " + ex.getMessage());
        }

        dao.delete(dup1.getId());
        System.out.println("\nDa xoa dup1 sau khi demo.");
        System.out.println("\n========== KET THUC DEMO UNIQUE CONSTRAINT ==========");
    }
}
