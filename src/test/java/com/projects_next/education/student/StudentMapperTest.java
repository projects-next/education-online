package com.projects_next.education.student;

import com.projects_next.education.school.model.School;
import com.projects_next.education.student.model.Student;
import com.projects_next.education.student.model.StudentDto;
import com.projects_next.education.student.model.StudentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentMapperTest {
    @InjectMocks
    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldMapStudentDtoToStudent() {
        StudentDto dto = new StudentDto(
                "Nguyen",
                "Tran",
                "email1@gmail.com",
                1
        );
        Student student = mapper.mapStudent(dto);
        assertEquals(dto.firstName(), student.getFirstName());
        assertEquals(dto.lastName(), student.getLastName());
        assertNotNull(student.getSchool());
        assertEquals(dto.schoolId(), student.getSchool().getId());
    }

    @Test
    void shouldThrowNullPointerExceptionWhenStudentDtoIsNull() {
        var exp = assertThrows(NullPointerException.class, () -> mapper.mapStudent(null));
        assertEquals("StudentDto should not be null", exp.getMessage());
    }

    @Test
    void shouldMapStudentToStudentResponseDto() {
        //Given
        Student student = Student.builder()
                                 .firstName("Nguyen")
                                 .lastName("Tran")
                                 .email("email1@gmail.com")
                                 .school(School.builder().build())
                                 .id(1)
                                 .build();

        //When
        StudentResponseDto studentResponseDto = mapper.mapStudentResponseDto(student);

        //Then
        assertEquals(student.getFirstName(), studentResponseDto.firstName());
        assertEquals(student.getLastName(), studentResponseDto.lastName());
        assertEquals(student.getEmail(), studentResponseDto.email());
    }
}