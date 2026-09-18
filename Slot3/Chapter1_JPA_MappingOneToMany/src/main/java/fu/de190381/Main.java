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

        // ============================================================
        // TODO 2.8 — Tái hiện N+1 Query Problem
        // Bật hibernate.show_sql=true trong persistence.xml để đếm SQL
        // Kết quả: 1 câu SELECT departments + N câu SELECT employees
        // (mỗi lần truy cập .getEmployees() của 1 dept khác nhau = 1 query)
        // ============================================================
        System.out.println("\n=== TODO 2.8: N+1 Query Problem (xem SQL log) ===");

        // Thêm 1 department IT để có N = 2 departments -> tổng 1+2 = 3 câu SQL
        Department it = new Department("IT", "Ho Chi Minh");
        Employee e4 = new Employee("dd.pham@company.com", "Pham Van D", Gender.MALE,
                new BigDecimal("20000000"), LocalDate.of(2020, 5, 20));
        it.addEmployee(e4);
        departmentDAO.save(it);

        System.out.println("--- Bat dau N+1: 1 cau SELECT departments + N cau SELECT employees ---");
        // findAll() = 1 SQL
        // d.getEmployees().size() moi dept = 1 SQL (LAZY load)
        // Tong: 1 + N cau (N = 2 departments -> 3 cau SQL)
        for (Department d : departmentDAO.findAll()) {
            System.out.println("Dept: " + d.getName()
                    + " | So nhan vien: " + d.getEmployees().size());
        }
        System.out.println("--- Ket thuc N+1: tong so cau SQL = 1 + N = 3 cau ---");

        JPAUtil.close();
    }
}
