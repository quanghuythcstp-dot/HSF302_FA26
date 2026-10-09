package fu.de190381.Chapter6.service;

import fu.de190381.Chapter6.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAll();

    /** Tìm kiếm theo keyword (tên hoặc email). Trả về tất cả nếu keyword rỗng */
    List<Student> search(String keyword);

    Optional<Student> findById(Long id);

    Student create(Student student);

    /** @return true nếu tìm thấy và cập nhật; false nếu không tồn tại id */
    boolean update(Long id, Student data);

    /** @return true nếu xoá được; false nếu không tồn tại id */
    boolean delete(Long id);

    /**
     * Kiểm tra email đã bị dùng chưa.
     * @param excludeId null khi thêm mới, = id hiện tại khi cập nhật
     */
    boolean isEmailTaken(String email, Long excludeId);

    List<String> getMajors();
}
