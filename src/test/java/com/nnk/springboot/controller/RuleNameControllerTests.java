package com.nnk.springboot.controller;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameServiceMock;

    @Test
    public void homeShouldReturnOkAndRuleNamePageTest() throws Exception {

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(view().name("ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ruleNames"));
    }

    @Test
    public void getAddBidFormShouldReturnToAddFormTest() throws Exception {

        mockMvc.perform(get("/ruleName/add"))
                .andExpect(view().name("ruleName/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void validateAddShouldReturnToAddFormWhenErrorsTest() throws Exception {

        RuleName ruleName = new RuleName();
        ruleName.setName("More than 125 characters to test the @size annotation validation on this field and then have error in binding result of the post mapping");

        mockMvc.perform(post("/ruleName/validate")
                .flashAttr("ruleName", ruleName))
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void validateAddShouldRedirectToRuleNameAndCreateIsCalledWhenNoErrorsTest() throws Exception {

        RuleName ruleName = new RuleName();
        ruleName.setName("Correct name");
        mockMvc.perform(post("/ruleName/validate")
                .flashAttr("ruleName", ruleName))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(status().is3xxRedirection());
        verify(ruleNameServiceMock, Mockito.times(1)).createRuleName(ruleName);
    }

    @Test
    public void showUpdateFormShouldReturnToUpdateFormAndAddExistingRuleNameToTheModelTest() throws Exception {

        RuleName existingRuleName = new RuleName();
        existingRuleName.setName("Correct name");

        when(ruleNameServiceMock.findById(anyInt())).thenReturn(Optional.of(existingRuleName));

        mockMvc.perform(get("/ruleName/update/{id}",1))
                .andExpect(view().name("ruleName/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ruleNameToUpdate"));
        verify(ruleNameServiceMock,Mockito.times(1)).findById(1);
    }

    @Test
    public void showUpdateFormShouldThrowIllegalArgumentExceptionWhenIdDoesntExistTest() {

        when(ruleNameServiceMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockMvc.perform(get("/ruleName/update/{id}",1))).hasCause(new IllegalArgumentException("No ruleName found for this id :1"));
    }

    @Test
    public void updateShouldReturnToUpdateFormWhenErrorsTest() throws Exception {

        RuleName existingRuleName = new RuleName();
        existingRuleName.setName("Initial correct name");

        when(ruleNameServiceMock.findById(1)).thenReturn(Optional.of(existingRuleName));

        existingRuleName.setName("More than 125 characters to test the @size annotation validation on this field and then have error in binding result of the post mapping");

        mockMvc.perform(post("/ruleName/update/{id}",1)
                .flashAttr("ruleName", existingRuleName))
                .andExpect(view().name("/ruleName/update"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void updateShouldRedirectToRuleNameAndUpdateIsCalledWhenNoErrorsTest() throws Exception {

        RuleName existingRuleName = new RuleName();
        existingRuleName.setName("Correct name");

        when(ruleNameServiceMock.findById(anyInt())).thenReturn(Optional.of(existingRuleName));

        mockMvc.perform(post("/ruleName/update/{id}",1)
                .flashAttr("ruleName", existingRuleName))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(status().is3xxRedirection());
        verify(ruleNameServiceMock, Mockito.times(1)).updateRuleName(existingRuleName);
    }

    @Test
    public void deleteShouldRedirectToBidlistListAndDeleteIsCalledTest() throws Exception {

        RuleName existingRuleName = new RuleName();
        existingRuleName.setName("Correct name");

        when(ruleNameServiceMock.findById(anyInt())).thenReturn(Optional.of(existingRuleName));

        mockMvc.perform(get("/ruleName/delete/{id}",1)
                .flashAttr("ruleName", existingRuleName))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(status().is3xxRedirection());
        verify(ruleNameServiceMock,Mockito.times(1)).deleteRuleName(existingRuleName);
    }

    @Test
    public void deleteShouldThrowIllegalArgumentExceptionWhenIdDoesntExistTest() {

        when(ruleNameServiceMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockMvc.perform(get("/ruleName/delete/{id}",1))).hasCause(new IllegalArgumentException("No ruleName found for this id :1"));
    }

}
