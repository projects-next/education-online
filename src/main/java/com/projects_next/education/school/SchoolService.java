package com.projects_next.education.school;

import com.projects_next.education.school.model.School;
import com.projects_next.education.school.model.SchoolDto;
import com.projects_next.education.school.model.SchoolResponseDto;
import com.projects_next.education.student.StudentMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolService {
    private final SchoolMapper schoolMapper;
    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolMapper schoolMapper, SchoolRepository schoolRepository, StudentMapper studentMapper) {
        this.schoolMapper = schoolMapper;
        this.schoolRepository = schoolRepository;
    }

    public SchoolResponseDto saveSchool(SchoolDto schoolDto) {
        School school = School.builder().name(schoolDto.name()).build();
        School result = schoolRepository.save(school);
        return schoolMapper.mapSchoolDto(result);
    }

    public List<SchoolResponseDto> getAll() {
        return schoolRepository.findAll()
                               .stream()
                               .map(schoolMapper::mapSchoolDto)
                               .collect(Collectors.toList());
    }

    public SchoolResponseDto getById(Integer schoolId) {
        return schoolRepository
                .findById(schoolId)
                .map(schoolMapper::mapSchoolDto).orElse(null);
    }

    public List<SchoolResponseDto> getAllBySchoolName(String schoolName) {
        return schoolRepository.findByNameContaining(schoolName).stream().map(schoolMapper::mapSchoolDto)
                               .collect(Collectors.toList());
    }

    public void delete(Integer id) {
        schoolRepository.deleteById(id);
    }

    public SchoolResponseDto updateSchoolById(Integer schoolId, SchoolDto schoolDto) {
        School school = schoolRepository.findById(schoolId).get();
        school.setName(schoolDto.name());
        School result = schoolRepository.save(school);
        return  schoolMapper.mapSchoolDto(result);
    }
}
