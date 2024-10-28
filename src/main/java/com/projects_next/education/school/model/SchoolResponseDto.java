package com.projects_next.education.school.model;

import com.projects_next.education.student.model.StudentResponseDto;
import lombok.Builder;

import java.util.List;

@Builder
public record SchoolResponseDto(
        String name,
        List<StudentResponseDto> students
)
{
}
