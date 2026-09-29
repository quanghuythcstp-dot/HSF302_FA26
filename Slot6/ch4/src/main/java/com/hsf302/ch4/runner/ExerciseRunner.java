package com.hsf302.ch4.runner;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.service.DepartmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;

@Component
@Order(2)
@RequiredArgsConstructor
public class ExerciseRunner implements CommandLineRunner {

    // Runner CHỈ phụ thuộc vào Service (interface), KHÔNG inject Repository
    private final DepartmentService departmentService;
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
    private void bonus() { todo24(); }
    private void partE() { todo20(); todo21(); todo22(); todo23(); }

    // ===== helpers =====
    private void title(String t) {
        System.out.println("\n===== " + t + " =====");
    }

    private void printList(String label, Collection<?> list) {
        System.out.println("-- " + label + ":");
        list.forEach(o -> System.out.println("   " + o));
        System.out.println("   -> " + list.size() + " record(s)");
    }

    // ===== Part B =====
    private void todo6() {
        title("TODO 6: count / findById / existsById");
        System.out.println("Departments: " + departmentService.count());
        System.out.println("Students   : " + studentService.count());

        studentService.findById(1L).ifPresentOrElse(
                s -> System.out.println("findById(1)  -> " + s),
                () -> System.out.println("findById(1)  -> Not found"));

        System.out.println("findById(99) -> " + studentService.findById(99L)
                .map(Object::toString)
                .orElse("Not found"));

        System.out.println("existsById(4) department -> " + departmentService.existsById(4L));
    }

    private void todo7() {
        title("TODO 7: Sort & Pageable");

        // (a) GPA giảm dần
        printList("All students order by GPA desc", studentService.findAllOrderByGpaDesc());

        // (b) Trang THỨ 2 → index 1 (Spring Data đánh số trang từ 0)
        Page<Student> page = studentService.findPage(1, 3, "fullName");
        printList("Page index " + page.getNumber() + " (size " + page.getSize() + ")", page.getContent());
        System.out.println("totalElements=" + page.getTotalElements()
                + ", totalPages=" + page.getTotalPages()
                + ", hasNext=" + page.hasNext()
                + ", hasPrevious=" + page.hasPrevious());
    }

    // ===== Part C =====
    private void todo8() {
        title("TODO 8: findBy / existsBy / countBy");

        // (a) findByStudentCode
        studentService.findByStudentCode("AI002").ifPresentOrElse(
                s -> System.out.println("AI002 -> " + s),
                () -> System.out.println("AI002 -> Not found"));

        studentService.findByStudentCode("XX999").ifPresentOrElse(
                s -> System.out.println("XX999 -> " + s),
                () -> System.out.println("XX999 -> Not found"));

        // (b) existsByEmail
        System.out.println("exists binh.tt@fpt.edu.vn -> " + studentService.isEmailExisted("binh.tt@fpt.edu.vn"));

        // (c) countByActiveTrue
        System.out.println("Active students: " + studentService.countActive());
    }
    private void todo9() {
        title("TODO 9: ContainingIgnoreCase / EndingWith / IsNull");

        printList("searchByName(\"nguyen\")", studentService.searchByName("nguyen"));
        printList("findByEmailDomain(\"@gmail.com\")", studentService.findByEmailDomain("@gmail.com"));
        printList("findWithoutEmail()", studentService.findWithoutEmail());
    }
    private void todo10() {
        title("TODO 10: Between / And+True / After");

        printList("GPA [3.0, 3.6]", studentService.findByGpaRange(3.0, 3.6));
        printList("MALE active", studentService.findActiveByGender(Gender.MALE));
        printList("Born after 2005-01-01", studentService.findBornAfter(LocalDate.of(2005, 1, 1)));
    }
    private void todo11() {
        title("TODO 11: nested property / Top / IsEmpty");

        // (a) sinh viên thuộc SE sắp xếp fullName tăng dần (nested property: department.code)
        printList("Students in SE (order by name)", studentService.findByDepartment("SE"));

        // (b) đếm sinh viên của AI
        System.out.println("Count AI: " + studentService.countByDepartment("AI"));

        // (c) top 3 GPA cao nhất
        printList("Top 3 GPA", studentService.findTop3ByGpa());

        // (d) department chưa có sinh viên
        printList("Departments without students", departmentService.findDepartmentsWithoutStudents());
    }

    // ===== Part D =====
    private void todo12() {
        title("TODO 12: JPQL + named parameter");

        // tìm sinh viên SE có GPA >= 3.0, sắp xếp GPA giảm dần
        printList("Good students in SE (GPA >= 3.0)", studentService.findGoodStudents("SE", 3.0));
    }
    private void todo13() {
        title("TODO 13: JPQL LIKE");

        // tìm theo fullName hoặc email chứa từ khoá (không phân biệt hoa thường)
        printList("searchByKeyword(\"hoa\")", studentService.searchByKeyword("hoa"));
        printList("searchByKeyword(\"gmail\")", studentService.searchByKeyword("gmail"));
    }
    private void todo14() {
        title("TODO 14: LEFT JOIN + GROUP BY + DTO");

        // thống kê từng department (kể cả chưa có sinh viên), in GPA 3 chữ số thập phân
        departmentService.getStatistics().forEach(s ->
                System.out.printf("   %-4s | %-25s | %3d students | avg GPA: %s%n",
                        s.code(), s.name(), s.studentCount(),
                        s.avgGpa() == null ? "null" : String.format("%.3f", s.avgGpa()))
        );
    }
    private void todo15() {
        title("TODO 15: subquery AVG");

        // tìm student có GPA lớn hơn GPA trung bình toàn bộ
        printList("Students above average GPA", studentService.findAboveAverageGpa());
    }
    private void todo16() { title("TODO 16: LazyInitializationException + JOIN FETCH"); }
    private void todo17() { title("TODO 17: native query TOP N"); }
    private void todo18() { title("TODO 18: interface projection"); }
    private void todo19() { title("TODO 19: @Query + Pageable"); }

    // ===== Bonus =====
    private void todo24() { title("TODO 24: Specification"); }

    // ===== Part E =====
    private void todo20() { title("TODO 20: update GPA dirty checking"); }
    private void todo21() { title("TODO 21: @Modifying UPDATE"); }
    private void todo22() { title("TODO 22: transfer students + delete department"); }
    private void todo23() { title("TODO 23: derived delete inactive"); }
}
