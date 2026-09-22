package fu.de190381;

import fu.de190381.dao.EmployeeDAO;
import fu.de190381.dao.ProjectDAO;
import fu.de190381.pojo.Employee;
import fu.de190381.pojo.Gender;
import fu.de190381.pojo.Project;
import fu.de190381.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        EmployeeDAO employeeDAO = new EmployeeDAO();
        ProjectDAO projectDAO = new ProjectDAO();

        // ============================================================
        // TODO 5.7 — Tạo 3 Employee, 2 Project, phân công chéo
        // NV1 (An): Project A + Project B
        // NV2 (Binh): Project B
        // NV3 (Chi): Project A
        // ============================================================
        System.out.println("=== TODO 5.7: ManyToMany Demo ===\n");

        // --- Tạo 3 Employee với đủ salary, hireDate, gender, active ---
        Employee nv1 = new Employee(
                "an.nguyen@company.com", "Nguyen Van An",
                Gender.MALE, new BigDecimal("20000000"), LocalDate.of(2021, 3, 1));

        Employee nv2 = new Employee(
                "binh.tran@company.com", "Tran Thi Binh",
                Gender.FEMALE, new BigDecimal("18000000"), LocalDate.of(2022, 7, 15));

        Employee nv3 = new Employee(
                "chi.le@company.com", "Le Van Chi",
                Gender.OTHER, new BigDecimal("15000000"), LocalDate.of(2023, 1, 20));
        // active mặc định true theo constructor

        // --- Tạo 2 Project ---
        Project projectA = new Project(
                "PRJ-A", "Project Alpha",
                new BigDecimal("500000000"), LocalDate.of(2024, 1, 1));

        Project projectB = new Project(
                "PRJ-B", "Project Beta",
                new BigDecimal("300000000"), LocalDate.of(2024, 6, 1),
                LocalDate.of(2025, 6, 1)); // có endDate

        // --- Lưu Employee và Project vào DB trước khi phân công ---
        System.out.println("--- Luu Employee va Project ---");
        employeeDAO.save(nv1);
        employeeDAO.save(nv2);
        employeeDAO.save(nv3);
        projectDAO.save(projectA);
        projectDAO.save(projectB);
        System.out.println("Da luu: " + nv1.getFullName() + " (id=" + nv1.getId() + ")");
        System.out.println("Da luu: " + nv2.getFullName() + " (id=" + nv2.getId() + ")");
        System.out.println("Da luu: " + nv3.getFullName() + " (id=" + nv3.getId() + ")");
        System.out.println("Da luu: " + projectA.getProjectName() + " (id=" + projectA.getId() + ")");
        System.out.println("Da luu: " + projectB.getProjectName() + " (id=" + projectB.getId() + ")");

        // --- Phân công chéo dùng EmployeeDAO.assignEmployeeToProject() ---
        System.out.println("\n--- Phan cong cheo ---");

        // NV1 tham gia Project A và Project B
        employeeDAO.assignEmployeeToProject(nv1.getId(), projectA.getId());
        employeeDAO.assignEmployeeToProject(nv1.getId(), projectB.getId());
        System.out.println(nv1.getFullName() + " -> Project A, Project B");

        // NV2 tham gia Project B
        employeeDAO.assignEmployeeToProject(nv2.getId(), projectB.getId());
        System.out.println(nv2.getFullName() + " -> Project B");

        // NV3 tham gia Project A
        employeeDAO.assignEmployeeToProject(nv3.getId(), projectA.getId());
        System.out.println(nv3.getFullName() + " -> Project A");

        // --- In danh sách project của từng nhân viên (dùng JPQL JOIN FETCH) ---
        System.out.println("\n--- Danh sach project cua tung nhan vien ---");
        printEmployeeProjects(nv1.getId());
        printEmployeeProjects(nv2.getId());
        printEmployeeProjects(nv3.getId());

        // --- Kiểm tra ngược lại: employee của từng project ---
        System.out.println("\n--- Danh sach nhan vien cua tung project ---");
        printProjectEmployees(projectA.getId());
        printProjectEmployees(projectB.getId());

        // ============================================================
        // TODO 5.9 — unassignFromProject: gỡ NV2 (Binh) khỏi Project B
        // Xác nhận bảng employee_project mất đúng 1 dòng,
        // Employee và Project gốc vẫn còn nguyên.
        // ============================================================
        System.out.println("\n=== TODO 5.9: Unassign NV2 (Binh) khoi Project B ===\n");

        // Trước khi gỡ: in số dòng trong employee_project liên quan đến NV2
        System.out.println("Truoc khi go:");
        printEmployeeProjects(nv2.getId());

        // Gỡ NV2 khỏi Project B
        employeeDAO.unassignEmployeeFromProject(nv2.getId(), projectB.getId());
        System.out.println("\nDa go " + nv2.getFullName() + " khoi " + projectB.getProjectName());

        // Sau khi gỡ: xác nhận NV2 không còn project nào
        System.out.println("\nSau khi go:");
        // NV2 đã không còn project → dùng findById để kiểm tra
        jakarta.persistence.EntityManager emCheck = JPAUtil.getEntityManager();
        try {
            java.util.List<Employee> emps = emCheck.createQuery(
                    "SELECT e FROM Employee e LEFT JOIN FETCH e.projects WHERE e.id = :id",
                    Employee.class)
                    .setParameter("id", nv2.getId())
                    .getResultList();
            if (!emps.isEmpty()) {
                Employee binh = emps.get(0);
                System.out.println("  " + binh.getFullName() + " hien tham gia: "
                        + (binh.getProjects().isEmpty() ? "(khong co project nao)" : binh.getProjects()));
            }
        } finally {
            emCheck.close();
        }

        // Xác nhận Project B vẫn còn (không bị xóa theo)
        Project checkProjectB = projectDAO.findById(projectB.getId());
        System.out.println("  " + checkProjectB.getProjectName() + " van con trong DB (id=" + checkProjectB.getId() + ")");

        // Xác nhận Project B vẫn còn NV1 (An)
        System.out.println("\nProject B sau khi go NV2:");
        printProjectEmployees(projectB.getId());

        // ============================================================
        // TODO 5.10 — JPQL tìm Employee active tham gia hơn 1 project
        // ============================================================
        System.out.println("\n=== TODO 5.10: Tim nhan vien active tham gia nhieu hon 1 project ===\n");

        // Sau khi gỡ NV2 khỏi Project B ở TODO 5.9:
        // - NV1 (An): còn Project A + B → SIZE = 2 → thỏa điều kiện
        // - NV2 (Binh): đã bị gỡ khỏi B → SIZE = 0 → không thỏa
        // - NV3 (Chi): chỉ có Project A → SIZE = 1 → không thỏa
        java.util.List<Employee> multiProjectEmps = employeeDAO.findActiveEmployeesInMultipleProjects();
        if (multiProjectEmps.isEmpty()) {
            System.out.println("  Khong co nhan vien nao tham gia nhieu hon 1 project.");
        } else {
            System.out.println("  Nhan vien active tham gia nhieu hon 1 project:");
            multiProjectEmps.forEach(e ->
                System.out.println("  - " + e.getFullName() + " (email: " + e.getEmail() + ")"));
        }

        // ============================================================
        // TODO 5.8 — JPQL đếm số nhân viên active và tổng salary theo project
        // ============================================================
        System.out.println("\n=== TODO 5.8: Dem nhan vien active va tong salary theo project ===\n");

        java.util.List<Object[]> stats = projectDAO.countActiveEmployeesAndSalaryByProject();
        System.out.printf("%-20s %15s %20s%n", "Project", "So NV active", "Tong Salary");
        System.out.println("-".repeat(58));
        for (Object[] row : stats) {
            String projectName  = (String) row[0];
            Long count          = (Long) row[1];
            java.math.BigDecimal totalSalary = (java.math.BigDecimal) row[2];
            System.out.printf("%-20s %15d %20s%n", projectName, count, totalSalary);
        }

        JPAUtil.close();
    }

    /**
     * In danh sách project của 1 nhân viên (dùng JOIN FETCH tránh LazyInit).
     */
    private static void printEmployeeProjects(Long employeeId) {
        jakarta.persistence.EntityManager em = JPAUtil.getEntityManager();
        try {
            Employee emp = em.createQuery(
                    "SELECT e FROM Employee e JOIN FETCH e.projects WHERE e.id = :id",
                    Employee.class)
                    .setParameter("id", employeeId)
                    .getSingleResult();
            System.out.print("  " + emp.getFullName() + " tham gia: ");
            emp.getProjects().forEach(p -> System.out.print("[" + p.getProjectName() + "] "));
            System.out.println();
        } finally {
            em.close();
        }
    }

    /**
     * In danh sách nhân viên của 1 project (dùng JOIN FETCH tránh LazyInit).
     */
    private static void printProjectEmployees(Long projectId) {
        jakarta.persistence.EntityManager em = JPAUtil.getEntityManager();
        try {
            Project proj = em.createQuery(
                    "SELECT p FROM Project p JOIN FETCH p.employees WHERE p.id = :id",
                    Project.class)
                    .setParameter("id", projectId)
                    .getSingleResult();
            System.out.print("  " + proj.getProjectName() + " co nhan vien: ");
            proj.getEmployees().forEach(e -> System.out.print("[" + e.getFullName() + "] "));
            System.out.println();
        } finally {
            em.close();
        }
    }
}
