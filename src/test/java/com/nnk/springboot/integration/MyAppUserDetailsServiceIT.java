package com.nnk.springboot.integration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.MyAppUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
@Sql({"/sql/IT_data.sql"})
public class MyAppUserDetailsServiceIT {

    @Autowired
    private MyAppUserDetailsService userDetailsService;

    @Test
    public void loadUserByUsernameShouldReturnExistingUserWhenFoundIT(){
        UserDetails userDetails = userDetailsService.loadUserByUsername("Username user 1");
        User expectedUser = new User();
        expectedUser.setUsername("Username user 1");
        expectedUser.setPassword("pwd user 1");
        expectedUser.setRole("ADMIN");
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(expectedUser.getRole()));

        assertThat(userDetails.getPassword()).isEqualTo(expectedUser.getPassword());
        assertThat(userDetails.getAuthorities().containsAll(roles)).isTrue();
        assertThat(userDetails.isAccountNonExpired()).isTrue();
        assertThat(userDetails.isEnabled()).isTrue();

    }

    @Test
    public void loadUserByUsernameShouldThrowExceptionWhenUserNotFoundIT() {
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("Unknown"));
        assertThat(exception.getMessage()).isEqualTo("User not found for username : Unknown");
    }
}
