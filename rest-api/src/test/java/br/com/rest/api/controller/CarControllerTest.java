package br.com.rest.api.controller;

import br.com.domain.entity.Car;
import br.com.domain.entity.dto.CarDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String id;

    private final CarDTO carDTO = CarDTO.builder()
            .name("nameCarTest")
            .brand("brandCarTest")
            .build();

    @Test
    void listAll() throws Exception {
        mockMvc.perform(get("/car")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void insert() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDTO))
                ).andExpect(status().isOk())
                .andReturn();

        Car carReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Car.class);

        id = carReturn.getId();

        Assertions.assertNotNull(id);
        Assertions.assertEquals(carReturn.getName(), "nameCarTest");
        Assertions.assertEquals(carReturn.getBrand(), "brandCarTest");
    }

    @Test
    void update() throws Exception {
        carDTO.setName("nameCarTestUpdate");
        carDTO.setBrand("brandCarTestUpdate");

        MvcResult mvcResult = mockMvc.perform(put("/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDTO))
                        .param("id", id)
                ).andExpect(status().isOk())
                .andReturn();

        Car carReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Car.class);

        Assertions.assertNotNull(id);
        Assertions.assertEquals(carReturn.getId(), id);
        Assertions.assertEquals(carReturn.getName(), "nameCarTestUpdate");
        Assertions.assertEquals(carReturn.getBrand(), "brandCarTestUpdate");
    }

    @Test
    void findById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDTO))
                        .param("id", id)
                ).andExpect(status().isOk())
                .andReturn();

        Car carReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Car.class);

        Assertions.assertNotNull(id);
        Assertions.assertEquals(carReturn.getId(), id);
        Assertions.assertEquals(carReturn.getName(), "nameCarTestUpdate");
        Assertions.assertEquals(carReturn.getBrand(), "brandCarTestUpdate");
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/car", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDTO))
                ).andExpect(status().isOk());
    }


}
