package com.nnk.springboot.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServiceMock;

    @Test
    public void homeShouldReturnOkAndUserPageTest() throws Exception {

        mockMvc.perform(get("/user/list"))
                .andExpect(view().name("user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"));
    }

    @Test
    public void getAddFormShouldReturnToAddFormTest() throws Exception {

        mockMvc.perform(get("/user/add"))
                .andExpect(view().name("user/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void validateAddShouldReturnToAddFormWhenErrorsTest() throws Exception {

        User user = new User();
        user.setUsername(null);
        user.setPassword("pwd");
        user.setFullname("full name");
        user.setRole("role");
        mockMvc.perform(post("/user/validate")
                .flashAttr("user", user))
                .andExpect(view().name("user/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void validateAddShouldRedirectToUserAndCreateIsCalledWhenNoErrorsTest() throws Exception {

        User user = new User();
        user.setUsername("userName");
        user.setPassword("pwd");
        user.setFullname("full name");
        user.setRole("role");
        mockMvc.perform(post("/user/validate")
                .flashAttr("user", user))
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(status().is3xxRedirection());
        verify(userServiceMock, Mockito.times(1)).createUser(user);
    }

    @Test
    public void showUpdateFormShouldReturnToUpdateFormAndAddExistingUserToTheModelTest() throws Exception {

        User existingUser = new User();
        existingUser.setUsername("userName");
        existingUser.setPassword("pwd");
        existingUser.setFullname("full name");
        existingUser.setRole("role");

        when(userServiceMock.findById(anyInt())).thenReturn(Optional.of(existingUser));

        mockMvc.perform(get("/user/update/{id}",1))
                .andExpect(view().name("user/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"));
        verify(userServiceMock,Mockito.times(1)).findById(1);
    }

    @Test
    public void showUpdateFormShouldThrowIllegalArgumentExceptionWhenIdDoesntExistTest() {

        when(userServiceMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockMvc.perform(get("/user/update/{id}",1))).hasCause(new IllegalArgumentException("Invalid user Id:1"));
    }

    @Test
    public void updateShouldReturnToUpdateFormWhenErrorsTest() throws Exception {

        User existingUser = new User();
        existingUser.setUsername("userName");
        existingUser.setPassword("pwd");
        existingUser.setFullname("full name");
        existingUser.setRole("role");

        when(userServiceMock.findById(anyInt())).thenReturn(Optional.of(existingUser));

        existingUser.setUsername(null);

        mockMvc.perform(post("/user/update/{id}",1)
                .flashAttr("user", existingUser))
                .andExpect(view().name("user/update"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void updateShouldRedirectToUserAndUpdateIsCalledWhenNoErrorsTest() throws Exception {

        User existingUser = new User();
        existingUser.setUsername("userName");
        existingUser.setPassword("pwd");
        existingUser.setFullname("full name");
        existingUser.setRole("role");

        when(userServiceMock.findById(anyInt())).thenReturn(Optional.of(existingUser));

        mockMvc.perform(post("/user/update/{id}",1)
                .flashAttr("user", existingUser))
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(status().is3xxRedirection());
        verify(userServiceMock, Mockito.times(1)).updateUser(existingUser);
    }

    @Test
    public void deleteShouldRedirectToListAndDeleteIsCalledTest() throws Exception {

        User existingUser = new User();
        existingUser.setUsername("userName");
        existingUser.setPassword("pwd");
        existingUser.setFullname("full name");
        existingUser.setRole("role");

        when(userServiceMock.findById(anyInt())).thenReturn(Optional.of(existingUser));

        mockMvc.perform(get("/user/delete/{id}",1)
                .flashAttr("user", existingUser))
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(status().is3xxRedirection());
        verify(userServiceMock,Mockito.times(1)).deleteUser(existingUser);
    }

    @Test
    public void deleteShouldThrowIllegalArgumentExceptionWhenIdDoesntExistTest() {

        when(userServiceMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockMvc.perform(get("/user/delete/{id}",1))).hasCause(new IllegalArgumentException("Invalid user Id:1"));
    }

}
