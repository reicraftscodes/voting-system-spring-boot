package com.lms.voting.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.voting.model.dto.UpdateUserDetailsDto;
import com.lms.voting.model.dto.UserDetailsRequestDto;
import com.lms.voting.model.entity.AccountInfo;
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
class AccountInfoControllerTests {

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
    private UserDetailsRequestDto userDetailsDto;
    private UpdateUserDetailsDto updateUserDetailsDto;


    @BeforeEach
    void setUp() {
        userDetailsDto = new UserDetailsRequestDto();
        userDetailsDto.setFirstName("John");
        userDetailsDto.setLastName("Doe");
        userDetailsDto.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userDetailsDto.setNationalInsuranceNumber("TW345679B");

        updateUserDetailsDto = new UpdateUserDetailsDto();
        updateUserDetailsDto.setId(21);
        updateUserDetailsDto.setFirstName("Jane");
        updateUserDetailsDto.setLastName("Doe");
        updateUserDetailsDto.setDateOfBirth(LocalDate.of(2000, 2, 2));
        updateUserDetailsDto.setNationalInsuranceNumber("TW345678B");

    }

    @Test
    void createNewUserTest() throws Exception {
        UserDetailsRequestDto mockResponse = new UserDetailsRequestDto();
        mockResponse.setFirstName("John");
        mockResponse.setLastName("Doe");
        mockResponse.setDateOfBirth(LocalDate.of(2000, 1, 1));
        mockResponse.setNationalInsuranceNumber("TW345679B");
        mockResponse.setId(1);

        // Mock the service to return the DTO
        when(userDetailsService.addPersonalDetails(any(UserDetailsRequestDto.class))).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockResponse)))
                .andExpect(status().isOk())  // Expect status 200 OK
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.dateOfBirth").value("2000-01-01"))
                .andExpect(jsonPath("$.nationalInsuranceNumber").value("TW345679B"));
    }

    @Test
    void getPersonalDetailsByIDTest() throws Exception {
        AccountInfo mockAccountInfo = new AccountInfo();
        mockAccountInfo.setId(1);
        mockAccountInfo.setFirstName("John");
        mockAccountInfo.setLastName("Doe");
        mockAccountInfo.setDateOfBirth(LocalDate.of(2000, 1, 1));
        mockAccountInfo.setNationalInsuranceNumber("TW345679B");

        when(userDetailsService.getPersonalDetailsByID(1)).thenReturn(Optional.of(mockAccountInfo));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Expect status 200 OK
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.dateOfBirth").value("2000-01-01"))
                .andExpect(jsonPath("$.nationalInsuranceNumber").value("TW345679B"));
    }

    @Test
    void updateUserDetailsTest() throws Exception {
        when(userDetailsService.updateUserDetails(eq(1), any(UpdateUserDetailsDto.class)))
                .thenReturn(updateUserDetailsDto);
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDetailsDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(21))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.dateOfBirth").value("2000-02-02"))
                .andExpect(jsonPath("$.nationalInsuranceNumber").value("TW345678B"));
        verify(userDetailsService).updateUserDetails(eq(1), any(UpdateUserDetailsDto.class));
    }
}

