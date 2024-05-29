package com.atendimento.restaurantes.controller;

import com.atendimento.restaurantes.model.food.DataFood;
import com.atendimento.restaurantes.service.food.DishFoodService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Role;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class FoodControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private DishFoodService service;
    @Autowired
    private JacksonTester<DataFood> tester;


    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("Logged in user trying to make a request without sending anything in json")
    void register01() throws Exception {
        String json = "{}";
        //ACT
        var response = mvc.perform(
                post("/food")
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
        String json = """
                {
                  "name": "USER",
                  "value": 40,
                  "description": "----"
                }
                """;

        //ACT
        var response = mvc.perform(
                post("/food")
                        .content(json)
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
                post("/food")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(403, response.getStatus());
    }
    @Test
    @DisplayName("""
            Testing food update erro 405
                        
            """)
    @WithMockUser(roles = "BOSS")
    void update01() throws Exception {
        String json = "{}";
        var response = mvc.perform(
                put("/food")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(405,response.getStatus());
    }
    @Test
    @DisplayName("""
            Testing food update erro 200
                        
            """)
    @WithMockUser(roles = "BOSS")
    void update02() throws Exception {
        String json = "{}";
        var response = mvc.perform(
                put("/food/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200,response.getStatus());
    }
    @Test
    @DisplayName("""
            Testing update about food by passing arguments                  
            """)
    @WithMockUser(roles = "BOSS")
    void update03() throws Exception {
        DataFood food = new DataFood("A", BigDecimal.ZERO, "B");
        var response = mvc.perform(
                put("/food/1")
                        .content(tester.write(food).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200,response.getStatus());
    }
    @Test
    @DisplayName("""
            Testing update about food by passing arguments                  
            """)
    @WithMockUser(roles = "BOSS")
    void update04() throws Exception {
        DataFood food = new DataFood("A", null, null);
        var response = mvc.perform(
                put("/food/1")
                        .content(tester.write(food).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200,response.getStatus());
    }
    @Test
    @DisplayName("""
            No json                
            """)
    @WithMockUser(roles = "BOSS")
    void update05() throws Exception {
        String json ="";
        var response = mvc.perform(
                put("/food/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400,response.getStatus());
    }
    @Test
    @DisplayName("""
            delete erro 400                
            """)
    @WithMockUser(roles = "BOSS")
    void delete01() throws Exception {
        String json ="";
        var response = mvc.perform(
                delete("/food")

        ).andReturn().getResponse();

        Assertions.assertEquals(405,response.getStatus());
    }
    @Test
    @DisplayName("""
            delete 200                
            """)
    @WithMockUser(roles = "BOSS")
    void delete02() throws Exception {
        var response = mvc.perform(
                delete("/food/1")

        ).andReturn().getResponse();

        Assertions.assertEquals(200,response.getStatus());
    }
    @Test
    @DisplayName("""
            get 200                
            """)
    @WithMockUser(roles = "BOSS")
    void get01() throws Exception {
        var response = mvc.perform(
                get("/food/1")

        ).andReturn().getResponse();

        Assertions.assertEquals(200,response.getStatus());
    }
    @Test
    @DisplayName("""
            get 200                
            """)
    @WithMockUser(roles = "BOSS")

    void get02() throws Exception {
        var response = mvc.perform(
                get("/food")

        ).andReturn().getResponse();

        Assertions.assertEquals(200,response.getStatus());
    }
    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("if you have the wrong role")
    void role() throws Exception {
        //ARRANGE
        String json = """
                {
                  "name": "USER",
                  "value": 40,
                  "description": "----"
                }
                """;

        //ACT
        var response = mvc.perform(
                post("/food")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(403, response.getStatus());
    }
    @Test
    @WithMockUser(roles = "EMPLOYEE")
    @DisplayName("if you have the wrong role")
    void role02() throws Exception {
        //ARRANGE
        String json = """
                {
                  "name": "USER",
                  "value": 40,
                  "description": "----"
                }
                """;

        //ACT
        var response = mvc.perform(
                post("/food")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(403, response.getStatus());
    }


}