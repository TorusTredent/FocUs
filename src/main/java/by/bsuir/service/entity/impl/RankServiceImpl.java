package by.bsuir.service.entity.impl;

import by.bsuir.entity.Rank;
import by.bsuir.repository.RankRepository;
import by.bsuir.service.entity.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankServiceImpl implements RankService {

    private final RankRepository rankRepository;

    @Autowired
    public RankServiceImpl(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    @Override
    public List<Rank> saveAll(List<Rank> ranks) {
        return rankRepository.saveAll(ranks);
    }
}
