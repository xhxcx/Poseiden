package com.nnk.springboot.integration;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeServiceImpl;
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
public class TradeServiceIT {

    @Autowired
    private TradeServiceImpl tradeService;

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    public void getAllIT(){
        List<Trade> result = tradeService.getAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getAccount()).isEqualTo("Account trade 1");
    }

    @Test
    public void createTradeIT(){
        Trade newTrade = new Trade();
        newTrade.setAccount("Account new trade");
        newTrade.setType("Type new trade");
        newTrade.setBuyQuantity(999d);

        Trade result = tradeService.createTrade(newTrade);

        assertThat(result.getTradeId()).isEqualTo(3);
        assertThat(result.getAccount()).isEqualTo(newTrade.getAccount());
        assertThat(tradeRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    public void updateTradeIT(){
        Trade tradeToUpdate = tradeRepository.findById(1).orElse(new Trade());
        tradeToUpdate.setAccount("Account trade 1 updated");

        Trade result = tradeService.updateTrade(tradeToUpdate);

        assertThat(result.getTradeId()).isEqualTo(1);
        assertThat(result.getAccount()).isEqualTo("Account trade 1 updated");
        assertThat(tradeRepository.findById(1).orElse(null)).isEqualTo(result);
        assertThat(tradeRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void deleteTradeIT(){
        Trade tradeToDelete = tradeRepository.findById(2).orElse(new Trade());
        tradeService.deleteTrade(tradeToDelete);
        assertThat(tradeRepository.findAll().size()).isEqualTo(1);
        assertThat(tradeRepository.findById(2).isPresent()).isFalse();
    }

    @Test
    public void findByIdIT(){
        Trade result = tradeService.findById(1).orElse(null);
        assertThat(result).isNotNull();
        assertThat(result.getAccount()).isEqualTo("Account trade 1");
    }

}
