package com.example.devoxx;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    @GetMapping("/tour")
    public String tourDetails() {
        return "The Eras Tour, a journey through the musical eras of Taylor Swift's career (past & present!)";
    }

    @GetMapping("/greeting")
    public String greeting(@AuthenticationPrincipal(expression = "username") String username) {
        return "Welcome, " + username + "!";
    }

    @GetMapping("/nearest-venue")
    public String nearestVenue(@AuthenticationPrincipal Fan fan) {
        return fan.getNearestVenue();
    }
}
