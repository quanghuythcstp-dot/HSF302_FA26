package fu.de190381;

import fu.de190381.dao.DepartmentDAO;
import fu.de190381.pojo.Department;
import fu.de190381.pojo.Employee;
import fu.de190381.pojo.Gender;
import fu.de190381.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        DepartmentDAO departmentDAO = new DepartmentDAO();

        // ============================================================
        // TODO 2.7 — Tạo 1 Department + 3 Employee, lưu xuống DB
        // ============================================================
        System.out.println("=== TODO 2.7: Save Department + 3 Employees ===");

        Department marketing = new Department("Marketing", "Ha Noi");

        Employee e1 = new Employee("aa.nguyen@company.com", "Nguyen Van A", Gender.MALE,
                new BigDecimal("15000000"), LocalDate.of(2022, 1, 10));
        Employee e2 = new Employee("bb.tran@company.com", "Tran Thi B", Gender.FEMALE,
                new BigDecimal("18000000"), LocalDate.of(2021, 6, 1));
        Employee e3 = new Employee("cc.le@company.com", "Le Van C", Gender.OTHER,
                new BigDecimal("12000000"), LocalDate.of(2023, 3, 15));

        // Dùng helper method addEmployee() để đồng bộ 2 chiều
        marketing.addEmployee(e1);
        marketing.addEmployee(e2);
        marketing.addEmployee(e3);

        // Chỉ persist Department — cascade = ALL tự lo phần Employee
        departmentDAO.save(marketing);
        System.out.println("Da luu Department, id = " + marketing.getId());

        // TODO 2.6 — Tìm lại kèm employees bằng JOIN FETCH
        // Không bị LazyInitializationException dù EntityManager đã đóng
        System.out.println("\n=== TODO 2.6: findByIdWithEmployees (JOIN FETCH) ===");
        Department found = departmentDAO.findByIdWithEmployees(marketing.getId());
        System.out.println("Phong ban: " + found.getName() + " - " + found.getLocation());
        for (Employee e : found.getEmployees()) {
            System.out.println("  - " + e);
        }

        JPAUtil.close();
    }
}
