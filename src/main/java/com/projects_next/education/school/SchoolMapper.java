package com.projects_next.education.school;

import com.projects_next.education.school.model.School;
import com.projects_next.education.school.model.SchoolResponseDto;
import com.projects_next.education.student.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class SchoolMapper {
    @Autowired
    private final StudentMapper studentMapper;

    public SchoolMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public SchoolResponseDto mapSchoolDto(School school) {
        return SchoolResponseDto.builder()
                                .name(school.getName())
                                .students(school.getStudents() != null && !school.getStudents().isEmpty() ?
                                          school.getStudents()
                                                .stream()
                                                .map(studentMapper::mapStudentResponseDto)
                                                .collect(Collectors.toList()) : Arrays.asList())
                                .build();
    }
}
