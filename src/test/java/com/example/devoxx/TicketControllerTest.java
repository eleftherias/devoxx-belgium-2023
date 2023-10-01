package com.example.devoxx;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TicketControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void tourReturnsTourDetails() throws Exception {
        this.mockMvc.perform(get("/tour"))
                .andExpect(status().isOk())
                .andExpect(content().string("The Eras Tour, a journey through the musical eras of Taylor Swift's career (past & present!)"));
    }

    @Test
    public void greetingReturnsWelcomeAndUsername() throws Exception {
        this.mockMvc.perform(get("/greeting").with(user("Ria")))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome, Ria!"));
    }

    @Test
    public void greetingWhenUnauthenticatedUserThenReturns401() throws Exception {
        this.mockMvc.perform(get("/greeting"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void getNearestVenueReturnsFansNearestVenue() throws Exception {
        Fan fan = new Fan();
        fan.setNearestVenue("Amsterdam, Netherlands");
        this.mockMvc.perform(get("/nearest-venue").with(user(new FanService.FanDetails(fan))))
                .andExpect(status().isOk())
                .andExpect(content().string("Amsterdam, Netherlands"));
    }

    @Test
    public void getNearestVenueWhenUnauthenticatedUserThenReturns401() throws Exception {
        this.mockMvc.perform(get("/nearest-venue"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }
}