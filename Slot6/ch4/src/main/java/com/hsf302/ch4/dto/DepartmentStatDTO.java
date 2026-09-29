package com.hsf302.ch4.dto;

public record DepartmentStatDTO(
        String code,
        String name,
        Long studentCount,
        Double avgGpa
) {}
