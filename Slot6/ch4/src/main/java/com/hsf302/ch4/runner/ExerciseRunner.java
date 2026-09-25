package com.hsf302.ch4.runner;

import com.hsf302.ch4.service.DepartmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
    private void todo6()  { title("TODO 6: count / findById / existsById"); }
    private void todo7()  { title("TODO 7: Sort & Pageable"); }

    // ===== Part C =====
    private void todo8()  { title("TODO 8: findBy / existsBy / countBy"); }
    private void todo9()  { title("TODO 9: ContainingIgnoreCase / EndingWith / IsNull"); }
    private void todo10() { title("TODO 10: Between / And+True / After"); }
    private void todo11() { title("TODO 11: nested property / Top / IsEmpty"); }

    // ===== Part D =====
    private void todo12() { title("TODO 12: JPQL + named parameter"); }
    private void todo13() { title("TODO 13: JPQL LIKE"); }
    private void todo14() { title("TODO 14: LEFT JOIN + GROUP BY + DTO"); }
    private void todo15() { title("TODO 15: subquery AVG"); }
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
