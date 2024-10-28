package com.projects_next.education.school;

import com.projects_next.education.school.model.School;
import com.projects_next.education.school.model.SchoolDto;
import com.projects_next.education.school.model.SchoolResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

class SchoolServiceTest {
    @InjectMocks
    private SchoolService schoolService;
    @Mock
    private SchoolRepository repository;
    @Mock
    private SchoolMapper mapper;

    private SchoolDto schoolDto;
    private School school;
    private SchoolResponseDto schoolResponseDto;
    private List<School> schoolList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Given
        this.schoolDto = SchoolDto.builder()
                                  .name("Truong Nguyen Thi Minh Khai")
                                  .build();
        this.school = School.builder()
                            .name("Truong Nguyen Thi Minh Khai")
                            .students(Arrays.asList())
                            .id(1)
                            .build();
        this.schoolResponseDto = SchoolResponseDto.builder()
                                                  .name("Truong Nguyen Thi Minh Khai")
                                                  .students(Arrays.asList())
                                                  .build();
        this.schoolList = new ArrayList<>();
        schoolList.add(school);
    }

    @Test
    void shouldSuccessfullySaveASchool() {
        // Given
        Mockito.when(repository.save(Mockito.any(School.class))).thenReturn(school);
        Mockito.when(mapper.mapSchoolDto(Mockito.any(School.class))).thenReturn(schoolResponseDto);

        // When
        SchoolResponseDto result = schoolService.saveSchool(schoolDto);

        // Then
        assertEquals("Truong Nguyen Thi Minh Khai", result.name());
        verify(mapper, Mockito.times(1)).mapSchoolDto(Mockito.any(School.class));
        verify(repository, Mockito.times(1)).save(Mockito.any(School.class));
    }

    @Test
    void shouldSuccessfullyReturnAllSchools() {
        // Given
        Mockito.when(repository.findAll()).thenReturn(schoolList);
        Mockito.when(mapper.mapSchoolDto(Mockito.any(School.class))).thenReturn(schoolResponseDto);

        // When
        List<SchoolResponseDto> result = schoolService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(1, schoolList.size());
    }

    @Test
    void shouldSuccessfullyReturnSchoolById() {
        // Given
        Mockito.when(repository.findById(1)).thenReturn(Optional.ofNullable(school));
        Mockito.when(mapper.mapSchoolDto(school)).thenReturn(schoolResponseDto);

        // When
        SchoolResponseDto result = schoolService.getById(1);

        //Then
        assertNotNull(result);
        assertEquals("Truong Nguyen Thi Minh Khai", result.name());
    }

    @Test
    void shouldSuccessfullyReturnAllBySchoolName() {
        // Given
        Mockito.when(repository.findByNameContaining("Nguyen Thi")).thenReturn(schoolList);
        Mockito.when(mapper.mapSchoolDto(school)).thenReturn(schoolResponseDto);

        // When
        List<SchoolResponseDto> result = schoolService.getAllBySchoolName("Nguyen Thi");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void shouldSuccessfullyUpdateSchoolById() {
        // Given
        Mockito.when(repository.findById(1)).thenReturn(Optional.ofNullable(school));
        Mockito.when(repository.save(school)).thenReturn(school);
        Mockito.when(mapper.mapSchoolDto(school)).thenReturn(schoolResponseDto);

        // When
        SchoolResponseDto result = schoolService.updateSchoolById(1, schoolDto);

        // Then
        assertNotNull(result);
        assertEquals("Truong Nguyen Thi Minh Khai", result.name());
        verify(repository, Mockito.times(1)).findById(1);
        verify(repository, Mockito.times(1)).save(school);
        verify(mapper, Mockito.times(1)).mapSchoolDto(school);
    }
}