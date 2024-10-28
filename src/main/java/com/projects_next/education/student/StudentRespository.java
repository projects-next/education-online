package com.projects_next.education.student;


import com.projects_next.education.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRespository extends JpaRepository<Student, Integer> {
    List<Student> findAllByFirstNameLike(String p);
}
