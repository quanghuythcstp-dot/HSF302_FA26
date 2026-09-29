package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.DepartmentStatDTO;
import com.hsf302.ch4.pojo.Department;
import com.hsf302.ch4.repository.DepartmentRepository;
import com.hsf302.ch4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;  // dùng ở TODO 22 (chuyển sinh viên)

    // TODO 6
    @Override
    public long count() {
        return departmentRepository.count();
    }

    @Override
    public boolean existsById(Long id) {
        return departmentRepository.existsById(id);
    }

    // TODO 11
    @Override
    public List<Department> findDepartmentsWithoutStudents() {
        return departmentRepository.findByStudentsIsEmpty();
    }

    // TODO 14
    @Override
    public List<DepartmentStatDTO> getStatistics() {
        return studentRepository.getDepartmentStatistics();
    }

    // TODO 16
    @Override
    public Optional<Department> findByCode(String code) {
        return departmentRepository.findByCode(code);
    }

    @Override
    public Department getWithStudents(String code) {
        return departmentRepository.findByCodeWithStudents(code)
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + code));
    }

    // TODO 22
    @Override
    @Transactional
    public int transferStudentsAndDelete(String fromCode, String toCode) {
        if (fromCode.equals(toCode)) throw new IllegalArgumentException("2 khoa phải khác nhau");
        Department from = departmentRepository.findByCode(fromCode)
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + fromCode));
        Department to = departmentRepository.findByCode(toCode)
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + toCode));
        int transferred = studentRepository.transferStudents(from, to);
        departmentRepository.deleteById(from.getId());
        return transferred;
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}
