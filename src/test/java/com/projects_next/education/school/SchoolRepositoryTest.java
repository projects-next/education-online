package com.projects_next.education.school;

import com.projects_next.education.school.model.School;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SchoolRepositoryTest {
    @Autowired
    SchoolRepository schoolRepository;
    @Autowired
    TestEntityManager entityManager;
    private School school;

    @BeforeEach
    void setUp() {
        this.school = School.builder()
                            .name("Truong Nguyen Thi Minh Khai")
                            .students(Arrays.asList())
                            .build();
    }

    @Test
    void givenNewSchool_whenSave_thenSuccess() throws Exception {
        // When
        School newSchool = schoolRepository.save(school);

        // Then
        assertThat(entityManager.find(School.class, newSchool.getId())).isEqualTo(school);
    }

    @Test
    void givenCurrentSchool_whenUpdate_thenSuccess() throws Exception {
        // Given
        String newName = "Truong Cao Dang Cong Nghiep";

        // When
        entityManager.persist(school);
        school.setName(newName);
        schoolRepository.save(school);

        // Then
        assertThat(entityManager.find(School.class, school.getId()).getName()).isEqualTo(newName);
    }

    @Test
    void givenCurrentSchool_whenFindById_thenSuccess() throws Exception {
        // When
        entityManager.persist(school);
        Optional<School> retrievedSchool = schoolRepository.findById(school.getId());

        // Then
        assertThat(retrievedSchool).contains(school);
    }

    @Test
    void givenCurrentSchools_whenFindByNameContaining_thenSuccess() throws Exception {
        // When
        entityManager.persist(school);
        Iterable<School> schoolList = schoolRepository.findByNameContaining("Minh Khai");

        // Then
        assertThat(schoolList).contains(school);
    }

    @Test
    void givenCurrentSchool_whenDelete_thenSuccess() throws Exception {
        // When
        entityManager.persist(school);
        schoolRepository.delete(school);

        // Then
        assertThat(entityManager.find(School.class, school.getId())).isNull();
    }
}