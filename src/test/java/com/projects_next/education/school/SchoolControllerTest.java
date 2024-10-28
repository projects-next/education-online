package com.projects_next.education.school;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects_next.education.school.model.School;
import com.projects_next.education.school.model.SchoolDto;
import com.projects_next.education.school.model.SchoolResponseDto;
import com.projects_next.education.student.model.Student;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class SchoolControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SchoolService schoolService;
    @Autowired
    private SchoolMapper schoolMapper;
    private School school;
    private SchoolDto schoolDto;
    private SchoolResponseDto newSchool;
    private SchoolResponseDto schoolResponseDto;
    private List<SchoolResponseDto> schoolList;

    @Test
    public void givenWac_whenServletContext_thenItProvidesSchoolController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("schoolController"));
    }

    @Test
    public void givenSchoolURI_whenCreateSchool_thenReturnNewSchool() throws Exception {
        // when
        Mockito.when(schoolService.saveSchool(schoolDto)).thenReturn(schoolResponseDto);

        String bodyJson = objectMapper.writeValueAsString(schoolDto);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/schools/v1.0")
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .content(bodyJson))
                                  .andDo(MockMvcResultHandlers.print())
                                  .andReturn();
        // then
        String jsonResult = result.getResponse().getContentAsString();
        SchoolResponseDto schoolResponse = objectMapper.readValue(jsonResult, SchoolResponseDto.class);

        assertNotNull(schoolResponse);
        assertEquals("Nguyen Test", schoolResponse.name());
        assertEquals(1, schoolResponse.students().size());
        assertEquals("Test", schoolResponse.students().get(0).lastName());
    }

    @Test
    public void givenSchoolURI_whenUpdateSchool_thenReturnNewProperty() throws Exception {
        //when
        Mockito.when(schoolService.updateSchoolById(1, schoolDto)).thenReturn(newSchool);

        String bodyJson = objectMapper.writeValueAsString(schoolDto);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/schools/v1.0/1")
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .content(bodyJson))
                                  .andDo(MockMvcResultHandlers.print())
                                  .andExpect(MockMvcResultMatchers.status().isOk())
                                  .andReturn();

        //then
        String jsonResult = result.getResponse().getContentAsString();
        SchoolResponseDto schoolResponse = objectMapper.readValue(jsonResult, SchoolResponseDto.class);

        assertEquals("New Name", schoolResponse.name());
        assertEquals(1, schoolResponse.students().size());
    }

    @Test
    public void givenSchoolURI_whenGetAllSchools_thenReturnListSchools() throws Exception {
        //when
        Mockito.when(schoolService.getAll()).thenReturn((List) schoolList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/schools/v1.0"))
                                  .andDo(MockMvcResultHandlers.print())
                                  .andExpect(MockMvcResultMatchers.status().isOk())
                                  .andReturn();
        // then
        String jsonResult = result.getResponse().getContentAsString();
        List<School> schoolResults = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        assertNotNull(schoolResults);
        assertEquals(1, schoolResults.size());
        assertEquals(1, schoolResults.get(0).getStudents().size());
        assertEquals("Nguyen Test", schoolResults.get(0).getName());
        assertEquals("Test", schoolResults.get(0).getStudents().get(0).getLastName());
    }

    @Test
    public void givenSchoolURI_whenGetSchoolById_thenReturnExistingSchool() throws Exception {
        //when
        Mockito.when(schoolService.getById(1)).thenReturn(schoolResponseDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/schools/v1.0/1"))
                                  .andDo(MockMvcResultHandlers.print())
                                  .andExpect(MockMvcResultMatchers.status().isOk())
                                  .andReturn();
        //then
        String jsonResult = result.getResponse().getContentAsString();
        SchoolResponseDto response = objectMapper.readValue(jsonResult, SchoolResponseDto.class);

        assertNotNull(response);
        assertEquals("Nguyen Test", response.name());
        assertEquals(1, response.students().size());
    }

    @Test
    public void givenSchoolURI_whenGetSchoolByName_thenReturnExistingSchool() throws Exception {
        //when
        Mockito.when(schoolService.getAllBySchoolName("Nguyen")).thenReturn(schoolList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/schools/v1.0/search")
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .param("name", "Nguyen"))
                                  .andDo(MockMvcResultHandlers.print())
                                  .andExpect(MockMvcResultMatchers.status().isOk())
                                  .andReturn();

        //then
        String jsonResult = result.getResponse().getContentAsString();
        List<SchoolResponseDto> response = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Nguyen Test", response.get(0).name());
    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Student student = Student.builder()
                                 .id(1)
                                 .firstName("Nguyen")
                                 .lastName("Test")
                                 .age(13)
                                 .school(School.builder().build())
                                 .build();
        List<Student> students = new ArrayList<>();
        students.add(student);
        this.school = School.builder()
                            .id(1)
                            .name("Nguyen Test")
                            .students(students)
                            .build();
        this.schoolDto = SchoolDto.builder()
                                  .name("Nguyen Test")
                                  .build();
        this.newSchool = schoolMapper.mapSchoolDto(School.builder()
                                                         .id(1)
                                                         .name("New Name")
                                                         .students(students)
                                                         .build());
        this.schoolResponseDto = schoolMapper.mapSchoolDto(school);
        this.schoolList = new ArrayList<>();
        schoolList.add(schoolMapper.mapSchoolDto(school));
    }
}