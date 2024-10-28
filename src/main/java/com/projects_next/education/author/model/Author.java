package com.projects_next.education.author.model;

import com.projects_next.education.common.BaseEntity;
import com.projects_next.education.course.model.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
//@Builder
//@Table(name = "AUTHOR_TBL")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(
        name = "Author.findByNamedQuery",
        query = "select a from Author a where a.age >= :age"
)
public class Author extends BaseEntity {
    private String firstName;

    private String lastName;

    @Column(
            unique = true
    )
    private String email;

    private int age;

    @Column(
            updatable = false
    )
    private LocalDateTime createdAt;

    @Column(
            insertable = false
    )
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    private List<Course> courses;
}
