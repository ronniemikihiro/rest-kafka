package br.com.rest.api.controller;

import br.com.domain.entity.User;
import br.com.domain.entity.dto.UserDTO;
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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String id;

    private final UserDTO userDTO = UserDTO.builder()
            .name("nameUserTest")
            .age(20)
            .build();

    @Test
    void listAll() throws Exception {
        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void insert() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO))
                ).andExpect(status().isOk())
                .andReturn();

        User userReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        id = userReturn.getId();

        Assertions.assertNotNull(id);
        Assertions.assertEquals(userReturn.getName(), "nameUserTest");
        Assertions.assertEquals(userReturn.getAge(), 20);
    }

    @Test
    void update() throws Exception {
        userDTO.setName("nameUserTestUpdate");
        userDTO.setAge(30);

        MvcResult mvcResult = mockMvc.perform(put("/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO))
                        .param("id", id)
                ).andExpect(status().isOk())
                .andReturn();

        User userReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        Assertions.assertNotNull(id);
        Assertions.assertEquals(userReturn.getId(), id);
        Assertions.assertEquals(userReturn.getName(), "nameUserTestUpdate");
        Assertions.assertEquals(userReturn.getAge(), 30);
    }

    @Test
    void findById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO))
                        .param("id", id)
                ).andExpect(status().isOk())
                .andReturn();

        User userReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        Assertions.assertNotNull(id);
        Assertions.assertEquals(userReturn.getId(), id);
        Assertions.assertEquals(userReturn.getName(), "nameUserTestUpdate");
        Assertions.assertEquals(userReturn.getAge(), 30);
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/user", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO))
                ).andExpect(status().isOk());
    }


}
