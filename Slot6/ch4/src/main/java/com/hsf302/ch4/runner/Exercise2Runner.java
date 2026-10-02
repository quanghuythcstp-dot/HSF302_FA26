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
    private void todo10() {
        title("TODO 10: courses by student and department with distinct");

        // (a) courses của SE002 (từ inverse side Students_StudentCode)
        printList("(a) Courses of SE002", courseService.findCoursesOfStudent("SE002"));

        // (b) courses có SV khoa AI — không Distinct (có thể trùng)
        printList("(b1) Courses of AI dept - no Distinct", courseService.findCoursesOfDepartment("AI", false));

        // (b) courses có SV khoa AI — có Distinct (loại trùng)
        printList("(b2) Courses of AI dept - Distinct", courseService.findCoursesOfDepartment("AI", true));
    }
    private void todo11() {
        title("TODO 11: unenrolled students and empty courses");

        // (a) student chưa đăng ký khóa nào
        printList("(a) Students without courses", enrollmentService.findStudentsWithoutCourses());

        // (b) course chưa có student
        printList("(b) Courses without students", courseService.findCoursesWithoutStudents());

        // (c) kiểm tra đăng ký
        System.out.println("(c) SE001 enrolled AIL303: " + enrollmentService.isEnrolled("SE001", "AIL303"));
        System.out.println("(c) SE002 enrolled AIL303: " + enrollmentService.isEnrolled("SE002", "AIL303"));
    }

    // ===== Part D =====
    private void todo12() {
        title("TODO 12: JPQL join collection");

        // tìm sinh viên của HSF302 có GPA >= 3.5, sắp xếp GPA giảm dần
        printList("Good students in HSF302 (GPA >= 3.5)",
                enrollmentService.findGoodStudentsInCourse("HSF302", 3.5));
    }
    private void todo13() {
        title("TODO 13: course statistics with dto projection");

        courseService.getStatistics().forEach(s ->
                System.out.printf("   %-6s | %-42s | %d/%d (còn %d) | avg GPA: %s%n",
                        s.code(), s.name(), s.enrolled(), s.capacity(), s.remaining(),
                        s.avgGpa() == null ? "null" : String.format("%.3f", s.avgGpa()))
        );
    }
    private void todo14() {
        title("TODO 14: group by sum having");

        // sinh viên có tổng tín chỉ >= 7, sắp xếp tổng TC giảm dần rồi fullName
        enrollmentService.getCreditSummary(7).forEach(s ->
                System.out.printf("   %-6s | %-20s | %d courses | %d credits%n",
                        s.studentCode(), s.fullName(), s.courseCount(), s.totalCredits())
        );
    }
    private void todo15() {
        title("TODO 15: full courses and busy students with size");

        // (a) khóa học đã đủ chỗ (SIZE(students) >= capacity)
        printList("(a) Full courses", courseService.findFullCourses());

        // (b) sinh viên đăng ký nhiều hơn 2 khóa
        printList("(b) Students with more than 2 courses", enrollmentService.findStudentsWithMoreThan(2));
    }
    private void todo16() {
        title("TODO 16: lazy + join fetch + entity graph");

        // (a) gọi getCourses() ngoài transaction → LazyInitializationException
        try {
            com.hsf302.ch4.pojo.Student s = studentService.findByStudentCode("SE001").orElseThrow();
            int size = s.getCourses().size();
            System.out.println("Size: " + size);
        } catch (Exception e) {
            System.out.println("(a) Exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        // (b) JOIN FETCH → load student kèm courses trong 1 query
        com.hsf302.ch4.pojo.Student s = enrollmentService.getStudentWithCourses("SE001");
        System.out.println("(b) " + s.getStudentCode() + " courses:");
        s.getCourses().stream()
                .sorted(java.util.Comparator.comparing(com.hsf302.ch4.pojo.Course::getCode))
                .forEach(c -> System.out.println("   " + c));

        // (c) @EntityGraph → load course SWP391 kèm students
        com.hsf302.ch4.pojo.Course c = courseService.getWithStudents("SWP391");
        System.out.println("(c) " + c.getCode() + " students:");
        c.getStudents().stream()
                .sorted(java.util.Comparator.comparing(com.hsf302.ch4.pojo.Student::getFullName))
                .forEach(st -> System.out.println("   " + st));
    }
    private void todo17() {
        title("TODO 17: top enrolled courses native sql");

        // top 3 khóa học đông sinh viên nhất (kể cả 0 SV), native SQL TOP
        courseService.findTopEnrolled(3).forEach(c ->
                System.out.printf("   %-6s | %-42s | %d enrolled%n",
                        c.getCode(), c.getName(), c.getEnrolled())
        );
    }
    private void todo18() {
        title("TODO 18: enrollment view interface projection");

        // bảng đăng ký của sinh viên khoa AI
        enrollmentService.getEnrollmentsOfDepartment("AI").forEach(e ->
                System.out.printf("   %-6s | %-20s | %-6s | %-42s | %d credits%n",
                        e.getStudentCode(), e.getFullName(),
                        e.getCourseCode(), e.getCourseName(), e.getCredits())
        );
    }
    private void todo19() {
        title("TODO 19: paginate students of course");

        // phân trang sinh viên của HSF302, mỗi trang 2, sắp xếp fullName
        int total = 0;
        int page = 0;
        org.springframework.data.domain.Page<com.hsf302.ch4.pojo.Student> result;
        do {
            result = enrollmentService.findStudentsInCoursePage("HSF302", page, 2);
            printList("HSF302 page " + page, result.getContent());
            if (page == 0) {
                System.out.println("totalElements=" + result.getTotalElements()
                        + ", totalPages=" + result.getTotalPages());
            }
            page++;
        } while (result.hasNext());
    }

    // ===== Bonus =====
    private void todo25() {
        title("TODO 25: specification with join and distinct");

        // search(null, "SU26", null, null) → SV đăng ký khóa học kỳ SU26
        printList("search(null, SU26, null, null)",
                enrollmentService.search(null, "SU26", null, null));

        // search("HSF302", null, "SE", 3.5) → SV SE đăng ký HSF302 có GPA >= 3.5
        printList("search(HSF302, null, SE, 3.5)",
                enrollmentService.search("HSF302", null, "SE", 3.5));

        // search(null, "FA26", "AI", null) → SV AI đăng ký khóa FA26
        printList("search(null, FA26, AI, null)",
                enrollmentService.search(null, "FA26", "AI", null));
    }

    // ===== Part E =====
    private void todo20() {
        title("TODO 20: enroll student");

        attempt("enroll IA003 -> MKT101 (OK)",       () -> enrollmentService.enroll("IA003", "MKT101"));
        attempt("enroll SE001 -> PRJ301 (đã đăng ký)", () -> enrollmentService.enroll("SE001", "PRJ301"));
        attempt("enroll SE004 -> AIL303 (hết chỗ)",   () -> enrollmentService.enroll("SE004", "AIL303"));
        attempt("enroll SE003 -> HSF302 (inactive)",   () -> enrollmentService.enroll("SE003", "HSF302"));
        attempt("enroll XX999 -> HSF302 (not found)",  () -> enrollmentService.enroll("XX999", "HSF302"));

        printList("Courses of IA003", enrollmentService.getCoursesOfStudent("IA003"));
        System.out.println("Students of MKT101: " + enrollmentService.countStudentsInCourse("MKT101"));
    }
    private void todo21() {
        title("TODO 21: unenroll student");

        attempt("unenroll AI002 -> AIL303 (OK)",     () -> enrollmentService.unenroll("AI002", "AIL303"));
        attempt("unenroll IA003 -> PRJ301 (not enrolled)", () -> enrollmentService.unenroll("IA003", "PRJ301"));

        // sau khi AI002 rời AIL303 thì AIL303 còn chỗ → SE004 có thể đăng ký
        attempt("enroll SE004 -> AIL303 (OK now)",   () -> enrollmentService.enroll("SE004", "AIL303"));

        printList("Students of AIL303", enrollmentService.findStudentsInCourse("AIL303"));
        printList("Courses of AI002",   enrollmentService.getCoursesOfStudent("AI002"));

        System.out.println("AI002 still exists: " + studentService.findByStudentCode("AI002").isPresent());
        System.out.println("Total courses: " + courseService.count());
    }
    private void todo22() { title("TODO 22: switch course in one transaction"); }
    private void todo23() { title("TODO 23: delete course safely"); }
    private void todo24() { title("TODO 24: remove enrollments of inactive students"); }
}
