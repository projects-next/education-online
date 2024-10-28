package com.projects_next.education.student;

import com.projects_next.education.student.model.Student;
import com.projects_next.education.student.model.StudentDto;
import com.projects_next.education.student.model.StudentResponseDto;
import com.projects_next.education.student.model.StudentUpdatePartialDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRespository studentRespository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRespository studentRespository, StudentMapper studentMapper) {
        this.studentRespository = studentRespository;
        this.studentMapper = studentMapper;
    }

    public StudentResponseDto saveStudent(StudentDto studentDto) {
        var student = studentMapper.mapStudent(studentDto);
        var newStudent = studentRespository.save(student);
        return studentMapper.mapStudentResponseDto(newStudent);
    }

    public StudentResponseDto updateStudentById(Integer studentId, StudentDto studentDto) {
        Student student = studentRespository.findById(studentId).get();
        student.setFirstName(studentDto.firstName());
        student.setLastName(studentDto.lastName());
        student.setEmail(studentDto.email());
        Student result = studentRespository.save(student);
        return studentMapper.mapStudentResponseDto(result);
    }

    public StudentResponseDto updatePartialStudentById(Integer studentId,
                                                       StudentUpdatePartialDto studentUpdatePartialDto)
    {
        Student student = studentRespository.findById(studentId).get();
        if (studentUpdatePartialDto.firstName() != null) student.setFirstName(studentUpdatePartialDto.firstName());
        if (studentUpdatePartialDto.lastName() != null) student.setLastName(studentUpdatePartialDto.lastName());
        if (studentUpdatePartialDto.email() != null) student.setEmail(studentUpdatePartialDto.email());
        Student result = studentRespository.save(student);
        return studentMapper.mapStudentResponseDto(result);
    }

    public List<StudentResponseDto> getAll() {
        return studentRespository.findAll()
                                 .stream()
                                 .map(studentMapper::mapStudentResponseDto)
                                 .collect(Collectors.toList());
    }

    public StudentResponseDto getById(Integer studentId) {
        return studentRespository.findById(studentId)
                                 .map(studentMapper::mapStudentResponseDto)
                                 .orElse(null);
    }

    public List<StudentResponseDto> getAllByFirstName(String studentName) {
        return studentRespository.findAllByFirstNameLike(studentName)
                                 .stream()
                                 .map(studentMapper::mapStudentResponseDto)
                                 .collect(Collectors.toList());
    }

    public void delete(Integer id) {
        studentRespository.deleteById(id);
    }
}
