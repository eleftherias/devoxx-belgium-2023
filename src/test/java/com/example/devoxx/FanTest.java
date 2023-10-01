package com.example.devoxx;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FanTest {
    @Test
    public void constructorCreatesCopy() {
        Fan fan = new Fan();
        fan.setUsername("user");
        fan.setPassword("password");
        fan.setShow("8A");
        fan.setSwiftie(true);
        Fan copy = new Fan(fan);
        assertThat(fan).usingRecursiveComparison().isEqualTo(copy);
    }
}