package com.projects_next.education.student;

import com.projects_next.education.student.model.StudentDto;
import com.projects_next.education.student.model.StudentResponseDto;
import com.projects_next.education.student.model.StudentUpdatePartialDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping({"/v1.0"})
    public StudentResponseDto newStudent(
            @Valid @RequestBody StudentDto studentDto
    )
    {
        return studentService.saveStudent(studentDto);
    }

    @PutMapping({"/v1.0/{student-id}"})
    public StudentResponseDto updateStudent(
            @Valid @RequestBody StudentDto studentDto,
            @PathVariable("student-id") Integer studentId
    )
    {
        return studentService.updateStudentById(studentId, studentDto);
    }

    @PatchMapping({"/v1.0/{student-id}"})
    public StudentResponseDto updatePartialStudent(
            @Valid @RequestBody StudentUpdatePartialDto studentUpdatePartialDto,
            @PathVariable("student-id") Integer studentId
    )
    {
        return studentService.updatePartialStudentById(studentId, studentUpdatePartialDto);
    }

    @GetMapping({"/v1.0"})
    public List<StudentResponseDto> findAllStudent() {
        return studentService.getAll();
    }

    @GetMapping({"/v1.0/{student-id}"})
    public StudentResponseDto findById(
            @PathVariable("student-id") Integer studentId
    )
    {
        return studentService.getById(studentId);
    }

    @GetMapping({"/v1.0/search"})
    public List<StudentResponseDto> findAllByFirstName(
            @RequestParam("name") String studentName
    ){
        return studentService.getAllByFirstName(studentName);
    }

    @DeleteMapping({"/v1.0/{student-id}"})
    public void deleteById(
            @PathVariable("student-id") Integer id
    ){
        studentService.delete(id);
    }
}
