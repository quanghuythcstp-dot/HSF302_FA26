package com.hsf302.ch4.runner;

import com.hsf302.ch4.service.CourseService;
import com.hsf302.ch4.service.EnrollmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collection;
@Component
@Order(3)
@Profile("ex2")
@RequiredArgsConstructor
public class Exercise2Runner implements CommandLineRunner {

    // CHỈ inject Service interface
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final StudentService studentService;

    @Override
    public void run(String... args) {
        partB();
        partC();
        partD();
        bonus();    // chạy trên dữ liệu gốc → trước Part E
        partE();
    }

    private void partB() { todo6(); todo7(); }
    private void partC() { todo8(); todo9(); todo10(); todo11(); }
    private void partD() { todo12(); todo13(); todo14(); todo15(); todo16(); todo17(); todo18(); todo19(); }
    private void bonus() { todo25(); }
    private void partE() { todo20(); todo21(); todo22(); todo23(); todo24(); }

    // ===== helpers =====
    private void title(String t) {
        System.out.println("\n===== " + t + " =====");
    }

    private void printList(String label, Collection<?> list) {
        System.out.println("-- " + label + ":");
        list.forEach(o -> System.out.println("   " + o));
        System.out.println("   -> " + list.size() + " record(s)");
    }

    private void attempt(String label, Runnable action) {
        try {
            action.run();
            System.out.println("   [OK]   " + label);
        } catch (RuntimeException e) {
            System.out.println("   [FAIL] " + label + " -> " + e.getMessage());
        }
    }

    // ===== Part B =====
    private void todo6() {
        title("TODO 6: count, findAll(Sort), findById");

        System.out.println("Total courses: " + courseService.count());
        printList("All courses order by code", courseService.findAllOrderByCode());

        for (long id : new long[]{2L, 99L}) {
            System.out.println("findById(" + id + "): "
                    + courseService.findById(id).map(Object::toString).orElse("Not found"));
        }
    }
    private void todo7() {
        title("TODO 7: navigate student.getCourses() / course.getStudents()");

        // (a) courses của SE001 (owning side)
        printList("(a) Courses of SE001", enrollmentService.getCoursesOfStudent("SE001"));

        // (b) students của AIL303 (inverse side)
        printList("(b) Students of AIL303", enrollmentService.getStudentsOfCourse("AIL303"));
    }

    // ===== Part C =====
    private void todo8() {
        title("TODO 8: findByCode, findBySemester, countBySemester");

        // (a) findByCode
        for (String code : java.util.List.of("HSF302", "XXX000")) {
            System.out.println("(a) " + code + ": "
                    + courseService.findByCode(code).map(c -> c.getName()).orElse("Not found"));
        }

        // (b) findBySemester SU26
        printList("(b) Semester SU26", courseService.findBySemester("SU26"));

        // (c) countBySemester FA26
        System.out.println("(c) Courses in FA26: " + courseService.countBySemester("FA26"));
    }
    private void todo9() {
        title("TODO 9: derived query through collection courses");

        // (a) students đăng ký PRJ301, sắp xếp fullName
        printList("(a) Students of PRJ301", enrollmentService.findStudentsInCourse("PRJ301"));

        // (b) đếm students của HSF302
        System.out.println("(b) Students of HSF302: " + enrollmentService.countStudentsInCourse("HSF302"));

        // (c) students ACTIVE đăng ký PRJ301
        printList("(c) Active students of PRJ301", enrollmentService.findActiveStudentsInCourse("PRJ301"));
    }
    private void todo10() { title("TODO 10: courses by student and department with distinct"); }
    private void todo11() { title("TODO 11: unenrolled students and empty courses"); }

    // ===== Part D =====
    private void todo12() { title("TODO 12: JPQL join collection"); }
    private void todo13() { title("TODO 13: course statistics with dto projection"); }
    private void todo14() { title("TODO 14: group by sum having"); }
    private void todo15() { title("TODO 15: full courses and busy students with size"); }
    private void todo16() { title("TODO 16: lazy + join fetch + entity graph"); }
    private void todo17() { title("TODO 17: top enrolled courses native sql"); }
    private void todo18() { title("TODO 18: enrollment view interface projection"); }
    private void todo19() { title("TODO 19: paginate students of course"); }

    // ===== Bonus =====
    private void todo25() { title("TODO 25: specification with join and distinct"); }

    // ===== Part E =====
    private void todo20() { title("TODO 20: enroll student"); }
    private void todo21() { title("TODO 21: unenroll student"); }
    private void todo22() { title("TODO 22: switch course in one transaction"); }
    private void todo23() { title("TODO 23: delete course safely"); }
    private void todo24() { title("TODO 24: remove enrollments of inactive students"); }
}
