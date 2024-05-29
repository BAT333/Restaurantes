package com.atendimento.restaurantes.controller;

import com.atendimento.restaurantes.service.Order.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private OrderService orderService;

    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("Logged in user trying to make a request without sending anything in json")
    void register01() throws Exception {
        String json = "{}";
        //ACT
        var response = mvc.perform(
                post("/demand/1")
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
                    "name": "GRANDE TESTE",
                    "Drinks": [
                        {
                            "idDrink": 1,
                            "quantityDrink": 2
                        },
                        {
                            "idDrink": 1,
                            "quantityDrink": 1
                        }
                        ,
                        {
                            "idDrink": 1,
                            "quantityDrink": 1
                        }
                       \s
                    ],
                    "foods": [
                        {
                            "idFood": 1,
                            "quantityFood": 1
                        },
                        {
                            "idFood": 1,
                            "quantityFood": 2
                        },
                        {
                            "idFood": 1,
                            "quantityFood": 2
                        },
                        {
                            "idFood": 1,
                            "quantityFood": 2
                        },
                        {
                            "idFood": 1,
                            "quantityFood": 2
                        }
                    ],
                    "description": "Example Description",
                    "idOrderTotal": 789
                }
                """;

        //ACT
        var response = mvc.perform(
                post("/demand/1")
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
                post("/demand/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(403, response.getStatus());
    }



    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("Update with status 200")
    void update01() throws Exception {
        //ARRANGE
        String json = """
                {}
                """;

        //ACT
        var response = mvc.perform(
                put("/demand/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("Update with status 400")
    void update02() throws Exception {
        //ARRANGE
        String json = """
                
                """;

        //ACT
        var response = mvc.perform(
                put("/demand/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }
    @Test
    @DisplayName("Update with status 403")
    void update03() throws Exception {
        //ARRANGE
        String json = """
                
                """;

        //ACT
        var response = mvc.perform(
                put("/demand/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(403, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("delete with status 200")
    void delete01() throws Exception {
        //ARRANGE

        //ACT
        var response = mvc.perform(
                delete("/demand/1")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("delete with status 405")
    void delete02() throws Exception {
        //ARRANGE

        //ACT
        var response = mvc.perform(
                delete("/demand")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(405, response.getStatus());
    }


    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("get with status 200")
    void get01() throws Exception {
        //ARRANGE

        //ACT
        var response = mvc.perform(
                get("/demand")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @WithMockUser(roles = "BOSS")
    @DisplayName("get with status 200")
    void get02() throws Exception {
        //ARRANGE

        //ACT
        var response = mvc.perform(
                get("/demand/2")
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }


}