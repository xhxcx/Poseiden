package com.nnk.springboot.controller;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
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
public class BidListControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListServiceMock;

    @MockBean
    private BidListRepository bidListRepositoryMock;

    @Test
    public void homeShouldReturnOkAndBidListPageTest() throws Exception {

        mockMvc.perform(get("/bidList/list"))
                .andExpect(view().name("bidList/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bidLists"));
    }

    @Test
    public void getAddBidFormShouldReturnToAddFormTest() throws Exception {

        mockMvc.perform(get("/bidList/add"))
                .andExpect(view().name("bidList/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void validateAddShouldReturnToAddFormWhenErrorsTest() throws Exception {

        BidList bidList = new BidList();
        bidList.setAccount(null);
        bidList.setType("type");
        bidList.setBidQuantity(1d);
        mockMvc.perform(post("/bidList/validate")
                .flashAttr("bidList", bidList))
                .andExpect(view().name("bidList/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void validateAddShouldRedirectToBidListAndCreateIsCalledWhenNoErrorsTest() throws Exception {

        BidList bidList = new BidList();
        bidList.setAccount("account");
        bidList.setType("type");
        bidList.setBidQuantity(1d);
        mockMvc.perform(post("/bidList/validate")
                .flashAttr("bidList", bidList))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(status().is3xxRedirection());
        verify(bidListServiceMock, Mockito.times(1)).createBidList(bidList);
    }

    @Test
    public void showUpdateFormShouldReturnToUpdateFormAndAddExistingBidListToTheModelTest() throws Exception {

        BidList existingBidList = new BidList();
        existingBidList.setAccount("account");
        existingBidList.setType("type");
        existingBidList.setBidQuantity(1d);

        when(bidListServiceMock.findById(anyInt())).thenReturn(Optional.of(existingBidList));

        mockMvc.perform(get("/bidList/update/{id}",1))
                .andExpect(view().name("bidList/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bidListToUpdate"));
        verify(bidListServiceMock,Mockito.times(1)).findById(1);
    }

    @Test
    public void showUpdateFormShouldThrowIllegalArgumentExceptionWhenIdDoesntExistTest() {

        when(bidListServiceMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockMvc.perform(get("/bidList/update/{id}",1))).hasCause(new IllegalArgumentException("No bidList found for this id :1"));
    }

    @Test
    public void updateShouldReturnToUpdateFormWhenErrorsTest() throws Exception {

        BidList existingBidList = new BidList();
        existingBidList.setAccount("account");
        existingBidList.setType("type");
        existingBidList.setBidQuantity(1d);
        when(bidListServiceMock.findById(anyInt())).thenReturn(Optional.of(existingBidList));

        existingBidList.setAccount(null);

        mockMvc.perform(post("/bidList/update/{id}",1)
                .flashAttr("bidListToUpdate", existingBidList))
                .andExpect(view().name("bidList/update"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void updateShouldRedirectToBidListAndUpdateIsCalledWhenNoErrorsTest() throws Exception {

        BidList existingBidList = new BidList();
        existingBidList.setAccount("account");
        existingBidList.setType("type");
        existingBidList.setBidQuantity(1d);
        when(bidListServiceMock.findById(anyInt())).thenReturn(Optional.of(existingBidList));

        mockMvc.perform(post("/bidList/update/{id}",1)
                .flashAttr("bidList", existingBidList))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(status().is3xxRedirection());
        verify(bidListServiceMock, Mockito.times(1)).updateBidList(existingBidList);
    }

    @Test
    public void deleteBidShouldRedirectToBidlistListAndDeleteIsCalledTest() throws Exception {

        BidList existingBidList = new BidList();
        existingBidList.setAccount("account");
        existingBidList.setType("type");
        existingBidList.setBidQuantity(1d);

        when(bidListServiceMock.findById(anyInt())).thenReturn(Optional.of(existingBidList));

        mockMvc.perform(get("/bidList/delete/{id}",1)
                .flashAttr("bidList", existingBidList))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(status().is3xxRedirection());
        verify(bidListServiceMock,Mockito.times(1)).deleteBidList(existingBidList);
    }

    @Test
    public void deleteBidShouldThrowIllegalArgumentExceptionWhenIdDoesntExistTest() {

        when(bidListServiceMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockMvc.perform(get("/bidList/delete/{id}",1))).hasCause(new IllegalArgumentException("No bidList found for this id :1"));
    }

}
