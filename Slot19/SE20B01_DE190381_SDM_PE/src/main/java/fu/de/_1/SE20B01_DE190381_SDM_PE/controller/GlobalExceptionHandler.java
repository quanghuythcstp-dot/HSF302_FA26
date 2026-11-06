package fu.de._1.SE20B01_DE190381_SDM_PE.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Q4.4 — Bat tat ca exception khong xu ly duoc o controller,
 * tra ve trang loi thay vi hien stacktrace trang.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An error occurred: " + ex.getMessage());
        return "error";
    }
}
