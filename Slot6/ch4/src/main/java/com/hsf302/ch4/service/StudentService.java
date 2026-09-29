package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Student;
import org.springframework.data.domain.Page;

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

    List<Student> searchByName(String keyword);
    List<Student> findByEmailDomain(String domain);
    List<Student> findWithoutEmail();
}
