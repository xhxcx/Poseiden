package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeServiceMock;

    @Test
    public void homeShouldReturnOkAndTradePageTest() throws Exception {

        mockMvc.perform(get("/trade/list"))
                .andExpect(view().name("trade/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trades"));
    }

    @Test
    public void getAddFormShouldReturnToAddFormTest() throws Exception {

        mockMvc.perform(get("/trade/add"))
                .andExpect(view().name("trade/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void validateAddShouldReturnToAddFormWhenErrorsTest() throws Exception {

        Trade trade = new Trade();
        trade.setAccount(null);
        trade.setType("type");
        trade.setBuyQuantity(1d);
        mockMvc.perform(post("/trade/validate")
                .flashAttr("trade", trade))
                .andExpect(view().name("trade/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void validateAddShouldRedirectToTradeAndCreateIsCalledWhenNoErrorsTest() throws Exception {

        Trade trade = new Trade();
        trade.setAccount("account");
        trade.setType("type");
        trade.setBuyQuantity(1d);
        mockMvc.perform(post("/trade/validate")
                .flashAttr("trade", trade))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(status().is3xxRedirection());
        verify(tradeServiceMock, Mockito.times(1)).createTrade(trade);
    }

    @Test
    public void showUpdateFormShouldReturnToUpdateFormAndAddExistingTradeToTheModelTest() throws Exception {

        Trade existingTrade = new Trade();
        existingTrade.setAccount("account");
        existingTrade.setType("type");
        existingTrade.setBuyQuantity(1d);

        when(tradeServiceMock.findById(anyInt())).thenReturn(Optional.of(existingTrade));

        mockMvc.perform(get("/trade/update/{id}",1))
                .andExpect(view().name("trade/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tradeToUpdate"));
        verify(tradeServiceMock,Mockito.times(1)).findById(1);
    }

    @Test
    public void showUpdateFormShouldThrowIllegalArgumentExceptionWhenIdDoesntExistTest() {

        when(tradeServiceMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockMvc.perform(get("/trade/update/{id}",1))).hasCause(new IllegalArgumentException("No trade found for this id :1"));
    }

    @Test
    public void updateShouldReturnToUpdateFormWhenErrorsTest() throws Exception {

        Trade existingTrade = new Trade();
        existingTrade.setAccount("account");
        existingTrade.setType("type");
        existingTrade.setBuyQuantity(1d);
        when(tradeServiceMock.findById(anyInt())).thenReturn(Optional.of(existingTrade));

        existingTrade.setAccount(null);

        mockMvc.perform(post("/trade/update/{id}",1)
                .flashAttr("trade", existingTrade))
                .andExpect(view().name("/trade/update"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void updateShouldRedirectToTradeAndUpdateIsCalledWhenNoErrorsTest() throws Exception {

        Trade existingTrade = new Trade();
        existingTrade.setAccount("account");
        existingTrade.setType("type");
        existingTrade.setBuyQuantity(1d);
        when(tradeServiceMock.findById(anyInt())).thenReturn(Optional.of(existingTrade));

        mockMvc.perform(post("/trade/update/{id}",1)
                .flashAttr("trade", existingTrade))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(status().is3xxRedirection());
        verify(tradeServiceMock, Mockito.times(1)).updateTrade(existingTrade);
    }

    @Test
    public void deleteShouldRedirectTolistListAndDeleteIsCalledTest() throws Exception {

        Trade existingTrade = new Trade();
        existingTrade.setAccount("account");
        existingTrade.setType("type");
        existingTrade.setBuyQuantity(1d);

        when(tradeServiceMock.findById(anyInt())).thenReturn(Optional.of(existingTrade));

        mockMvc.perform(get("/trade/delete/{id}",1)
                .flashAttr("trade", existingTrade))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(status().is3xxRedirection());
        verify(tradeServiceMock,Mockito.times(1)).deleteTrade(existingTrade);
    }

    @Test
    public void deleteShouldThrowIllegalArgumentExceptionWhenIdDoesntExistTest() {

        when(tradeServiceMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockMvc.perform(get("/trade/delete/{id}",1))).hasCause(new IllegalArgumentException("No trade found for this id :1"));
    }

}
