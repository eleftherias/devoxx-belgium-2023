package com.example.devoxx;

import com.example.devoxx.FanService.FanDetails;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FanServiceTest {
    @Test
    public void userGetsAuthorityFanByDefault() {
        Fan user = new Fan();
        FanDetails userDetails = new FanDetails(user);
        Set<String> authorities = AuthorityUtils.authorityListToSet(userDetails.getAuthorities());
        assertThat(authorities).containsExactly("FAN");
    }

    @Test
    public void swiftieGetsAuthoritySwiftie() {
        Fan user = new Fan();
        user.setSwiftie(true);
        FanDetails userDetails = new FanDetails(user);
        Set<String> authorities = AuthorityUtils.authorityListToSet(userDetails.getAuthorities());
        assertThat(authorities).containsExactlyInAnyOrder("FAN", "SWIFTIE");
    }

    @Test
    public void FanDetailsHasTheSameValuesAsFan() {
        Fan user = new Fan();
        user.setUsername("user");
        user.setPassword("password");
        user.setNearestVenue("Paris, France");
        FanDetails userDetails = new FanDetails(user);
        assertThat(userDetails.getUsername()).isEqualTo("user");
        assertThat(userDetails.getPassword()).isEqualTo("password");
        assertThat(userDetails.getNearestVenue()).isEqualTo("Paris, France");
    }
}