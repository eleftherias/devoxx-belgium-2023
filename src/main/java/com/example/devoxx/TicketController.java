package com.example.devoxx;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/show")
    public void bookShow(@AuthenticationPrincipal Fan fan, @RequestBody String show) {
        // To keep this talk focused, data operations are omitted
        System.out.println("They'll tell you now, you're the lucky one, " + fan.getUsername());
    }
}
