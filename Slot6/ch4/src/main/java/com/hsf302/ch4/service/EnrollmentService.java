package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Course;
import com.hsf302.ch4.pojo.Student;

import java.util.List;

public interface EnrollmentService {

    // TODO 7
    List<Course> getCoursesOfStudent(String studentCode);
    List<Student> getStudentsOfCourse(String courseCode);

    // TODO 9
    List<Student> findStudentsInCourse(String courseCode);
    long countStudentsInCourse(String courseCode);
    List<Student> findActiveStudentsInCourse(String courseCode);

    // TODO 11
    List<Student> findStudentsWithoutCourses();
    boolean isEnrolled(String studentCode, String courseCode);

    // TODO 12
    List<Student> findGoodStudentsInCourse(String courseCode, double minGpa);

    // TODO 14
    List<com.hsf302.ch4.dto.StudentCreditDTO> getCreditSummary(int minCredits);

    // TODO 15
    List<Student> findStudentsWithMoreThan(int n);

    // TODO 16
    Student getStudentWithCourses(String studentCode);

    // TODO 18
    List<com.hsf302.ch4.dto.EnrollmentView> getEnrollmentsOfDepartment(String deptCode);

    // TODO 19
    org.springframework.data.domain.Page<com.hsf302.ch4.pojo.Student> findStudentsInCoursePage(
            String courseCode, int pageIndex, int size);

    // TODO 25
    List<com.hsf302.ch4.pojo.Student> search(String courseCode, String semester, String deptCode, Double minGpa);

    // TODO 20
    void enroll(String studentCode, String courseCode);

    // TODO 21
    void unenroll(String studentCode, String courseCode);

    // TODO 22
    void switchCourse(String studentCode, String fromCode, String toCode);

    // TODO 24
    int removeEnrollmentsOfInactiveStudents();
}
