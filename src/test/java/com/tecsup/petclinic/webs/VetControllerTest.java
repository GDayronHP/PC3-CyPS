package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.PetTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j

public class VetControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    /**
     *
     * @throws Exception
     */
    @Test
    public void testDeleteVet() throws Exception {

        String FIRST_NAME = "Maximo";
        String LAST_NAME = "Ventos";

        VetTO newVetTO = new VetTO();
        newVetTO.setFirstName(FIRST_NAME);
        newVetTO.setLastName(LAST_NAME);

        ResultActions mvcActions = mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVetTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();

        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/vets/" + id ))
                .andExpect(status().isOk());
    }


}