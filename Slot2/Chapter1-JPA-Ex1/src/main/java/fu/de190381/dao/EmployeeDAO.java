package fu.de190381.dao;

import fu.de190381.pojo.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EmployeeDAO {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hsf302PU");

    // ---------- CREATE (TODO 0.3) ----------

    /**
     * Luu mot Employee moi xuong DB trong 1 transaction.
     * Sau khi method return, e.getId() != null chung to entity da duoc INSERT.
     */
    public void save(Employee e) {
        // Truoc dong nay: e dang o trang thai NEW/TRANSIENT
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(e); // e chuyen sang MANAGED, se duoc INSERT khi commit
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close(); // sau dong nay: e tro thanh DETACHED
        }
    }
}
