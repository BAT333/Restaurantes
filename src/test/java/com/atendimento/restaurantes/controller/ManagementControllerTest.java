package com.atendimento.restaurantes.controller;

import com.atendimento.restaurantes.model.Management.DataReceivedManagement;
import com.atendimento.restaurantes.model.Management.Decades;
import com.atendimento.restaurantes.service.management.ManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ManagementControllerTest {
    @MockBean
    private ManagementService service;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DataReceivedManagement> jsonDto;

    @Test
    @DisplayName("""
            test which are called information certain
            """)
    @WithMockUser(roles = "BOSS")
    void get01() throws Exception {
        String json = """
                {}
                """;
        var response=   mvc.perform(
                get("/management")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(200,response.getStatus());
        System.out.println(response.getContentAsString());


    }
    @Test
    @DisplayName("""
            To give error 400
            """)
    @WithMockUser(roles = "BOSS")
    void get02() throws Exception {
        String json ="";
        var response=   mvc.perform(
                get("/management")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(400,response.getStatus());
        System.out.println(response.getContentAsString());


    }
    @Test
    @DisplayName("""
            Testing json requests
            """)
    @WithMockUser(roles = "BOSS")
    void get03() throws Exception {
        DataReceivedManagement dto = new DataReceivedManagement(Decades.DAY, LocalDate.now());
        var response=   mvc.perform(
                get("/management")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(200,response.getStatus());
        System.out.println(response.getContentAsString());
        System.out.println(response.getWriter());


    }
    @Test
    @DisplayName("""
            Testing json requests
            """)
    @WithMockUser(roles = "BOSS")
    void get04() throws Exception {
        DataReceivedManagement dto = new DataReceivedManagement(Decades.MONTH, LocalDate.now());
        var response=   mvc.perform(
                get("/management")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(200,response.getStatus());
        System.out.println(response.getContentAsString());
        System.out.println(response.getWriter());


    }
    @Test
    @DisplayName("""
            Testing json requests
            """)
    @WithMockUser(roles = "BOSS")
    void get05() throws Exception {
        DataReceivedManagement dto = new DataReceivedManagement(Decades.YEAR, LocalDate.now());
        var response=   mvc.perform(
                get("/management")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(200,response.getStatus());
        System.out.println(response.getContentAsString());
        System.out.println(response.getWriter());


    }
    @Test
    @DisplayName("""
            Testing json requests
            """)
    @WithMockUser(roles = "BOSS")
    void get06() throws Exception {
        DataReceivedManagement dto = new DataReceivedManagement(null, LocalDate.now());
        var response=   mvc.perform(
                get("/management")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(200,response.getStatus());
        System.out.println(response.getContentAsString());
        System.out.println(response.getWriter());


    }
    @Test
    @DisplayName("""
            Testing json requests
            """)
    @WithMockUser(roles = "BOSS")
    void get07() throws Exception {
        DataReceivedManagement dto = new DataReceivedManagement(Decades.DAY,null);
        var response=   mvc.perform(
                get("/management")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(200,response.getStatus());

    }


}