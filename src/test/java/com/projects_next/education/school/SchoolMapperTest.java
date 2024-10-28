package com.projects_next.education.school;

import com.projects_next.education.school.model.School;
import com.projects_next.education.school.model.SchoolResponseDto;
import com.projects_next.education.student.StudentMapper;
import com.projects_next.education.student.model.Student;
import com.projects_next.education.student.model.StudentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SchoolMapperTest {
    @InjectMocks
    private SchoolMapper mapper;
    @Mock
    private StudentMapper studentMapper;

    private School school;
    private Student student;
    private StudentResponseDto studentResponseDto;


    @BeforeEach
    void setUp() {
        student = Student.builder()
                         .id(1)
                         .firstName("John")
                         .lastName("Nguyen")
                         .email("email@gmail.com")
                         .build();
        studentResponseDto = StudentResponseDto.builder()
                                               .firstName("John")
                                               .lastName("Nguyen")
                                               .email("email@gmail.com")
                                               .build();
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        this.school = School.builder()
                            .name("Truong Cao Dang")
                            .students(studentList)
                            .build();
    }

    @Test
    void givenSchool_whenMapSchoolDto_thenReturnSchoolResponseDto() {
        // Given
        Mockito.when(studentMapper.mapStudentResponseDto(student)).thenReturn(studentResponseDto);

        // When
        SchoolResponseDto result = mapper.mapSchoolDto(school);

        // Then
        assertNotNull(school);
        assertEquals("Truong Cao Dang", result.name());
        assertEquals(1, result.students().size());
    }
}