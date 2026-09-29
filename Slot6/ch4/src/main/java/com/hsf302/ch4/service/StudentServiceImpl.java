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
}
