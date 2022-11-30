package by.bsuir.service.entity.impl;

import by.bsuir.entity.Rank;
import by.bsuir.exception.BusinessException;
import by.bsuir.exception.enums.ERROR_CODE;
import by.bsuir.repository.RankRepository;
import by.bsuir.service.entity.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.image.BufferStrategy;
import java.util.List;

import static by.bsuir.exception.enums.ERROR_CODE.RANK_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    @Override
    public Rank save(Rank rank) {
        return rankRepository.save(rank);
    }

    @Override
    public Rank findById(Long rankId) {
        return rankRepository.findById(rankId)
                .orElseThrow(() -> new BusinessException(String.format("Rank with id %s not found", rankId), RANK_NOT_FOUND, NOT_FOUND));
    }

    @Override
    public void delete(Rank rank) {
        rankRepository.delete(rank);
    }
}
