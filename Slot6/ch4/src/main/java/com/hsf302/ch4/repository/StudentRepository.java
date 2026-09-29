package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.pojo.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>,
                                           JpaSpecificationExecutor<Student> {

    // TODO 8
    Optional<Student> findByStudentCode(String studentCode);
    boolean existsByEmail(String email);
    long countByActiveTrue();

    // TODO 9
    List<Student> findByFullNameContainingIgnoreCase(String keyword);
    List<Student> findByEmailEndingWith(String suffix);
    List<Student> findByEmailIsNull();

    // TODO 10
    List<Student> findByGpaBetweenOrderByGpaDesc(double min, double max);
    List<Student> findByGenderAndActiveTrue(Gender gender);
    List<Student> findByDobAfter(LocalDate date);

    // TODO 11
    List<Student> findByDepartmentCodeOrderByFullNameAsc(String deptCode);
    long countByDepartmentCode(String deptCode);
    List<Student> findTop3ByOrderByGpaDesc();

    // TODO 12
    @Query("SELECT s FROM Student s WHERE s.department.code = :deptCode AND s.gpa >= :minGpa ORDER BY s.gpa DESC")
    List<Student> findGoodStudents(@Param("deptCode") String deptCode, @Param("minGpa") double minGpa);

    // TODO 13
    @Query("SELECT s FROM Student s WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%', :kw, '%')) OR (s.email IS NOT NULL AND LOWER(s.email) LIKE LOWER(CONCAT('%', :kw, '%'))) ORDER BY s.fullName")
    List<Student> searchByKeyword(@Param("kw") String kw);

    // TODO 14
    @Query("SELECT new com.hsf302.ch4.dto.DepartmentStatDTO(d.code, d.name, COUNT(s), AVG(s.gpa)) " +
           "FROM Department d LEFT JOIN d.students s " +
           "GROUP BY d.code, d.name ORDER BY d.code")
    List<com.hsf302.ch4.dto.DepartmentStatDTO> getDepartmentStatistics();

    // TODO 15
    @Query("SELECT s FROM Student s WHERE s.gpa > (SELECT AVG(st.gpa) FROM Student st) ORDER BY s.gpa DESC")
    List<Student> findAboveAverageGpa();

    // TODO 17
    @Query(value = "SELECT TOP (:n) * FROM students s " +
                   "INNER JOIN departments d ON s.department_id = d.id " +
                   "WHERE d.code = :deptCode " +
                   "ORDER BY s.gpa DESC",
           nativeQuery = true)
    List<Student> findTopNInDepartment(@Param("deptCode") String deptCode, @Param("n") int n);

    // TODO 18
    @Query("SELECT s.studentCode AS studentCode, s.fullName AS fullName, s.gpa AS gpa, " +
           "s.department.name AS departmentName " +
           "FROM Student s WHERE s.active = true ORDER BY s.fullName")
    List<com.hsf302.ch4.dto.StudentSummary> findActiveSummaries();

    // TODO 19
    @Query("SELECT s FROM Student s WHERE s.active = true AND s.department.code = :deptCode ORDER BY s.gpa DESC")
    Page<Student> findActiveByDepartment(@Param("deptCode") String deptCode, Pageable pageable);
}
