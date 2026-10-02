package com.hsf302.ch4.dto;

public record CourseStatDTO(
        String code,
        String name,
        Integer capacity,
        Long enrolled,
        Double avgGpa
) {
    public int remaining() {
        return capacity - enrolled.intValue();
    }
}
