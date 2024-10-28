package com.projects_next.education.student.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projects_next.education.profile.model.Profile;
import com.projects_next.education.school.model.School;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "school")
public class Student {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Column(unique = true)
    private String email;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Transient
    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    @PrimaryKeyJoinColumn
    private Profile profile;

    @ManyToOne
    @JoinColumn(
            name = "school_id"
    )
    @JsonBackReference
    private School school;
}
