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
        Employee emp = new Employee(
                "Nguyen Van A",
                "nguyenvana@fpt.edu.vn",
                new BigDecimal("15000000"),
                Gender.MALE,
                LocalDate.of(2022, 3, 1)
        );
        dao.save(emp);
        System.out.println("Da tao employee voi id = " + emp.getId());
        System.out.println("Ket qua: " + emp);

        // ===== BUOC 2: READ =====
        System.out.println("\n----- READ (findById) -----");
        Employee found = dao.findById(emp.getId());
        System.out.println("Doc lai theo id = " + emp.getId() + ": " + found);

        System.out.println("\n----- READ (findAll) -----");
        List<Employee> all = dao.findAll();
        System.out.println("Tong so employee trong DB: " + all.size());
        all.forEach(e -> System.out.println("  " + e));

        // ===== BUOC 3: UPDATE =====
        System.out.println("\n----- UPDATE -----");
        found.setSalary(new BigDecimal("17000000"));
        found.setFullName("Nguyen Van A (Updated)");
        Employee updated = dao.update(found);
        System.out.println("Sau update: " + updated);

        System.out.println("\n----- READ LAI SAU UPDATE (kiem chung) -----");
        Employee reChecked = dao.findById(emp.getId());
        System.out.println("Kiem tra lai: " + reChecked);
        System.out.println("Salary moi: " + reChecked.getSalary() + " (ky vong: 17000000)");

        // ===== BUOC 4: DELETE =====
        System.out.println("\n----- DELETE -----");
        dao.delete(emp.getId());
        System.out.println("Da xoa employee co id = " + emp.getId());

        System.out.println("\n----- READ LAI SAU DELETE (kiem chung) -----");
        Employee afterDelete = dao.findById(emp.getId());
        if (afterDelete == null) {
            System.out.println("Ket qua: null — da xoa thanh cong (dung ky vong)");
        } else {
            System.out.println("LOI: van con trong DB — xoa that bai!");
        }

        System.out.println("\n========== KET THUC DEMO CRUD ==========");
    }
}
