package com.example.devoxx;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
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

    @Test
    public void postShowWhenFanIsSwiftieThenBooksShow() throws Exception {
        Fan swiftie = new Fan();
        swiftie.setSwiftie(true);
        this.mockMvc.perform(post("/show")
                        .content("Amsterdam, Netherlands")
                        .with(user(new FanService.FanDetails(swiftie)))
                        .with(csrf())
                )
                .andExpect(status().isOk());
    }

    @Test
    public void postShowWhenFanIsNotSwiftieThenReturns403() throws Exception {
        Fan fan = new Fan();
        this.mockMvc.perform(post("/show")
                        .content("Amsterdam, Netherlands")
                        .with(user(new FanService.FanDetails(fan)))
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void postShowWhenUnauthenticatedThenReturns401() throws Exception {
        this.mockMvc.perform(post("/show")
                        .content("Amsterdam, Netherlands")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void supportWhenUserAuthorityIsSupportThenReturnsAdvice() throws Exception {
        Jwt supportJwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "customer-support")
                .claim("scope", "support")
                .build();
        this.mockMvc.perform(get("/support/tickets").with(jwt().jwt(supportJwt)))
                .andExpect(status().isOk())
                .andExpect(content().string("Would've, could've, should've stayed in 1 tab"));
    }

    @Test
    public void supportWhenUserAuthorityIsNotSupportThenReturns403() throws Exception {
        Jwt noSupportJwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "customer-support")
                .build();
        this.mockMvc.perform(get("/support/tickets").with(jwt().jwt(noSupportJwt)))
                .andExpect(status().isForbidden());
    }
}