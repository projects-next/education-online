package com.projects_next.education.student.model;

import lombok.Builder;

@Builder
public record StudentResponseDto(
        String firstName,
        String lastName,
        String email,
        String school
) {
}
