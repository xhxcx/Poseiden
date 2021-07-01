package com.nnk.springboot.integration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
@Sql({"/sql/IT_data.sql"})
public class UserServiceIT {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAllIT(){
        List<User> result = userService.getAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getFullname()).isEqualTo("Fullname user 1");
    }

    @Test
    public void createUserIT(){
        User newUser = new User();
        newUser.setFullname("Fullname new user");
        newUser.setUsername("Username new user");
        newUser.setPassword("Pwd new user");
        newUser.setRole("Role new user");

        User result = userService.createUser(newUser);

        assertThat(result.getId()).isEqualTo(3);
        assertThat(result.getFullname()).isEqualTo(newUser.getFullname());
        assertThat(userRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    public void updateUserIT(){
        User userToUpdate = userRepository.findById(1).orElse(new User());
        userToUpdate.setFullname("Fullname user 1 updated");

        User result = userService.updateUser(userToUpdate);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getFullname()).isEqualTo("Fullname user 1 updated");
        assertThat(userRepository.findById(1).orElse(null)).isEqualTo(result);
        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void deleteUserIT(){
        User userToDelete = userRepository.findById(2).orElse(new User());
        userService.deleteUser(userToDelete);
        assertThat(userRepository.findAll().size()).isEqualTo(1);
        assertThat(userRepository.findById(2).isPresent()).isFalse();
    }

    @Test
    public void findByIdIT(){
        User result = userService.findById(1).orElse(null);
        assertThat(result).isNotNull();
        assertThat(result.getFullname()).isEqualTo("Fullname user 1");
    }

}
