package fu.de190381.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utility class quản lý EntityManagerFactory dùng chung cho toàn ứng dụng.
 * EMF rất nặng (tốn tài nguyên khởi tạo) nên chỉ tạo 1 lần — khai báo static final.
 * EntityManager nhẹ hơn, tạo mới trong mỗi thao tác DAO.
 */
public class JPAUtil {

    // Tên phải khớp với persistence-unit name trong persistence.xml
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hsf302FU");

    /**
     * Tạo một EntityManager mới cho mỗi thao tác.
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Đóng EntityManagerFactory khi ứng dụng kết thúc.
     * Gọi ở cuối main() hoặc trong shutdown hook.
     */
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
