package com.example.devoxx;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    @GetMapping("/tour")
    public String tourDetails() {
        return "The Eras Tour, a journey through the musical eras of Taylor Swift's career (past & present!)";
    }
}
