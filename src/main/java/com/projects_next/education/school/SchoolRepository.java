package com.projects_next.education.school;

import com.projects_next.education.school.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {
    List<School> findByNameContaining(String schoolName);
}
