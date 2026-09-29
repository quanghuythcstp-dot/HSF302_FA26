package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.DepartmentStatDTO;
import com.hsf302.ch4.pojo.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    // TODO 6
    long count();
    boolean existsById(Long id);

    // TODO 11
    List<Department> findDepartmentsWithoutStudents();

    // TODO 14 (ở DepartmentService)

    // TODO 15
    List<DepartmentStatDTO> getStatistics();

    // TODO 16
    Optional<Department> findByCode(String code);
    Department getWithStudents(String code);

    // TODO 22
    int transferStudentsAndDelete(String fromCode, String toCode);
    List<Department> findAll();
}
