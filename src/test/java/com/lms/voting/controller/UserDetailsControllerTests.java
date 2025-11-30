//package com.lms.voting.controller;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.lms.voting.entity.UserDetails;
//import com.lms.voting.service.UserDetailsService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
//@AutoConfigureMockMvc
//@WebMvcTest(UserDetailsController.class)// testing the web layer (controller) without loading the entire Spring context.
//class UserDetailsControllerTests {
//
//    // to perform the request: This allows you to simulate HTTP requests to your controller and check the response.
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    // You’ll need to mock the UserDetailsService that the controller relies on. You can do this using @MockBean.
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//
//    // test a single member
//    @Test
//    void testGetUserById_Success() throws Exception {
//        Integer testId = 1;
//        UserDetails user = new UserDetails();
//        user.setId(testId);
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setDateOfBirth(LocalDate.of(1990, 1, 1));
//        user.setNationalInsuranceNumber("AB123456C");
//
//        // Mock the service to return a user when the ID exists
//        when(userDetailsService.getPersonalDetailsByID(testId)).thenReturn(Optional.of(user));
//
//        mockMvc.perform(get("/members/{id}", testId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(testId))
//                .andExpect(jsonPath("$.firstName").value("John"))
//                .andExpect(jsonPath("$.lastName").value("Doe"))
//                .andExpect(jsonPath("$.dateOfBirth").value("1990-01-01"))
//                .andExpect(jsonPath("$.nationalInsuranceNumber").value("AB123456C"));
//    }
//
//    @Test
//    void testGetUserById_NotFound() throws Exception {
//        // Assuming this ID doesn't exist
//        Integer testId = 999;
//
//        // Mock the service to return an empty Optional when the ID is not found
//        when(userDetailsService.getPersonalDetailsByID(testId)).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/members/{id}", testId))
//                .andExpect(status().isNotFound());
//    }
//
//
//}
