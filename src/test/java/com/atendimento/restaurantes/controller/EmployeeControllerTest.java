package com.atendimento.restaurantes.controller;

import com.atendimento.restaurantes.model.Employee.DataAddress;
import com.atendimento.restaurantes.model.Employee.DataFunction;
import com.atendimento.restaurantes.model.Employee.DataRegisterEmployee;
import com.atendimento.restaurantes.model.FunctionEmployee.Workspace;
import com.atendimento.restaurantes.service.Employee.EmployeeService;
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

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class EmployeeControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmployeeService service;
    @Autowired
    private JacksonTester<DataRegisterEmployee> tester;

    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("Logged in user trying to make a request without sending anything in json")
    void register01() throws Exception {
        String json = "{}";
        //ACT
        var response = mvc.perform(
                post("/employee")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }
    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("Logged in user trying to make a request")
    void register02() throws Exception {
        //ARRANGE
        DataRegisterEmployee registerEmployee = new DataRegisterEmployee("a","5", LocalDate.now().withYear(5),
                new DataFunction(Workspace.BARMANAGER,"A", BigDecimal.ZERO),
                new DataAddress("1","s","d","R","s","x",1),"4552");

        //ACT
        var response = mvc.perform(
                post("/employee")
                        .content(tester.write(registerEmployee).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
    @Test
    @DisplayName("USER NOT LOGGED IN")
    void register03() throws Exception {
        //ARRANGE
        String json = """
                
                """;

        //ACT
        var response = mvc.perform(
                post("/employee")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(403, response.getStatus());
    }
    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("ERRO UPDATE 405")
    void update01() throws Exception {
        //ARRANGE
        String json = """
                
                """;

        //ACT
        var response = mvc.perform(
                put("/employee")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(405, response.getStatus());
    }
    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("ERRO UPDATE 200")
    void update02() throws Exception {
        //ARRANGE
        String json = """
                {}
                """;

        //ACT
        var response = mvc.perform(
                put("/employee/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("ERRO DELETE 405")
    void delete01() throws Exception {

        var response = mvc.perform(
                put("/employee")

        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(405, response.getStatus());
    }
    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("DELETE 200")
    void delete02() throws Exception {


        //ACT
        var response = mvc.perform(
                delete("/employee/1")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("get 200")
    void get01() throws Exception {


        //ACT
        var response = mvc.perform(
                get("/employee/1")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
    @Test
    @WithMockUser(roles = "USER")
    @DisplayName(
            "if you have the wrong role")
    void role01() throws Exception {
        String json = "{}";
        //ACT
        var response = mvc.perform(
                post("/employee")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(403, response.getStatus());
    }
}