package fu.de190381.Chapter6.service.impl;

import fu.de190381.Chapter6.dto.StudentForm;
import fu.de190381.Chapter6.entity.Student;
import fu.de190381.Chapter6.repository.StudentRepository;
import fu.de190381.Chapter6.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)   // mặc định: mọi method chỉ đọc
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<Student> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        String kw = keyword.trim();
        return studentRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        kw, kw, Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    @Transactional                // ghi dữ liệu → bỏ readOnly
    public Student create(Student student) {
        student.setId(null);      // luôn INSERT, không bao giờ ghi đè bản ghi cũ
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student createFromForm(StudentForm form) {
        Student student = new Student(
                form.getName(), form.getEmail(),
                form.getAge(), form.getMajor(), form.getGpa());
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public boolean update(Long id, Student data) {
        return studentRepository.findById(id)
                .map(existing -> {
                    existing.setName(data.getName());
                    existing.setEmail(data.getEmail());
                    existing.setAge(data.getAge());
                    existing.setMajor(data.getMajor());
                    existing.setGpa(data.getGpa());
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional
    public boolean updateFromForm(Long id, StudentForm form) {
        return studentRepository.findById(id)
                .map(existing -> {
                    existing.setName(form.getName());
                    existing.setEmail(form.getEmail());
                    existing.setAge(form.getAge());
                    existing.setMajor(form.getMajor());
                    existing.setGpa(form.getGpa());
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (!studentRepository.existsById(id)) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean isEmailTaken(String email, Long excludeId) {
        if (email == null || email.isBlank()) return false;
        return excludeId == null
                ? studentRepository.existsByEmailIgnoreCase(email.trim())
                : studentRepository.existsByEmailIgnoreCaseAndIdNot(email.trim(), excludeId);
    }

    @Override
    public List<String> getMajors() {
        return List.of("CNTT", "KTPM", "HTTT", "ATTT", "MMT");
    }

    @Override
    public Page<Student> findPaged(String keyword, Pageable pageable) {
        String kw = (keyword == null || keyword.isBlank()) ? "" : keyword.trim();
        return studentRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(kw, kw, pageable);
    }
}
