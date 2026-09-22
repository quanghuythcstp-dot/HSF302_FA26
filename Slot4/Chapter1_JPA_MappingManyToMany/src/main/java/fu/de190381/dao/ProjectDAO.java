package fu.de190381.dao;

import fu.de190381.pojo.Project;
import fu.de190381.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

/**
 * DAO cho entity Project.
 * Mỗi method tự mở/đóng EntityManager riêng — không dùng chung.
 */
public class ProjectDAO {

    /**
     * Lưu mới một Project xuống DB.
     * Không dùng cascade sang Employee ở N-N để tránh xóa nhầm entity.
     */
    public void save(Project project) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(project);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Tìm Project theo id. Trả về null nếu không tồn tại.
     */
    public Project findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Project.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Lấy toàn bộ danh sách Project.
     */
    public List<Project> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Project p", Project.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * TODO 5.8 — Đếm số nhân viên active và tính tổng salary theo từng project.
     * JPQL:
     *   SELECT p.projectName, COUNT(e), SUM(e.salary)
     *   FROM Project p JOIN p.employees e
     *   WHERE e.active = true
     *   GROUP BY p.projectName
     *
     * Trả về List<Object[]>, mỗi phần tử gồm:
     *   [0] String  — projectName
     *   [1] Long    — số nhân viên active
     *   [2] BigDecimal — tổng salary
     */
    public List<Object[]> countActiveEmployeesAndSalaryByProject() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT p.projectName, COUNT(e), SUM(e.salary) " +
                    "FROM Project p JOIN p.employees e " +
                    "WHERE e.active = true " +
                    "GROUP BY p.projectName",
                    Object[].class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
