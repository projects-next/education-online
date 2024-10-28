package com.projects_next.education.student;

import com.projects_next.education.school.model.School;
import com.projects_next.education.student.model.Student;
import com.projects_next.education.student.model.StudentDto;
import com.projects_next.education.student.model.StudentResponseDto;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {
    public Student mapStudent(StudentDto dto) {
        if(dto == null) {
            throw new NullPointerException("StudentDto should not be null");
        }
        var student = new Student();
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setEmail(dto.email());
        var school = new School();
        school.setId(dto.schoolId());
        student.setSchool(school);
        return student;
    }

    public StudentResponseDto mapStudentResponseDto(Student student) {
        return new StudentResponseDto(
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getSchool().getName()
        );
    }
}
