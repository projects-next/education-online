package com.projects_next.education.student;

import com.projects_next.education.student.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {
    @Autowired
    StudentRespository studentRespository;
    @Autowired
    TestEntityManager entityManager;
    private Student student;

    @Test
    public void givenNewStudent_whenSave_thenSuccess() throws Exception {
        // when
        Student newStudent = studentRespository.save(student);

        // then
        assertThat(entityManager.find(Student.class, newStudent.getId())).isEqualTo(student);
    }

    @Test
    public void givenCurrentStudent_whenUpdate_thenSuccess() throws Exception {
        // given
        String newFirstName = "New First name";

        // when
        entityManager.persist(student);
        student.setFirstName(newFirstName);
        studentRespository.save(student);

        // then
        assertThat(entityManager.find(Student.class, student.getId()).getFirstName()).isEqualTo(newFirstName);
    }

    @Test
    public void givenCurrentStudent_whenFindById_thenSuccess() throws Exception {
        // when
        entityManager.persist(student);
        Optional<Student> retrievedStudent = studentRespository.findById(student.getId());

        // then
        assertThat(retrievedStudent).contains(student);
    }

    @Test
    public void givenCurrentStudents_whenFindAllByFirstName_thenSuccess() throws Exception {
        // when
        entityManager.persist(student);
        Iterable<Student> studentList = studentRespository.findAllByFirstNameLike("Nguyen");

        // then
        assertThat(studentList).contains(student);
    }

    @Test
    public void givenCurrentStudent_whenDelete_thenSuccess() throws Exception {
        // when
        entityManager.persist(student);
        studentRespository.delete(student);

        // then
        assertThat(entityManager.find(Student.class, student.getId())).isNull();
    }

    @BeforeEach
    void setUp() {
        this.student = Student.builder()
                              .firstName("Nguyen")
                              .lastName("Tran")
                              .email("email1@gmail.com")
                              .build();
    }


}