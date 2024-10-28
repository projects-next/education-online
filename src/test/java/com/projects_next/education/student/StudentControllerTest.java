package com.projects_next.education.student;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects_next.education.school.model.School;
import com.projects_next.education.student.model.Student;
import com.projects_next.education.student.model.StudentDto;
import com.projects_next.education.student.model.StudentResponseDto;
import com.projects_next.education.student.model.StudentUpdatePartialDto;
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
class StudentControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;
    private Student student;
    private StudentDto studentDto;
    private StudentResponseDto studentResponseDto;
    private List<StudentResponseDto> studentList;

    @Test
    public void givenWac_whenServletContext_thenItProvidesStudentController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("studentController"));
    }

    @Test
    public void givenStudentsURI_whenGetAllStudents_thenReturnListStudents() throws Exception {
        // when
        Mockito.when(studentService.getAll()).thenReturn((List) studentList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/students/v1.0"))
                                  .andDo(MockMvcResultHandlers.print())
                                  .andExpect(MockMvcResultMatchers.status().isOk())
                                  .andReturn();

        // then
        String jsonResult = result.getResponse().getContentAsString();
        List<Student> studentResults = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        assertNotNull(studentResults);
        assertEquals(1, studentResults.size());
    }

    @Test
    public void givenStudentURI_whenGetStudentById_thenReturnStudent() throws Exception {
        // when
        Mockito.when(studentService.getById(3)).thenReturn(studentResponseDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/students/v1.0/3"))
                                  .andDo(MockMvcResultHandlers.print())
                                  .andExpect(MockMvcResultMatchers.status().isOk())
                                  .andReturn();

        // then
        String jsonResult = result.getResponse().getContentAsString();
        StudentResponseDto studentResult = objectMapper.readValue(jsonResult, StudentResponseDto.class);

        assertNotNull(studentResult);
        assertEquals("Nguyen", studentResult.firstName());
        assertEquals("Tran", studentResult.lastName());
        assertEquals("email1@gmail.com", studentResult.email());
    }

    @Test
    public void givenStudentURI_whenGetStudentById_thenReturnNull() throws Exception {
        // when
        Mockito.when(studentService.getById(2)).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/students/v1.0/2"))
                                  .andDo(MockMvcResultHandlers.print())
                                  .andExpect(MockMvcResultMatchers.status().isOk())
                                  .andReturn();
        // then
        String jsonResult = result.getResponse().getContentAsString();

        assertEquals("", jsonResult);
    }

    @Test
    public void givenStudentURI_whenGetStudentByFirstname_thenReturnStudentList() throws Exception {
        // when
        Mockito.when(studentService.getAllByFirstName("Nguyen")).thenReturn((List) studentList);

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/students/v1.0/search").contentType(MediaType.APPLICATION_JSON)
                                                      .param("name", "Nguyen"))
                       .andDo(MockMvcResultHandlers.print())
                       .andExpect(MockMvcResultMatchers.status().isOk())
                       .andReturn();
        // then
        String jsonResult = result.getResponse().getContentAsString();
        List<StudentDto> studentResults = objectMapper.readValue(jsonResult, new TypeReference<List<StudentDto>>() {
        });

        assertNotNull(studentResults);
        assertEquals(1, studentResults.size());
    }

    @Test
    public void givenStudentURI_whenCreateStudent_thenReturnNewStudent() throws Exception {
        // when
        Mockito.when(studentService.saveStudent(Mockito.any(StudentDto.class)))
               .thenReturn(studentMapper.mapStudentResponseDto(student));

        String bodyJson = objectMapper.writeValueAsString(studentDto);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/students/v1.0")
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .content(bodyJson))
                                  .andDo(MockMvcResultHandlers.print())
                                  .andExpect(MockMvcResultMatchers.status().isOk())
                                  .andReturn();

        // then
        String jsonResult = result.getResponse().getContentAsString();
        StudentResponseDto studentResult = objectMapper.readValue(jsonResult, StudentResponseDto.class);

        assertNotNull(studentResult);
        assertEquals("Tran", studentResult.lastName());
        assertEquals("Nguyen", studentResult.firstName());
        assertEquals("email1@gmail.com", studentResult.email());

        Mockito.verify(studentService, Mockito.times(1)).saveStudent(studentDto);
    }

    @Test
    public void givenStudentURI_whenUpdateStudent_thenReturnNewStudent() throws Exception {
        // when
        Mockito.when(studentService.updateStudentById(1, studentDto)).thenReturn(studentResponseDto);

        String bodyJson = objectMapper.writeValueAsString(studentDto);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/students/v1.0/1")
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .content(bodyJson))
                                  .andDo(MockMvcResultHandlers.print())
                                  .andExpect(MockMvcResultMatchers.status().isOk())
                                  .andReturn();

        // then
        String jsonResult = result.getResponse().getContentAsString();
        StudentResponseDto studentResult = objectMapper.readValue(jsonResult, StudentResponseDto.class);

        assertNotNull(studentResult);
        assertEquals("Tran", studentResult.lastName());
        assertEquals("Nguyen", studentResult.firstName());
        assertEquals("email1@gmail.com", studentResult.email());

        Mockito.verify(studentService, Mockito.times(1)).updateStudentById(1, studentDto);
    }

    @Test
    public void givenStudentURI_whenUpdatePartialStudent_thenReturnNewValueStudent() throws Exception {
        // when
        StudentUpdatePartialDto updateStudent = StudentUpdatePartialDto.builder()
                                                                       .firstName("New Name")
                                                                       .lastName("Tran")
                                                                       .email("email1@gmail.com").build();
        Mockito.when(studentService.updatePartialStudentById(1, updateStudent))
               .thenReturn(StudentResponseDto.builder().firstName("New Name").build());

        String bodyJson = objectMapper.writeValueAsString(updateStudent);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/students/v1.0/1")
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .content(bodyJson))
                                  .andDo(MockMvcResultHandlers.print())
                                  .andExpect(MockMvcResultMatchers.status().isOk())
                                  .andReturn();

        // then
        String jsonResult = result.getResponse().getContentAsString();
        StudentResponseDto studentResult = objectMapper.readValue(jsonResult, StudentResponseDto.class);

        assertNotNull(studentResult);
        assertEquals("New Name", studentResult.firstName());
        Mockito.verify(studentService, Mockito.times(1)).updatePartialStudentById(1, updateStudent);
    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
       /* @AutoConfigureMockMvc handling
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();*/
        this.student = Student.builder()
                              .firstName("Nguyen")
                              .lastName("Tran")
                              .email("email1@gmail.com")
                              .school(School.builder().build())
                              .id(1)
                              .build();
        this.studentDto = StudentDto.builder()
                                    .firstName("Nguyen")
                                    .lastName("Tran")
                                    .email("email1@gmail.com")
                                    .schoolId(1)
                                    .build();
        this.studentResponseDto = this.studentMapper.mapStudentResponseDto(student);
        this.studentList = new ArrayList<>();
        this.studentList.add(studentResponseDto);
    }
}