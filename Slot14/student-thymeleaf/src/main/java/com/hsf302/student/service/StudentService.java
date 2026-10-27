package com.hsf302.student.service;

import com.hsf302.student.entity.Student;
import com.hsf302.student.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll(Sort.by("id"));
    }

    public List<Student> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        String kw = keyword.trim();
        return studentRepository
                .findByStudentCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrderByIdAsc(kw, kw);
    }

    public List<Student> searchAndSort(String keyword, String major, String sortField, Sort.Direction direction) {
        Sort sort = Sort.by(direction, sortField);
        boolean hasKeyword = keyword != null && !keyword.isBlank();
        boolean hasMajor   = major != null && !major.isBlank();

        if (hasMajor && hasKeyword) {
            String kw = keyword.trim();
            return studentRepository
                    .findByMajorAndStudentCodeContainingIgnoreCaseOrMajorAndFullNameContainingIgnoreCase(
                            major, kw, major, kw, sort);
        } else if (hasMajor) {
            return studentRepository.findByMajor(major, sort);
        } else if (hasKeyword) {
            String kw = keyword.trim();
            return studentRepository
                    .findByStudentCodeContainingIgnoreCaseOrFullNameContainingIgnoreCase(kw, kw, sort);
        } else {
            return studentRepository.findAll(sort);
        }
    }

    /** Phân trang: page bắt đầu từ 0, pageSize = 3 */
    public static final int PAGE_SIZE = 3;

    public Page<Student> searchPage(String keyword, String major, String sortField,
                                    Sort.Direction direction, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(direction, sortField));
        boolean hasKeyword = keyword != null && !keyword.isBlank();
        boolean hasMajor   = major != null && !major.isBlank();

        if (hasMajor && hasKeyword) {
            String kw = keyword.trim();
            return studentRepository
                    .findByMajorAndStudentCodeContainingIgnoreCaseOrMajorAndFullNameContainingIgnoreCase(
                            major, kw, major, kw, pageable);
        } else if (hasMajor) {
            return studentRepository.findByMajor(major, pageable);
        } else if (hasKeyword) {
            String kw = keyword.trim();
            return studentRepository
                    .findByStudentCodeContainingIgnoreCaseOrFullNameContainingIgnoreCase(kw, kw, pageable);
        } else {
            return studentRepository.findAll(pageable);
        }
    }
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    /** id == null → INSERT, id != null → UPDATE */
    @Transactional
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    /** Kiểm tra trùng mã SV, bỏ qua chính sinh viên đang sửa (excludeId) */
    public boolean isStudentCodeDuplicated(String code, Long excludeId) {
        if (code == null || code.isBlank()) return false;
        String c = code.trim();
        return excludeId == null
                ? studentRepository.existsByStudentCodeIgnoreCase(c)
                : studentRepository.existsByStudentCodeIgnoreCaseAndIdNot(c, excludeId);
    }

    public double averageGpa(List<Student> list) {
        return list.stream()
                .map(Student::getGpa)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }
}
