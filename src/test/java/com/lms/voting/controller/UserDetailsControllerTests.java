package com.lms.voting.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.voting.dto.UpdateUserDetailsDto;
import com.lms.voting.entity.UserDetails;
import com.lms.voting.service.UserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WebMvcTest(UserDetailsController.class)
class UserDetailsControllerTests {

    /**
     * Simulates HTTP requests to controller endpoints without starting the server
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Converts Java objects to JSON (Serialization) and JSON to Java objects (Deserialization) for requests/responses
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Mocks UserDetailsService to avoid calling real service or database
     */
    @MockBean
    private UserDetailsService userDetailsService;

    private UserDetails userDetails;
    private UpdateUserDetailsDto updateUserDetailsDto;


    @BeforeEach
    void setUp() {
        userDetails = new UserDetails();
        userDetails.setId(1);
        userDetails.setFirstName("John");
        userDetails.setLastName("Doe");
        userDetails.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userDetails.setNationalInsuranceNumber("CS200001S");

        updateUserDetailsDto = new UpdateUserDetailsDto();
        updateUserDetailsDto.setId(1);
        updateUserDetailsDto.setFirstName("Jane");
        updateUserDetailsDto.setLastName("Doe");
        updateUserDetailsDto.setDateOfBirth(LocalDate.of(2000, 2, 2));
        updateUserDetailsDto.setNationalInsuranceNumber("12345667");

    }

    @Test
    void createNewUserTest() throws Exception {

        when(userDetailsService.addPersonalDetails(any(UserDetails.class))).thenReturn(userDetails);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.dateOfBirth").value("2000-01-01"))
                .andExpect(jsonPath("$.nationalInsuranceNumber").value("CS200001S"));
    }

    @Test
    void getPersonalDetailsByIDTest() throws Exception {

        when(userDetailsService.getPersonalDetailsByID(userDetails.getId())).thenReturn(Optional.of(userDetails));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.dateOfBirth").value("2000-01-01"))
                .andExpect(jsonPath("$.nationalInsuranceNumber").value("CS200001S"));

    }

    @Test
    void updateUserDetailsTest() throws Exception {
        when(userDetailsService.updateUserDetails(eq(1), any(UpdateUserDetailsDto.class)))
                .thenReturn(updateUserDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDetailsDto)))
                .andExpect(status().isOk())
                // Verify the response JSON matches our expected updated values
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.dateOfBirth").value("2000-02-02"))
                .andExpect(jsonPath("$.nationalInsuranceNumber").value("12345667"));

        verify(userDetailsService).updateUserDetails(eq(1), any(UpdateUserDetailsDto.class));
    }
}

