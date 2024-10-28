package com.projects_next.education.school;

import com.projects_next.education.school.model.SchoolDto;
import com.projects_next.education.school.model.SchoolResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schools")
public class SchoolController {
    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping({"/v1.0"})
    public SchoolResponseDto newSchool(
            @RequestBody SchoolDto schoolDto
    ) {
        return schoolService.saveSchool(schoolDto);
    }

    @PutMapping({"/v1.0/{school-id}"})
    public SchoolResponseDto updateSchool(
            @RequestBody SchoolDto schoolDto,
            @PathVariable("school-id") Integer schoolId
    ) {
        return schoolService.updateSchoolById(schoolId, schoolDto);
    }

    @GetMapping({"/v1.0"})
    public List<SchoolResponseDto> findAllSchools() {
        return schoolService.getAll();
    }

    @GetMapping({"/v1.0/{school-id}"})
    public SchoolResponseDto findById(
            @PathVariable("school-id") Integer schoolId
    ) {
        return schoolService.getById(schoolId);
    }

    @GetMapping({"/v1.0/search"})
    public List<SchoolResponseDto> findAllBySchoolName(
            @RequestParam(name = "name") String schoolName
    ){
        return schoolService.getAllBySchoolName(schoolName);
    }

    @DeleteMapping({"/v1.0/{school-id}"})
    public void deleteId(
            @PathVariable("school-id") Integer id
    ) {
        schoolService.delete(id);
    }
}
