package com.hsf302.student.repository;

import com.hsf302.student.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // TODO 19.1: Tìm theo mã SV HOẶC họ tên chứa từ khóa, không phân biệt hoa thường, sắp xếp theo id tăng dần
    List<Student> findByStudentCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrderByIdAsc(
            String studentCode, String fullName);

    // Tìm kiếm có hỗ trợ sắp xếp động (dùng cho bài mở rộng)
    List<Student> findByStudentCodeContainingIgnoreCaseOrFullNameContainingIgnoreCase(
            String studentCode, String fullName, Sort sort);

    // Lọc theo ngành + tìm kiếm keyword + sắp xếp động
    List<Student> findByMajorAndStudentCodeContainingIgnoreCaseOrMajorAndFullNameContainingIgnoreCase(
            String major1, String studentCode, String major2, String fullName, Sort sort);

    // Lọc theo ngành (không có keyword) + sắp xếp động
    List<Student> findByMajor(String major, Sort sort);

    // ===== Phân trang (bài mở rộng 3) =====
    // Tìm kiếm keyword có phân trang
    Page<Student> findByStudentCodeContainingIgnoreCaseOrFullNameContainingIgnoreCase(
            String studentCode, String fullName, Pageable pageable);

    // Lọc theo ngành có phân trang
    Page<Student> findByMajor(String major, Pageable pageable);

    // Lọc theo ngành + keyword có phân trang
    Page<Student> findByMajorAndStudentCodeContainingIgnoreCaseOrMajorAndFullNameContainingIgnoreCase(
            String major1, String studentCode, String major2, String fullName, Pageable pageable);

    // TODO 19.2: Kiểm tra mã SV đã tồn tại (dùng khi THÊM MỚI)
    boolean existsByStudentCodeIgnoreCase(String studentCode);

    // TODO 19.3: Kiểm tra mã SV đã tồn tại ở sinh viên KHÁC (dùng khi CẬP NHẬT – bỏ qua chính nó)
    boolean existsByStudentCodeIgnoreCaseAndIdNot(String studentCode, Long id);
}
