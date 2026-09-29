package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Department;

import java.util.List;

public interface DepartmentService {

    // TODO 6
    long count();
    boolean existsById(Long id);

    // TODO 11
    List<Department> findDepartmentsWithoutStudents();
}
