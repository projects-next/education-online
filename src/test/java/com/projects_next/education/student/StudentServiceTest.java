package com.projects_next.education.student;

import com.projects_next.education.school.model.School;
import com.projects_next.education.student.model.Student;
import com.projects_next.education.student.model.StudentDto;
import com.projects_next.education.student.model.StudentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    // which service we want to test
    @InjectMocks
    private StudentService studentService;
    // declare the dependencies
    @Mock
    private StudentRespository respository;
    @Mock
    private StudentMapper mapper;
    private StudentDto studentDto;
    private Student student;
    private Student saveStudent;
    private StudentResponseDto studentResponseDto;
    private StudentResponseDto newStudentResponseDto;
    private List<Student> studentList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.studentDto = StudentDto.builder()
                                    .firstName("John")
                                    .lastName("Nguyen")
                                    .email("john@gmail.com")
                                    .schoolId(1)
                                    .build();
        this.student = Student.builder()
                              .firstName("John")
                              .lastName("Nguyen")
                              .email("john@gmail.com")
                              .age(20)
                              .build();
        this.saveStudent = Student.builder()
                                  .firstName("John")
                                  .lastName("Nguyen")
                                  .email("john@gmail.com")
                                  .age(20)
                                  .id(1)
                                  .build();
        this.studentResponseDto = StudentResponseDto.builder()
                                                    .firstName("John")
                                                    .lastName("Nguyen")
                                                    .email("john@gmail.com")
                                                    .school("Nguyen Trai")
                                                    .build();
        this.newStudentResponseDto = StudentResponseDto.builder()
                                                       .firstName("New Name")
                                                       .lastName("New Last Name")
                                                       .email("new@gmail.com")
                                                       .school("Nguyen Thi Minh Khai")
                                                       .build();
        this.studentList = new ArrayList<>();
        studentList.add(student);
    }

    @Test
    void shouldSuccessfullySaveAStudent() {
        // Mock
        when(mapper.mapStudent(studentDto)).thenReturn(student);
        when(respository.save(student)).thenReturn(saveStudent);
        when(mapper.mapStudentResponseDto(saveStudent)).thenReturn(studentResponseDto);

        // When
        StudentResponseDto responseDto = studentService.saveStudent(studentDto);

        // Then
        assertEquals(studentDto.firstName(), responseDto.firstName());
        assertEquals(studentDto.lastName(), responseDto.lastName());
        assertEquals(studentDto.email(), responseDto.email());

        verify(mapper, times(1)).mapStudent(studentDto);
        verify(respository, times(1)).save(student);
        verify(mapper, times(1)).mapStudentResponseDto(saveStudent);
    }

    @Test
    void shouldSuccessfullyUpdateStudentById() {
        // Given
        when(respository.findById(1)).thenReturn(Optional.ofNullable(student));
        student.setFirstName("New Name");
        student.setLastName("New Last Name");
        student.setEmail("new@gmail.com");
        student.setSchool(School.builder().name("Nguyen Thi Minh Khai").build());
        when(respository.save(student)).thenReturn(student);
        when(mapper.mapStudentResponseDto(student)).thenReturn(newStudentResponseDto);

        // When
        StudentResponseDto result = studentService.updateStudentById(1, studentDto);

        // Then
        assertNotNull(result);
        assertEquals("New Name", result.firstName());
        assertEquals("New Last Name", result.lastName());
        assertEquals("new@gmail.com", result.email());
        assertEquals("Nguyen Thi Minh Khai", result.school());
        verify(respository, Mockito.times(1)).findById(1);
        verify(respository, Mockito.times(1)).save(student);
        verify(mapper, Mockito.times(1)).mapStudentResponseDto(student);
    }

    @Test
    void shouldSuccessfullyUpdatePartiallyStudentById() {
        // Given
        when(respository.findById(1)).thenReturn(Optional.ofNullable(student));
        student.setFirstName("New Name");
        when(respository.save(student)).thenReturn(student);
        when(mapper.mapStudentResponseDto(student)).thenReturn(newStudentResponseDto);

        // When
        StudentResponseDto result = studentService.updateStudentById(1, studentDto);

        // Then
        assertNotNull(result);
        assertEquals("New Name", result.firstName());
        verify(respository, Mockito.times(1)).findById(1);
        verify(respository, Mockito.times(1)).save(student);
        verify(mapper, Mockito.times(1)).mapStudentResponseDto(student);
    }

    @Test
    void shouldReturnAllStudent() {
        // Mock
        when(respository.findAll()).thenReturn(studentList);
        when(mapper.mapStudentResponseDto(any(Student.class))).thenReturn(studentResponseDto);

        // When
        List<StudentResponseDto> responseAll = studentService.getAll();

        // Then
        assertEquals(1, responseAll.size());
        verify(respository, times(1)).findAll();
    }

    @Test
    void shouldReturnStudentById() {
        //Given
        Integer id = 1;

        // Mock
        when(respository.findById(id)).thenReturn(Optional.of(student));
        when(mapper.mapStudentResponseDto(student)).thenReturn(studentResponseDto);

        // When
        StudentResponseDto responseDto = studentService.getById(id);

        // Then
        assertEquals(student.getFirstName(), responseDto.firstName());
        assertEquals(student.getLastName(), responseDto.lastName());
        assertEquals(student.getEmail(), responseDto.email());

        verify(respository, times(1)).findById(id);
        verify(mapper, times(1)).mapStudentResponseDto(student);
    }

    @Test
    void shouldReturnStudentByFirstName() {
        // Given
        String firstName = "John";

        // Mock
        when(respository.findAllByFirstNameLike(firstName)).thenReturn(studentList);
        when(mapper.mapStudentResponseDto(any(Student.class))).thenReturn(studentResponseDto);

        // When
        var responseDtos = studentService.getAllByFirstName(firstName);

        // Then
        assertEquals(studentList.size(), responseDtos.size());
        verify(respository, times(1)).findAllByFirstNameLike(firstName);
    }
}