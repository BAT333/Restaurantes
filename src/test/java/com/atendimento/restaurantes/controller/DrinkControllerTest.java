package com.atendimento.restaurantes.controller;

import com.atendimento.restaurantes.service.Drink.DrinkService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class DrinkControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private DrinkService drinkService;


    @Test
    @WithMockUser(roles = "EMPLOYEE")
    @DisplayName("Logged in user trying to make a request without sending anything in json")
    void register01() throws Exception {
        String json = "{}";
        //ACT
        var response = mvc.perform(
                post("/drink")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
       Assertions.assertEquals(400, response.getStatus());
    }
    @Test
    @WithMockUser(roles = "EMPLOYEE")
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
                post("/drink")
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
                post("/drink")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(403, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    @DisplayName("""
            sending an update without notifying which one is to be updated
            """)
    void update01() throws Exception {
        String json = "{}";
        //ACT
        var response = mvc.perform(
                put("/drink")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(405, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    @DisplayName("""
            Updating with everything ok
            """)
    void update02() throws Exception {
        String json = """
                    {
                  "name": "USER",
                  "value": 40,
                  "description": "----"
                    }
                """;
        //ACT
        var response = mvc.perform(
                put("/drink/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    @DisplayName("""
            testing delete erro 405
            """)
    void delete01() throws Exception {
        //ACT
        var response = mvc.perform(
                delete("/drink")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(405, response.getStatus());
    }
    @Test
    @WithMockUser(roles = "EMPLOYEE")
    @DisplayName("""
            testing delete 200
            """)
    void delete02() throws Exception {
        //ACT
        var response = mvc.perform(
                delete("/drink/1")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("""
            testing get  200
            """)
    void get02() throws Exception {
        //ACT
        var response = mvc.perform(
                get("/drink/1")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
    @Test
    @DisplayName("""
            testing get  403
            """)
    void get03() throws Exception {
        //ACT
        var response = mvc.perform(
                get("/drink")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(403, response.getStatus());
    }
    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("""
            testing get list 200
            """)
    void get04() throws Exception {
        //ACT
        var response = mvc.perform(
                get("/drink")

        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

}