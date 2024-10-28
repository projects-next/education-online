package com.projects_next.education.student.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record StudentDto(
        @NotEmpty(message = "Firstname should not be empty")
        String firstName,
        @NotEmpty(message = "Lastname should not be empty")
        String lastName,
        String email,
        Integer schoolId
) {

}
