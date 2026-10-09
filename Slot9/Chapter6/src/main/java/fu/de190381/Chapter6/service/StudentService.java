package fu.de190381.Chapter6.service;

import fu.de190381.Chapter6.dto.StudentForm;
import fu.de190381.Chapter6.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAll();

    /** Tìm kiếm theo keyword (tên hoặc email). Trả về tất cả nếu keyword rỗng */
    List<Student> search(String keyword);

    /** Phân trang + tìm kiếm */
    Page<Student> findPaged(String keyword, Pageable pageable);

    Optional<Student> findById(Long id);

    Student create(Student student);

    /** Tạo sinh viên từ DTO */
    Student createFromForm(StudentForm form);

    /** @return true nếu tìm thấy và cập nhật; false nếu không tồn tại id */
    boolean update(Long id, Student data);

    /** Cập nhật từ DTO */
    boolean updateFromForm(Long id, StudentForm form);

    /** @return true nếu xoá được; false nếu không tồn tại id */
    boolean delete(Long id);

    /**
     * Kiểm tra email đã bị dùng chưa.
     * @param excludeId null khi thêm mới, = id hiện tại khi cập nhật
     */
    boolean isEmailTaken(String email, Long excludeId);

    List<String> getMajors();

    /** Trả về danh sách entity Major (dùng cho dropdown bài 5) */
    List<fu.de190381.Chapter6.entity.Major> getMajorEntities();
}
