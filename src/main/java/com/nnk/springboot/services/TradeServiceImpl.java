package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService{

    private final TradeRepository tradeRepository;

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> getAll() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade createTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    @Override
    public Trade updateTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    @Override
    public void deleteTrade(Trade trade) {
        tradeRepository.delete(trade);
    }

    @Override
    public Optional<Trade> findById(Integer id) {
        return tradeRepository.findById(id);
    }
}
