package com.nnk.springboot.integration;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListServiceImpl;
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
public class BidListServiceIT {

    @Autowired
    private BidListServiceImpl bidListService;

    @Autowired
    private BidListRepository bidListRepository;

    @Test
    public void getAllIT(){
        List<BidList> result = bidListService.getAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getAccount()).isEqualTo("Account bidList 1");
    }

    @Test
    public void createBidListIT(){
        BidList newBidList = new BidList();
        newBidList.setAccount("Account new bidList");
        newBidList.setType("Type new bidList");
        newBidList.setBidQuantity(999d);

        BidList result = bidListService.createBidList(newBidList);

        assertThat(result.getBidListId()).isEqualTo(3);
        assertThat(result.getAccount()).isEqualTo(newBidList.getAccount());
        assertThat(bidListRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    public void updateBidListIT(){
        BidList bidListToUpdate = bidListRepository.findById(1).orElse(new BidList());
        bidListToUpdate.setAccount("Account bidList 1 updated");

        BidList result = bidListService.updateBidList(bidListToUpdate);

        assertThat(result.getBidListId()).isEqualTo(1);
        assertThat(result.getAccount()).isEqualTo("Account bidList 1 updated");
        assertThat(bidListRepository.findById(1).orElse(null)).isEqualTo(result);
        assertThat(bidListRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void deleteBidListIT(){
        BidList bidListToDelete = bidListRepository.findById(2).orElse(new BidList());
        bidListService.deleteBidList(bidListToDelete);
        assertThat(bidListRepository.findAll().size()).isEqualTo(1);
        assertThat(bidListRepository.findById(2).isPresent()).isFalse();
    }

    @Test
    public void findByIdIT(){
        BidList result = bidListService.findById(1).orElse(null);
        assertThat(result).isNotNull();
        assertThat(result.getAccount()).isEqualTo("Account bidList 1");
    }

}
