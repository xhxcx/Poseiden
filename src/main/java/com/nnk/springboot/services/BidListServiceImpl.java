package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListServiceImpl implements BidListService{

    private final BidListRepository bidListRepository;

    @Autowired
    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Override
    public List<BidList> getAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList createBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    @Override
    public BidList updateBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    @Override
    public void deleteBidList(BidList bidList) {
        bidListRepository.delete(bidList);
    }

    @Override
    public Optional<BidList> findById(Integer id) {
        return bidListRepository.findById(id);
    }
}
