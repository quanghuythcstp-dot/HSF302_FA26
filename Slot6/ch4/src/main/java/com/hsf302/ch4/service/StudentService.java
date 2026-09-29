package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.StudentSummary;
import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    // TODO 6
    long count();
    Optional<Student> findById(Long id);

    // TODO 7
    List<Student> findAllOrderByGpaDesc();
    Page<Student> findPage(int pageIndex, int size, String sortField);

    // TODO 8
    Optional<Student> findByStudentCode(String code);
    boolean isEmailExisted(String email);
    long countActive();

    // TODO 9
    List<Student> searchByName(String keyword);
    List<Student> findByEmailDomain(String domain);
    List<Student> findWithoutEmail();

    // TODO 10
    List<Student> findByGpaRange(double min, double max);
    List<Student> findActiveByGender(Gender gender);
    List<Student> findBornAfter(LocalDate date);

    // TODO 11
    List<Student> findByDepartment(String deptCode);
    long countByDepartment(String deptCode);
    List<Student> findTop3ByGpa();

    // TODO 12
    List<Student> findGoodStudents(String deptCode, double minGpa);

    // TODO 13
    List<Student> searchByKeyword(String kw);

    // TODO 14 (ở DepartmentService)

    // TODO 15
    List<Student> findAboveAverageGpa();

    // TODO 16 (ở DepartmentService)

    // TODO 17
    List<Student> findTopNInDepartment(String deptCode, int n);

    // TODO 18
    List<StudentSummary> getActiveSummaries();

    // TODO 19
    Page<Student> findActiveByDepartment(String deptCode, int pageIndex, int size);

    // TODO 24 (Bonus)
    List<Student> search(String kw, String deptCode, Double minGpa, Boolean active);

    // TODO 20
    Student updateGpa(String code, double newGpa);

    // TODO 21
    int deactivateLowGpa(double threshold);
}
