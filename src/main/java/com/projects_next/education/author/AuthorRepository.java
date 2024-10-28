package com.projects_next.education.author;

import com.projects_next.education.author.model.Author;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AuthorRepository extends JpaRepository<Author, Integer>,
        JpaSpecificationExecutor<Author>
{
    @Transactional
    List<Author> findByNamedQuery(@Param("age") int age);

//    @Modifying
//    @Transactional
//    @Query("update author a set a.age = :age where a.id = :id")
//    int updateAuthor(int age, int id);

    List<Author> findAllByFirstName(String name);
}
