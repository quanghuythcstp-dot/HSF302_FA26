package fu.de190381.dao;

import fu.de190381.pojo.Employee;
import fu.de190381.pojo.Project;
import fu.de190381.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

/**
 * DAO cho entity Employee.
 * Mỗi method tự mở/đóng EntityManager riêng — không dùng chung.
 */
public class EmployeeDAO {

    /**
     * Lưu mới một Employee (Department phải đã được persist trước).
     */
    public void save(Employee employee) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(employee);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Tìm Employee theo id. Trả về null nếu không tồn tại.
     */
    public Employee findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Lấy toàn bộ danh sách Employee.
     */
    public List<Employee> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Cập nhật Employee. Dùng merge() và gán lại kết quả.
     */
    public Employee update(Employee employee) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee merged = em.merge(employee);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Xóa Employee theo id.
     */
    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee employee = em.find(Employee.class, id);
            if (employee != null) {
                em.remove(employee);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * TODO 5.6 — Phân công Employee vào Project trong 1 transaction duy nhất.
     * Find cả 2 entity bằng cùng 1 EntityManager để chúng ở cùng persistence context,
     * đảm bảo Hibernate track thay đổi trên cả 2 phía khi commit.
     *
     * Dùng em.find() thay vì em.getReference() để phát hiện sớm nếu id không tồn tại.
     *
     * @param employeeId id của Employee cần phân công
     * @param projectId  id của Project cần tham gia
     * @throws IllegalArgumentException nếu Employee hoặc Project không tồn tại
     */
    public void assignEmployeeToProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Find cả 2 entity trong cùng 1 EntityManager → cùng persistence context
            Employee employee = em.find(Employee.class, employeeId);
            if (employee == null) {
                throw new IllegalArgumentException("Employee not found with id: " + employeeId);
            }

            Project project = em.find(Project.class, projectId);
            if (project == null) {
                throw new IllegalArgumentException("Project not found with id: " + projectId);
            }

            // Gọi helper method đồng bộ 2 chiều (TODO 5.5)
            // Hibernate sẽ tự INSERT vào bảng employee_project khi commit
            employee.assignToProject(project);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * TODO 5.9 — Gỡ Employee khỏi Project trong 1 transaction duy nhất.
     * Find cả 2 entity trong cùng 1 EntityManager, gọi unassignFromProject()
     * để đồng bộ 2 chiều.
     * Hibernate sẽ tự DELETE dòng tương ứng trong bảng employee_project khi commit.
     * Employee và Project gốc KHÔNG bị xóa.
     *
     * @param employeeId id của Employee cần gỡ
     * @param projectId  id của Project cần gỡ khỏi
     * @throws IllegalArgumentException nếu Employee hoặc Project không tồn tại
     */
    public void unassignEmployeeFromProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Employee employee = em.find(Employee.class, employeeId);
            if (employee == null) {
                throw new IllegalArgumentException("Employee not found with id: " + employeeId);
            }

            Project project = em.find(Project.class, projectId);
            if (project == null) {
                throw new IllegalArgumentException("Project not found with id: " + projectId);
            }

            // Gọi helper method gỡ 2 chiều (TODO 5.9)
            // Hibernate sẽ tự DELETE khỏi bảng employee_project khi commit
            employee.unassignFromProject(project);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * TODO 5.10 — Tìm các Employee active đang tham gia nhiều hơn 1 project.
     * JPQL:
     *   SELECT e FROM Employee e WHERE e.active = true AND SIZE(e.projects) > 1
     *
     * SIZE() là hàm JPQL trả về số phần tử trong collection,
     * không cần JOIN hay GROUP BY — Hibernate tự sinh subquery đếm.
     *
     * @return danh sách Employee active tham gia hơn 1 project
     */
    public List<Employee> findActiveEmployeesInMultipleProjects() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Employee e WHERE e.active = true AND SIZE(e.projects) > 1",
                    Employee.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
