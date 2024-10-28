package com.projects_next.education.student.model;

import lombok.Builder;

@Builder
public record StudentUpdatePartialDto(
        String firstName,
        String lastName,
        String email,
        Integer schoolId
) {

}
