package com.projects_next.education.course.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
//@DiscriminatorValue("V")
//@PrimaryKeyJoinColumn(name = "video_id")
//@Polymorphism(type = PolymorphismType.EXPLICIT)
public class Video extends Resource {
    private int length;
}
