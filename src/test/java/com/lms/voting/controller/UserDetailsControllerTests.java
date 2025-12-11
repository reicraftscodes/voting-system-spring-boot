package com.lms.voting.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.voting.dto.UpdateUserDetailsDto;
import com.lms.voting.entity.UserDetails;
import com.lms.voting.service.UserDetailsService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WebMvcTest(UserDetailsController.class)// testing the web layer (controller) without loading the entire Spring context.
class UserDetailsControllerTests {

    //This allows you to simulate HTTP requests to your controller and check the response.
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // to mock service layer responses without calling the real service.
    // Also, service layer beans are not included in the application context.
    // It provides @AutoConfigureMockMvc by default
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void createNewUserTest() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(1);
        userDetails.setFirstName("May");
        userDetails.setLastName("San");
        userDetails.setDateOfBirth(LocalDate.of(1990, 3, 28));
        userDetails.setNationalInsuranceNumber("123455");

        // the any() means accept anything of this type. mockito will match whatever object the method receives.
        when(userDetailsService.addPersonalDetails(any(UserDetails.class))).thenReturn(userDetails);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/person-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDetails)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("May"))
                .andExpect(jsonPath("$.lastName").value("San"))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-03-28"))
                .andExpect(jsonPath("$.nationalInsuranceNumber").value("123455"));
    }

    @Test
    void getPersonalDetailsByIDTest() throws Exception {
        int userId = 1;

        UserDetails userDetailsInfo = new UserDetails();
        userDetailsInfo.setId(userId);
        userDetailsInfo.setFirstName("John");
        userDetailsInfo.setLastName("Doe");
        userDetailsInfo.setDateOfBirth(LocalDate.of(1990, 3, 27));
        userDetailsInfo.setNationalInsuranceNumber("12345667");

        when(userDetailsService.getPersonalDetailsByID(userDetailsInfo.getId())).thenReturn(Optional.of(userDetailsInfo));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/person-details/members/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-03-27"))
                .andExpect(jsonPath("$.nationalInsuranceNumber").value("12345667"));

    }

    @Test
    void updateUserDetailsTest() throws Exception {
        int userId = 1;


        UpdateUserDetailsDto updateUserDetailsDto = new UpdateUserDetailsDto();
        updateUserDetailsDto.setId(userId);
        updateUserDetailsDto.setFirstName("May");
        updateUserDetailsDto.setLastName("Doe");
        updateUserDetailsDto.setDateOfBirth(LocalDate.of(2000, 2, 2));
        updateUserDetailsDto.setNationalInsuranceNumber("12345667");

        when(userDetailsService.updateUserDetails(eq(userId), any(UpdateUserDetailsDto.class)))
                .thenReturn(updateUserDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/person-details/members/update/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDetailsDto)))
                .andExpect(status().isOk())
                // Verify the response JSON matches our expected updated values
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("May"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.dateOfBirth").value("2000-02-02"))
                .andExpect(jsonPath("$.nationalInsuranceNumber").value("12345667"));
    }
}

