package com.projects_next.education.profile.model;

import com.projects_next.education.student.model.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue
    private Integer id;

    private String bio;

    @OneToOne
    @MapsId
    @JoinColumn(
            name = "student_id"
    )
    private Student student;
}
