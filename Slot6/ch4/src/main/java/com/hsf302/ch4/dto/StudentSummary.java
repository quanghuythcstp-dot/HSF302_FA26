package com.hsf302.ch4.dto;

public interface StudentSummary {
    String getStudentCode();
    String getFullName();
    Double getGpa();
    String getDepartmentName();  // alias trong @Query phải khớp: departmentName
}
