package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    // TODO 6
    @Override
    public long count() {
        return studentRepository.count();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    // TODO 7
    @Override
    public List<Student> findAllOrderByGpaDesc() {
        return studentRepository.findAll(Sort.by(Sort.Direction.DESC, "gpa"));
    }

    @Override
    public Page<Student> findPage(int pageIndex, int size, String sortField) {
        if (pageIndex < 0 || size <= 0) {
            throw new IllegalArgumentException("pageIndex phải >= 0 và size phải > 0");
        }
        Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(sortField).ascending());
        return studentRepository.findAll(pageable);
    }

    // TODO 8
    @Override
    public Optional<Student> findByStudentCode(String code) {
        return studentRepository.findByStudentCode(code);
    }

    @Override
    public boolean isEmailExisted(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public long countActive() {
        return studentRepository.countByActiveTrue();
    }

    // TODO 9
    @Override
    public List<Student> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) return List.of();
        return studentRepository.findByFullNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Student> findByEmailDomain(String domain) {
        if (!domain.startsWith("@")) domain = "@" + domain;
        return studentRepository.findByEmailEndingWith(domain);
    }

    @Override
    public List<Student> findWithoutEmail() {
        return studentRepository.findByEmailIsNull();
    }

    // TODO 10
    @Override
    public List<Student> findByGpaRange(double min, double max) {
        if (min > max) throw new IllegalArgumentException("min phải <= max");
        return studentRepository.findByGpaBetweenOrderByGpaDesc(min, max);
    }

    @Override
    public List<Student> findActiveByGender(Gender gender) {
        return studentRepository.findByGenderAndActiveTrue(gender);
    }

    @Override
    public List<Student> findBornAfter(LocalDate date) {
        return studentRepository.findByDobAfter(date);
    }

    // TODO 11
    @Override
    public List<Student> findByDepartment(String deptCode) {
        return studentRepository.findByDepartmentCodeOrderByFullNameAsc(deptCode);
    }

    @Override
    public long countByDepartment(String deptCode) {
        return studentRepository.countByDepartmentCode(deptCode);
    }

    @Override
    public List<Student> findTop3ByGpa() {
        return studentRepository.findTop3ByOrderByGpaDesc();
    }

    // TODO 12
    @Override
    public List<Student> findGoodStudents(String deptCode, double minGpa) {
        return studentRepository.findGoodStudents(deptCode, minGpa);
    }

    // TODO 13
    @Override
    public List<Student> searchByKeyword(String kw) {
        if (kw == null || kw.isBlank()) return List.of();
        return studentRepository.searchByKeyword(kw);
    }

    // TODO 15
    @Override
    public List<Student> findAboveAverageGpa() {
        return studentRepository.findAboveAverageGpa();
    }

    // TODO 17
    @Override
    public List<Student> findTopNInDepartment(String deptCode, int n) {
        if (n <= 0) throw new IllegalArgumentException("n phải > 0");
        return studentRepository.findTopNInDepartment(deptCode, n);
    }

    // TODO 18
    @Override
    public List<com.hsf302.ch4.dto.StudentSummary> getActiveSummaries() {
        return studentRepository.findActiveSummaries();
    }

    // TODO 19
    @Override
    public Page<Student> findActiveByDepartment(String deptCode, int pageIndex, int size) {
        if (pageIndex < 0 || size <= 0) {
            throw new IllegalArgumentException("pageIndex phải >= 0 và size phải > 0");
        }
        Pageable pageable = PageRequest.of(pageIndex, size);
        return studentRepository.findActiveByDepartment(deptCode, pageable);
    }

    // TODO 24 (Bonus)
    @Override
    public List<Student> search(String kw, String deptCode, Double minGpa, Boolean active) {
        org.springframework.data.jpa.domain.Specification<Student> spec =
                com.hsf302.ch4.specification.StudentSpecs.nameContains(kw)
                .and(com.hsf302.ch4.specification.StudentSpecs.inDepartment(deptCode))
                .and(com.hsf302.ch4.specification.StudentSpecs.gpaAtLeast(minGpa))
                .and(com.hsf302.ch4.specification.StudentSpecs.isActive(active));
        return studentRepository.findAll(spec, Sort.by("fullName").ascending());
    }

    // TODO 20
    @Override
    @Transactional
    public Student updateGpa(String code, double newGpa) {
        if (newGpa < 0 || newGpa > 4) throw new IllegalArgumentException("GPA phải trong khoảng [0, 4]");
        Student s = studentRepository.findByStudentCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + code));
        s.setGpa(newGpa);
        return s;
    }

    // TODO 21
    @Override
    @Transactional
    public int deactivateLowGpa(double threshold) {
        return studentRepository.deactivateLowGpa(threshold);
    }

    // TODO 23
    @Override
    @Transactional
    public long deleteInactiveStudents() {
        return studentRepository.deleteByActiveFalse();
    }
}
